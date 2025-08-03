package com.smartbank.customerservice.services.impl;

import com.smartbank.common.events.CustomerDataChangedEvent;
import com.smartbank.customerservice.commands.events.CustomerUpdatedEvent;
import com.smartbank.customerservice.constants.CustomerConstants;
import com.smartbank.customerservice.dtos.*;
import com.smartbank.customerservice.entities.Customer;
import com.smartbank.customerservice.exceptions.CustomerAlreadyExistsException;
import com.smartbank.customerservice.exceptions.ResourceNotFoundException;
import com.smartbank.customerservice.mappers.CustomerMapper;
import com.smartbank.customerservice.repositories.CustomerRepository;
import com.smartbank.customerservice.services.ICustomerService;
import lombok.AllArgsConstructor;
import org.axonframework.eventhandling.gateway.EventGateway;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class CustomerService implements ICustomerService {
    private CustomerRepository customerRepository;
    private EventGateway eventGateway;

    @Override
    public void createCustomer(Customer customer) {
        customer.setActive(CustomerConstants.ACTIVE_SW);
        Optional<Customer> optionalCustomer = customerRepository.findByMobileNumberAndIsActive(customer.getMobileNumber(), customer.isActive());
        if (optionalCustomer.isPresent()) throw new CustomerAlreadyExistsException("Customer already exists with mobile number " + customer.getMobileNumber());
        customerRepository.save(customer);
    }

    @Override
    public CustomerDTO fetchCustomer(String mobileNumber) {
        Customer customer = customerRepository.findByMobileNumberAndIsActive(mobileNumber, CustomerConstants.ACTIVE_SW)
                .orElseThrow(() -> new ResourceNotFoundException("Customer", "mobileNumber", mobileNumber));
        return CustomerMapper.mapToCustomerDto(customer, new CustomerDTO());
    }

    @Override
    public boolean updateCustomer(CustomerUpdatedEvent customerUpdatedEvent) {
        Customer customer = customerRepository.findByMobileNumberAndIsActive(customerUpdatedEvent.getMobileNumber(), CustomerConstants.ACTIVE_SW)
                .orElseThrow(() -> new ResourceNotFoundException("Customer", "mobileNumber", customerUpdatedEvent.getMobileNumber()));
        CustomerMapper.mapEventToCustomer(customerUpdatedEvent, customer);
        customerRepository.save(customer);
        return true;
    }

    @Override
    public boolean deleteCustomer(String customerId) {
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new ResourceNotFoundException("Customer", "customerId", customerId));
        customer.setActive(CustomerConstants.IN_ACTIVE_SW);
        customerRepository.save(customer);
        CustomerDataChangedEvent customerDataChangedEvent = new CustomerDataChangedEvent();
        customerDataChangedEvent.setName(customer.getName());
        customerDataChangedEvent.setMobileNumber(customer.getMobileNumber());
        customerDataChangedEvent.setActive(CustomerConstants.IN_ACTIVE_SW);
        eventGateway.publish(customerDataChangedEvent);
        return true;
    }

    @Override
    public boolean updateCustomerMobileNumber(String mobileNumber, String newMobileNumber) {
        Customer customer = customerRepository.findByMobileNumberAndIsActive(mobileNumber, CustomerConstants.ACTIVE_SW)
                .orElseThrow(() -> new ResourceNotFoundException("Customer", "mobileNumber", mobileNumber));
        customer.setMobileNumber(newMobileNumber);
        customerRepository.save(customer);
        return true;
    }
}
