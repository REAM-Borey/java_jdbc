package model;

import model.dto.CustomerDto;
import model.entity.Customers;

public class Mapper {
    public static CustomerDto fromCustomerToCustomerDto(Customers customers){
        if(customers == null){
            return null;
        }
        return CustomerDto.builder()
                .name(customers.getName())
                .email(customers.getEmail())
                .id(customers.getId())
                .build();

    }
}
