package com.example.apartmentmanagement.mapper;

import com.example.apartmentmanagement.dto.request.bill.CreateBillRequest;
import com.example.apartmentmanagement.dto.response.bill.BillResponse;
import com.example.apartmentmanagement.entity.Apartment;
import com.example.apartmentmanagement.entity.Bill;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", uses = {BillItemMapper.class}, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface BillMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "apartment", source = "apartmentId", qualifiedByName = "mapApartmentIdToApartment")
    @Mapping(target = "items", ignore = true)
    @Mapping(target = "totalAmount", constant = "0.0")
    @Mapping(target = "status", constant = "PENDING")
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    Bill toBill(CreateBillRequest request);

    @Mapping(target = "apartmentId", source = "apartment.id")
    @Mapping(target = "apartmentNumber", source = "apartment.number")
    @Mapping(target = "items", source = "items")
    BillResponse toBillResponse(Bill bill);

    List<BillResponse> toBillResponseList(List<Bill> bills);

    // Custom mapping cho apartmentId
    @Named("mapApartmentIdToApartment")
    default Apartment mapApartmentIdToApartment(Long apartmentId) {
        Apartment apartment = new Apartment();
        apartment.setId(apartmentId);
        return apartment;
    }
}
