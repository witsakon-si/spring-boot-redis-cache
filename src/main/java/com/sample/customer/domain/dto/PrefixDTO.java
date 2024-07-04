package com.sample.customer.domain.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class PrefixDTO implements Serializable {
    private Long id;
    private String code;
    private String nameTh;
    private String nameEn;
}
