package com.example.apartmentmanagement.mapper;

import com.example.apartmentmanagement.dto.request.bill.BillItemRequest;
import com.example.apartmentmanagement.dto.response.bill.BillItemResponse;
import com.example.apartmentmanagement.entity.BillItem;
import com.example.apartmentmanagement.entity.FeeType;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface BillItemMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "bill", ignore = true)
    @Mapping(target = "feeType", source = "feeTypeId", qualifiedByName = "mapFeeTypeIdToFeeType")
    @Mapping(target = "amount", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    BillItem toBillItem(BillItemRequest request);

    @Mapping(target = "feeTypeId", source = "feeType.id")
    @Mapping(target = "feeTypeName", source = "feeType.name")
    BillItemResponse toBillItemResponse(BillItem billItem);

    List<BillItemResponse> toBillItemResponseList(List<BillItem> billItems);

    // Custom mapping cho feeTypeId
    @Named("mapFeeTypeIdToFeeType")
    default FeeType mapFeeTypeIdToFeeType(Long feeTypeId) {
        FeeType feeType = new FeeType();
        feeType.setId(feeTypeId);
        return feeType;
    }
}
