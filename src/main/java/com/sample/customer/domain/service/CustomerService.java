package com.sample.customer.domain.service;

import com.sample.customer.domain.dto.CustomerDTO;
import com.sample.customer.domain.dto.CustomerSettingDTO;
import com.sample.customer.domain.exception.DataNotFoundException;
import com.sample.customer.domain.model.Customer;
import com.sample.customer.domain.model.Prefix;
import com.sample.customer.domain.repository.CustomerRepository;
import com.sample.customer.domain.util.DateTimeUtil;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@Transactional(rollbackFor = Exception.class)
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final ModelMapper modelMapper;

    private final PrefixService prefixService;

    public CustomerService(CustomerRepository customerRepository, ModelMapper modelMapper, PrefixService prefixService) {
        this.customerRepository = customerRepository;
        this.modelMapper = modelMapper;
        this.prefixService = prefixService;
    }

    public List<CustomerDTO> getAllCustomer() {
        List<Customer> customerList = customerRepository.findByActive(true);
        return modelMapper.map(customerList, new TypeToken<List<CustomerDTO>>() {
        }.getType());
    }

    @Cacheable(value = "customer", key = "#customerId", unless = "#result==null")
    public Customer getCustomer(Long customerId) throws DataNotFoundException {
        log.debug("retrieve customer data");
        return customerRepository.findByIdAndActive(customerId, true).orElseThrow(()
                -> new DataNotFoundException("customer id {" + customerId + "} doesn't exist."));
    }

    public void createNewCustomer(CustomerSettingDTO customerSettingDTO, String username) throws DataNotFoundException {
        Date now = DateTimeUtil.now();
        Customer customer = modelMapper.map(customerSettingDTO, Customer.class);

        Prefix prefix = prefixService.getPrefix(customerSettingDTO.getPrefix());
        customer.setPrefix(prefix);

        customer.setCreateDate(now);
        customer.setCreateBy(username);
        customerRepository.save(customer);
    }

    @CachePut(value = "customer", key = "#customerId")
    public Optional<Customer> updateCustomer(Long customerId, CustomerSettingDTO customerSettingDTO, String username) throws DataNotFoundException {
        log.debug("update customer data");
        Date now = DateTimeUtil.now();
        Customer customer = getCustomer(customerId);
        modelMapper.map(customerSettingDTO, customer);

        Prefix prefix = prefixService.getPrefix(customerSettingDTO.getPrefix());
        customer.setPrefix(prefix);

        customer.setModifyDate(now);
        customer.setModifyBy(username);
        return Optional.of(customerRepository.save(customer));
    }

    @CacheEvict(value = "customer", allEntries = true)
    public void deleteCustomer(Long customerId, String username) throws DataNotFoundException {
        log.debug("update customer data into inactive");
        Date now = DateTimeUtil.now();
        Customer customer = getCustomer(customerId);
        customer.setActive(false);
        customer.setModifyDate(now);
        customer.setModifyBy(username);
    }
}
