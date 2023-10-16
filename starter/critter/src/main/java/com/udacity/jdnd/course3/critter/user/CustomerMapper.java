package com.udacity.jdnd.course3.critter.user;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CustomerMapper {

    CustomerMapper MAPPER = Mappers.getMapper(CustomerMapper.class);
    CustomerDTO map(Customer Customer);
    Customer map(CustomerDTO CustomerDTO);
    List<CustomerDTO> map(List<Customer> Customers);
}
