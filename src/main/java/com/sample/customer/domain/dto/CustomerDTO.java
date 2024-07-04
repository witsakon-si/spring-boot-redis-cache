package com.sample.customer.domain.dto;

import com.sample.customer.domain.enums.Language;
import com.sample.customer.domain.enums.Nationality;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class CustomerDTO implements Serializable {
    private Long id;
    private Nationality nationality;
    private Language language;
    private PrefixDTO prefix;
    private String firstName;
    private String lastName;
    private String personalId;
    private String email;
    private String mobile;
    private Date birthDate;
}
