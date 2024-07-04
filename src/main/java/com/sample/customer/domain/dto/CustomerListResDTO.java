package com.sample.customer.domain.dto;

import lombok.Data;

import java.util.List;

@Data
public class CustomerListResDTO extends ResponseDTO {
    private List<CustomerDTO> result;
}
