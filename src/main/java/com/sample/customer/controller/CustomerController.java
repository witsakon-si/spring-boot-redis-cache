package com.sample.customer.controller;

import com.sample.customer.domain.dto.*;
import com.sample.customer.domain.enums.ResponseCode;
import com.sample.customer.domain.exception.DataNotFoundException;
import com.sample.customer.domain.model.Customer;
import com.sample.customer.domain.service.CustomerService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/customers")
public class CustomerController extends BaseController {

    private final CustomerService customerService;
    private final ModelMapper modelMapper;

    @Autowired
    public CustomerController(CustomerService customerService, ModelMapper modelMapper) {
        this.customerService = customerService;
        this.modelMapper = modelMapper;
    }

    @GetMapping(value = "")
    public ResponseEntity<CustomerListResDTO> getCustomerList() {
        CustomerListResDTO responseDTO = new CustomerListResDTO();
        try {
            List<CustomerDTO> customerDTOList = customerService.getAllCustomer();
            responseDTO.setResult(customerDTOList);

            responseDTO.setResponseCode(ResponseCode.COMPLETE);
            responseDTO.setMessage("search customer list is completed.");
            return ResponseEntity.status(HttpStatus.OK).body(responseDTO);
        } catch (Exception e) {
            e.printStackTrace();
            responseDTO.setResponseCode(ResponseCode.EXCEPTION);
            responseDTO.setMessage("unexpected Exception. Please contact your system administrator.");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseDTO);
        }
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<CustomerResDTO> getCustomer(@PathVariable Long id) {
        log.debug("getCustomer: {}", id);
        CustomerResDTO responseDTO = new CustomerResDTO();
        try {
            log.debug("start call service");
            Customer customer = customerService.getCustomer(id);
            CustomerDTO customerDTO = modelMapper.map(customer, CustomerDTO.class);
            log.debug("end call service");
            responseDTO.setResult(customerDTO);

            responseDTO.setResponseCode(ResponseCode.COMPLETE);
            responseDTO.setMessage("search customer is completed.");
            return ResponseEntity.status(HttpStatus.OK).body(responseDTO);
        } catch (DataNotFoundException e) {
            log.error("getCustomer: {}", e.getMessage());
            responseDTO.setResponseCode(ResponseCode.DATA_NOT_FOUND);
            responseDTO.setMessage(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseDTO);
        } catch (Exception e) {
            e.printStackTrace();
            responseDTO.setResponseCode(ResponseCode.EXCEPTION);
            responseDTO.setMessage("unexpected Exception. Please contact your system administrator.");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseDTO);
        }
    }

    @PostMapping
    public ResponseEntity<ResponseDTO> createNewCustomer(@RequestBody CustomerSettingDTO customerSettingDTO) {
        log.debug("createNewCustomer: {}", customerSettingDTO);
        ResponseDTO responseDTO = new ResponseDTO();
        try {
            customerService.createNewCustomer(customerSettingDTO, getUsername());

            responseDTO.setResponseCode(ResponseCode.COMPLETE);
            responseDTO.setMessage("create customer complete.");
            return ResponseEntity.status(HttpStatus.OK).body(responseDTO);
        } catch (DataNotFoundException e) {
            log.error("createNewCustomer: {}", e.getMessage());
            responseDTO.setResponseCode(ResponseCode.DATA_NOT_FOUND);
            responseDTO.setMessage(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseDTO);
        } catch (Exception e) {
            e.printStackTrace();
            responseDTO.setResponseCode(ResponseCode.EXCEPTION);
            responseDTO.setMessage("unexpected Exception. Please contact your system administrator.");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseDTO);
        }
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<ResponseDTO> updateCustomer(@PathVariable Long id, @RequestBody CustomerSettingDTO customerSettingDTO) {
        log.debug("updateCustomer: {}", customerSettingDTO);
        ResponseDTO responseDTO = new ResponseDTO();
        try {
            // validate
            if (id == null) {
                responseDTO.setResponseCode(ResponseCode.VALIDATION_FAILED);
                responseDTO.setMessage("please specify customer id");
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseDTO);
            }

            log.debug("start call service");
            customerService.updateCustomer(id, customerSettingDTO, getUsername());
            log.debug("end call service");

            responseDTO.setResponseCode(ResponseCode.COMPLETE);
            responseDTO.setMessage("update customer complete.");
            return ResponseEntity.status(HttpStatus.OK).body(responseDTO);
        } catch (DataNotFoundException e) {
            log.error("updateCustomer: {}", e.getMessage());
            responseDTO.setResponseCode(ResponseCode.DATA_NOT_FOUND);
            responseDTO.setMessage(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseDTO);
        } catch (Exception e) {
            e.printStackTrace();
            responseDTO.setResponseCode(ResponseCode.EXCEPTION);
            responseDTO.setMessage("unexpected Exception. Please contact your system administrator.");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseDTO);
        }
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<ResponseDTO> deleteCustomer(@PathVariable Long id) {
        ResponseDTO responseDTO = new ResponseDTO();
        try {
            // validate
            if (id == null) {
                responseDTO.setResponseCode(ResponseCode.VALIDATION_FAILED);
                responseDTO.setMessage("please specify customer id");
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseDTO);
            }

            customerService.deleteCustomer(id, getUsername());

            responseDTO.setResponseCode(ResponseCode.COMPLETE);
            responseDTO.setMessage("delete customer complete.");
            return ResponseEntity.status(HttpStatus.OK).body(responseDTO);
        } catch (DataNotFoundException e) {
            log.error("deleteCustomer: {}", e.getMessage());
            responseDTO.setResponseCode(ResponseCode.DATA_NOT_FOUND);
            responseDTO.setMessage(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseDTO);
        } catch (Exception e) {
            e.printStackTrace();
            responseDTO.setResponseCode(ResponseCode.EXCEPTION);
            responseDTO.setMessage("unexpected Exception. Please contact your system administrator.");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseDTO);
        }
    }

}
