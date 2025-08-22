package com.example.apartmentmanagement.service.impl;

import com.example.apartmentmanagement.dto.request.bill.CreateBillRequest;
import com.example.apartmentmanagement.dto.request.bill.UpdateBillRequest;
import com.example.apartmentmanagement.dto.response.bill.BillResponse;
import com.example.apartmentmanagement.entity.Apartment;
import com.example.apartmentmanagement.entity.Bill;
import com.example.apartmentmanagement.entity.BillItem;
import com.example.apartmentmanagement.entity.FeeType;
import com.example.apartmentmanagement.enums.BillStatus;
import com.example.apartmentmanagement.enums.ErrorCode;
import com.example.apartmentmanagement.enums.FeeTypeMode;
import com.example.apartmentmanagement.exception.AppException;
import com.example.apartmentmanagement.mapper.BillItemMapper;
import com.example.apartmentmanagement.mapper.BillMapper;
import com.example.apartmentmanagement.repository.*;
import com.example.apartmentmanagement.service.BillService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BillServiceImpl implements BillService {
    private final BillRepository billRepository;
    private final BillItemRepository billItemRepository;
    private final FeeTypeRepository feeTypeRepository;
    private final ApartmentRepository apartmentRepository;
    private final ResidentApartmentHistoryRepository historyRepository;
    private final BillMapper billMapper;
    private final BillItemMapper billItemMapper;

    @Override
    @Transactional
    public BillResponse createBill(CreateBillRequest request) {
        Apartment apartment = apartmentRepository.findById(request.getApartmentId())
                .orElseThrow(() -> new AppException(ErrorCode.APARTMENT_NOT_FOUND));

        Bill bill = billMapper.toBill(request);
        bill.setApartment(apartment);
        billRepository.save(bill);

        // Xử lý danh sách BillItem
        List<BillItem> items = request.getItems().stream()
                .map(billItemRequest -> {
                    FeeType feeType = feeTypeRepository.findById(billItemRequest.getFeeTypeId())
                            .orElseThrow(() -> new AppException(ErrorCode.FEE_TYPE_NOT_FOUND));

                    BillItem item = billItemMapper.toBillItem(billItemRequest);
                    item.setBill(bill);
                    item.setFeeType(feeType);

                    double amount = feeType.getMetered().equals(FeeTypeMode.METERED)
                            ? feeType.getUnitPrice() * billItemRequest.getQuantity()
                            : feeType.getUnitPrice();

                    item.setAmount(amount);
                    return item;
                })
                .collect(Collectors.toList());

        double totalAmount = items.stream()
                .mapToDouble(BillItem::getAmount)
                .sum();

        // Không dùng setItems, mà clear + addAll để tránh lỗi orphanRemoval
        bill.getItems().clear();
        bill.getItems().addAll(items);

        bill.setTotalAmount(totalAmount);

        Bill savedBill = billRepository.save(bill);
        return billMapper.toBillResponse(savedBill);
    }


    @Override
    public BillResponse updateBill(Long billId, UpdateBillRequest request) {
        Bill bill = billRepository.findById(billId)
                .orElseThrow(() -> new AppException(ErrorCode.BILL_NOT_FOUND));

        if (request.getPaymentDate() != null) {
            bill.setPaymentDate(request.getPaymentDate());
            if (request.getStatus() != null) {
                bill.setStatus(request.getStatus());
            } else {
                if (request.getPaymentDate().isAfter(bill.getDueDate())) {
                    bill.setStatus(BillStatus.OVERDUE);
                } else {
                    bill.setStatus(BillStatus.PAID);
                }
            }
        } else {
            // Không có paymentDate
            if (request.getStatus() != null) {
                bill.setStatus(request.getStatus());
            }
        }

        Bill updatedBill = billRepository.save(bill);
        return billMapper.toBillResponse(updatedBill);
    }


    @Override
    public BillResponse getBillById(Long id) {
        Bill bill = billRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.BILL_NOT_FOUND));
        return billMapper.toBillResponse(bill);
    }

    @Override
    public List<BillResponse> getBillsByApartment(Long apartmentId) {
        List<Bill> bills = billRepository.findByApartmentId(apartmentId);
        return billMapper.toBillResponseList(bills);
    }

    @Override
    public List<BillResponse> getAllBills() {
        return billMapper.toBillResponseList(billRepository.findAll());
    }

    @Override
    public void deleteBill(Long id) {
        Bill bill = billRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.BILL_NOT_FOUND));
        billRepository.delete(bill);
    }
}
