package com.sample.customer.domain.mapper;

import com.sample.customer.domain.dto.CustomerSettingDTO;
import com.sample.customer.domain.model.Customer;
import org.modelmapper.PropertyMap;

public class CustomerMapper extends PropertyMap<CustomerSettingDTO, Customer> {

    @Override
    protected void configure() {
        skip(destination.getId());
    }
}
