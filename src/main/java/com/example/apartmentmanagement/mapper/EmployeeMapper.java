package com.example.apartmentmanagement.mapper;

import com.example.apartmentmanagement.dto.request.EmployeeRequest;
import com.example.apartmentmanagement.dto.response.EmployeeResponse;
import com.example.apartmentmanagement.dto.response.EmployeeSummaryResponse;
import com.example.apartmentmanagement.entity.Employee;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface EmployeeMapper {

    @Mapping(target = "level", ignore = true)
    @Mapping(target = "user", ignore = true)
    Employee toEntity(EmployeeRequest request);

    @Mapping(source = "level.name", target = "levelName")
    @Mapping(source = "user.username", target = "username")
    EmployeeResponse toResponse(Employee employee);

    List<EmployeeResponse> toResponseList(List<Employee> employees);

    @Mapping(source = "level.name", target = "levelName")
    EmployeeSummaryResponse toSummary(Employee employee);

    List<EmployeeSummaryResponse> toSummaryList(List<Employee> employees);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "level", ignore = true)
    @Mapping(target = "user", ignore = true)
    void updateEntityFromRequest(@MappingTarget Employee employee, EmployeeRequest request);
}
