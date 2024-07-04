package com.sample.customer.domain.dto;

import com.sample.customer.domain.enums.ResponseCode;
import lombok.Data;

@Data
public class ResponseDTO {
    private String message;
    private ResponseCode responseCode;
}
