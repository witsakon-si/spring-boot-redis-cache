package com.sample.customer.infrastracture.config;

import com.sample.customer.domain.mapper.CustomerMapper;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApplicationConfig {

    @Bean
    public ModelMapper getModelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.addMappings(new CustomerMapper());
        return modelMapper;
    }
}
