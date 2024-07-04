package com.sample.customer.domain.model;

import com.sample.customer.domain.enums.Language;
import com.sample.customer.domain.enums.Nationality;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
public class Customer extends BaseModel implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private Nationality nationality;

    @Enumerated(EnumType.STRING)
    private Language language;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "PREFIX_ID")
    private Prefix prefix;

    private String firstName;

    private String lastName;

    private String personalId;

    private String email;

    private String mobile;

    @Temporal(TemporalType.TIMESTAMP)
    private Date birthDate;

}
