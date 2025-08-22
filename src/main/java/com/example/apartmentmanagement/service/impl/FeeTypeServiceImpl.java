package com.example.apartmentmanagement.service.impl;

import com.example.apartmentmanagement.dto.request.FeeTypeRequest;
import com.example.apartmentmanagement.dto.response.FeeTypeResponse;
import com.example.apartmentmanagement.entity.FeeType;
import com.example.apartmentmanagement.enums.ErrorCode;
import com.example.apartmentmanagement.exception.AppException;
import com.example.apartmentmanagement.mapper.FeeTypeMapper;
import com.example.apartmentmanagement.repository.FeeTypeRepository;
import com.example.apartmentmanagement.service.FeeTypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FeeTypeServiceImpl implements FeeTypeService {
    private final FeeTypeRepository feeTypeRepository;
    private final FeeTypeMapper feeTypeMapper;

    @Override
    public FeeTypeResponse createFeeType(FeeTypeRequest request) {
        if (request.getMetered() == null) {
            throw new AppException(ErrorCode.INVALID_METERED_VALUE);
        }

        if (feeTypeRepository.existsByName(request.getName())) {
            throw new AppException(ErrorCode.FEE_TYPE_ALREADY_EXISTS);
        }
        FeeType feeType = feeTypeMapper.toEntity(request);
        return feeTypeMapper.toResponse(feeTypeRepository.save(feeType));
    }

    @Override
    public FeeTypeResponse updateFeeType(Long id, FeeTypeRequest request) {
        FeeType feeType = feeTypeRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.FEE_TYPE_NOT_FOUND));

        if (request.getMetered() == null) {
            throw new AppException(ErrorCode.INVALID_METERED_VALUE);
        }

        if (!feeType.getName().equals(request.getName())
                && feeTypeRepository.existsByName(request.getName())) {
            throw new AppException(ErrorCode.FEE_TYPE_ALREADY_EXISTS);
        }

        feeType.setName(request.getName());
        feeType.setUnitPrice(request.getUnitPrice());
        feeType.setMetered(request.getMetered());

        return feeTypeMapper.toResponse(feeTypeRepository.save(feeType));
    }

    @Override
    public void deleteFeeType(Long id) {
        if (!feeTypeRepository.existsById(id)) {
            throw new AppException(ErrorCode.FEE_TYPE_NOT_FOUND);
        }
        feeTypeRepository.deleteById(id);
    }

    @Override
    public List<FeeTypeResponse> getAllFeeTypes() {
        return feeTypeRepository.findAll().stream()
                .map(feeTypeMapper::toResponse)
                .toList();
    }

    @Override
    public FeeTypeResponse getFeeTypeById(Long id) {
        FeeType feeType = feeTypeRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.FEE_TYPE_NOT_FOUND));
        return feeTypeMapper.toResponse(feeType);
    }
}
