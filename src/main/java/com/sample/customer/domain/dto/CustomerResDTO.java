package com.sample.customer.domain.dto;

import lombok.Data;


@Data
public class CustomerResDTO extends ResponseDTO {
    private CustomerDTO result;
}
