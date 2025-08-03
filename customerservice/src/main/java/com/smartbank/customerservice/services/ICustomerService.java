package com.smartbank.customerservice.services;

import com.smartbank.customerservice.commands.events.CustomerUpdatedEvent;
import com.smartbank.customerservice.dtos.CustomerDTO;
import com.smartbank.customerservice.dtos.CustomerDetailsDTO;
import com.smartbank.customerservice.entities.Customer;

public interface ICustomerService {

    void createCustomer(Customer customer);
    CustomerDTO fetchCustomer(String mobileNumber);
    boolean updateCustomer(CustomerUpdatedEvent customerUpdatedEvent);
    boolean deleteCustomer(String customerId);
    boolean updateCustomerMobileNumber(String mobileNumber, String newMobileNumber);
}
