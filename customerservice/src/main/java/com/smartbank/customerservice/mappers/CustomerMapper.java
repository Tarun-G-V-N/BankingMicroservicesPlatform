package com.smartbank.customerservice.mappers;

import com.smartbank.customerservice.commands.events.CustomerUpdatedEvent;
import com.smartbank.customerservice.dtos.CustomerDTO;
import com.smartbank.customerservice.entities.Customer;

public class CustomerMapper {
    public static CustomerDTO mapToCustomerDto(Customer customer, CustomerDTO customerDto) {
        customerDto.setCustomerId(customer.getCustomerId());
        customerDto.setName(customer.getName());
        customerDto.setEmail(customer.getEmail());
        customerDto.setMobileNumber(customer.getMobileNumber());
        customerDto.setActive(customer.isActive());
        return customerDto;
    }

    public static Customer mapToCustomer(CustomerDTO customerDto, Customer customer) {
        customer.setCustomerId(customerDto.getCustomerId());
        customer.setName(customerDto.getName());
        customer.setEmail(customerDto.getEmail());
        customer.setMobileNumber(customerDto.getMobileNumber());
        if(customerDto.isActive()) {
            customer.setActive(customerDto.isActive());
        }
        return customer;
    }

    public static Customer mapEventToCustomer(CustomerUpdatedEvent customerUpdatedEvent, Customer customer) {
        customer.setName(customerUpdatedEvent.getName());
        customer.setEmail(customerUpdatedEvent.getEmail());
        return customer;
    }
}
