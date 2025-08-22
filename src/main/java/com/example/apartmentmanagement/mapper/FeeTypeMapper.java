package com.example.apartmentmanagement.mapper;

import com.example.apartmentmanagement.dto.request.FeeTypeRequest;
import com.example.apartmentmanagement.dto.response.FeeTypeResponse;
import com.example.apartmentmanagement.entity.FeeType;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface FeeTypeMapper {

    //@Mapping(target = "metered", source = "metered")
    FeeType toEntity(FeeTypeRequest request);

    //@Mapping(target = "metered", source = "metered")
    FeeTypeResponse toResponse(FeeType entity);
}
