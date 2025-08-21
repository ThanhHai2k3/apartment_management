package com.example.apartmentmanagement.service;

import com.example.apartmentmanagement.dto.request.bill.CreateBillRequest;
import com.example.apartmentmanagement.dto.request.bill.UpdateBillRequest;
import com.example.apartmentmanagement.dto.response.bill.BillResponse;

import java.util.List;

public interface BillService {
    BillResponse createBill(CreateBillRequest request);
    BillResponse updateBill(Long billId, UpdateBillRequest request);
    BillResponse getBillById(Long id);
    List<BillResponse> getBillsByApartment(Long apartmentId);
    List<BillResponse> getAllBills();
    void deleteBill(Long id);
}
