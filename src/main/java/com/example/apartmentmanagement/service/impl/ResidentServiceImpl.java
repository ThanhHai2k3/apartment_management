package com.example.apartmentmanagement.service.impl;

import com.example.apartmentmanagement.dto.request.ApartmentRequest;
import com.example.apartmentmanagement.dto.request.ResidentRequest;
import com.example.apartmentmanagement.dto.response.ResidentApartmentHistoryResponse;
import com.example.apartmentmanagement.dto.response.ResidentResponse;
import com.example.apartmentmanagement.entity.Apartment;
import com.example.apartmentmanagement.entity.Resident;
import com.example.apartmentmanagement.entity.ResidentApartmentHistory;
import com.example.apartmentmanagement.enums.ErrorCode;
import com.example.apartmentmanagement.exception.AppException;
import com.example.apartmentmanagement.mapper.ResidentApartmentHistoryMapper;
import com.example.apartmentmanagement.mapper.ResidentMapper;
import com.example.apartmentmanagement.repository.ApartmentRepository;
import com.example.apartmentmanagement.repository.ResidentApartmentHistoryRepository;
import com.example.apartmentmanagement.repository.ResidentRepository;
import com.example.apartmentmanagement.service.ResidentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ResidentServiceImpl implements ResidentService {
    private final ResidentRepository residentRepository;
    private final ApartmentRepository apartmentRepository;
    private final ResidentMapper residentMapper;
    private final ResidentApartmentHistoryRepository historyRepository;
    private final ResidentApartmentHistoryMapper historyMapper;

    @Override
    public ResidentResponse createResident (ResidentRequest request){
//        Apartment apartment = apartmentRepository.findById(request.getApartmentId())
//                .orElseThrow(() -> new AppException(ErrorCode.APARTMENT_NOT_FOUND));
        if (residentRepository.existsByIdNumber(request.getIdNumber())) {
            throw new AppException(ErrorCode.ID_NUMBER_EXISTED);
        }

        Resident resident = residentMapper.toResident(request);
        //Gán lại apartment thật (đảm bảo không bị entity giả)
        //resident.setApartment(apartment);

        Resident savedResident = residentRepository.save(resident);

        //Ghi lich su
//        ResidentApartmentHistory history = new ResidentApartmentHistory();
//        history.setResident(savedResident);
//        history.setApartment(apartment);
//        history.setStartDate(LocalDate.now());
//
//        historyRepository.save(history);

        return residentMapper.toResidentResponse(savedResident);
    }

    @Override
    public ResidentResponse updateResident(Long id, ResidentRequest request){
        Resident resident = residentRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.RESIDENT_NOT_FOUND));

//        Apartment apartment = apartmentRepository.findById(request.getApartmentId())
//                .orElseThrow(() -> new AppException(ErrorCode.APARTMENT_NOT_FOUND));

        if(resident.getIdNumber() != null && !request.getIdNumber().equals(resident.getIdNumber())){
            if (residentRepository.existsByIdNumberAndIdNot(request.getIdNumber(), id)){
                throw new AppException(ErrorCode.ID_NUMBER_EXISTED);
            }
            resident.setIdNumber(request.getIdNumber());
        }

        //Long currentApartmentId = resident.getApartment() != null ? resident.getApartment().getId() : null;

        residentMapper.updateResident(resident, request);
        //Gán lại apartment thật (đảm bảo không bị entity giả)
        //resident.setApartment(apartment);

        // Nếu apartment thay đổi
//        if(!Objects.equals(currentApartmentId, apartment.getId())){
//            // Kết thúc lịch sử hiện tại nếu có
//            historyRepository.findByResidentIdAndEndDateIsNull(id).ifPresent(h -> {
//                h.setEndDate(LocalDate.now());
//                historyRepository.save(h);
//            });
//
//            // Cập nhật resident → căn hộ mới
//            resident.setApartment(apartment);
//            resident.setMoveInDate(LocalDateTime.now());
//            resident.setMoveOutDate(null);
//
//            // Tạo lịch sử mới
//            ResidentApartmentHistory newHist = new ResidentApartmentHistory();
//            newHist.setResident(resident);
//            newHist.setApartment(apartment);
//            newHist.setStartDate(LocalDate.now());
//
//            historyRepository.save(newHist);
//        }
        Resident updatedResident = residentRepository.save(resident);

        return residentMapper.toResidentResponse(updatedResident);
    }

    @Override
    public void assignResidentToApartment(Long residentId, Long apartmentId){
        Resident resident = residentRepository.findById(residentId)
                .orElseThrow(() -> new AppException(ErrorCode.RESIDENT_NOT_FOUND));
        Apartment apartment = apartmentRepository.findById(apartmentId)
                .orElseThrow(() -> new AppException(ErrorCode.APARTMENT_NOT_FOUND));

        //Kết thúc lịch sử cũ nếu có
        historyRepository.findByResidentIdAndEndDateIsNull(residentId)
                .ifPresent(h -> {
                    h.setEndDate(LocalDate.now());
                    historyRepository.save(h);
                });

        //Tạo lịch sử mới
        ResidentApartmentHistory newHist = new ResidentApartmentHistory();
        newHist.setResident(resident);
        newHist.setApartment(apartment);
        newHist.setStartDate(LocalDate.now());

        historyRepository.save(newHist);
    }

    @Override
    public void removeResidentFromApartment(Long residentId){
        Resident resident = residentRepository.findById(residentId)
                .orElseThrow(() -> new AppException(ErrorCode.RESIDENT_NOT_FOUND));

//        if (resident.getApartment() == null){
//            throw new AppException(ErrorCode.RESIDENT_NOT_IN_APARTMENT);
//        }

        // Kết thúc lịch sử active
        ResidentApartmentHistory active = historyRepository.findByResidentIdAndEndDateIsNull(residentId)
                .orElseThrow(() -> new AppException(ErrorCode.HISTORY_NOT_FOUND));

        active.setEndDate(LocalDate.now());
        historyRepository.save(active);
    }

    @Override
    public List<ResidentApartmentHistoryResponse> getResidentHistory(Long residentId){
        residentRepository.findById(residentId)
                .orElseThrow(() -> new AppException(ErrorCode.RESIDENT_NOT_FOUND));

        List<ResidentApartmentHistory> histories = historyRepository.findByResidentIdOrderByStartDateDesc(residentId);

        return historyMapper.toResponses(histories);
    }

    //Check 2 resident có ở cùng căn hộ cùng thời điểm không
    @Override
    public boolean areResidentsCurrentCohabiting(Long residentId1, Long residentId2){
        if(!residentRepository.existsById(residentId1) || !residentRepository.existsById(residentId2)){
            throw new AppException(ErrorCode.RESIDENT_NOT_FOUND);
        }

        return historyRepository.existsCohabitationCurrent(residentId1, residentId2);
    }

    //Check xem có từng ở chung trong quá khứ không
    @Override
    public boolean haveResidentsEverCohabited(Long residentId1, Long residentId2) {
        if (!residentRepository.existsById(residentId1) || !residentRepository.existsById(residentId2)) {
            throw new AppException(ErrorCode.RESIDENT_NOT_FOUND);
        }
        return historyRepository.existsCohabitationHistory(residentId1, residentId2);
    }

    @Override
    public void deleteResident(Long id){
        residentRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.RESIDENT_NOT_FOUND));

        residentRepository.deleteById(id);
    }

    @Override
    public ResidentResponse getResidentById (Long id){
        Resident resident = residentRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.RESIDENT_NOT_FOUND));

        return residentMapper.toResidentResponse(resident);
    }

    @Override
    public List<ResidentResponse> getAllResidents(){
        return residentRepository.findAll()
                .stream()
                .map(residentMapper::toResidentResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<ResidentResponse> getCurrentResidentsByApartment(Long apartmentId){
        if (!apartmentRepository.existsById(apartmentId)) {
            throw new AppException(ErrorCode.APARTMENT_NOT_FOUND);
        }

        List<Resident> residents = residentRepository.findCurrentResidentsByApartmentId(apartmentId);
        return residents.stream()
                .map(residentMapper::toResidentResponse)
                .toList();
    }
}
