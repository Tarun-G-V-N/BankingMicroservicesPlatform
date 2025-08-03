package com.smartbank.customerservice.queries.handler;

import com.smartbank.customerservice.dtos.CustomerDTO;
import com.smartbank.customerservice.queries.FindCustomerQuery;
import com.smartbank.customerservice.services.ICustomerService;
import lombok.RequiredArgsConstructor;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CustomerQueryHandler {
    private final ICustomerService customerService;
    @QueryHandler
    public CustomerDTO findCustomer(FindCustomerQuery findCustomerQuery) {
        return customerService.fetchCustomer(findCustomerQuery.getMobileNumber());
    }
}
