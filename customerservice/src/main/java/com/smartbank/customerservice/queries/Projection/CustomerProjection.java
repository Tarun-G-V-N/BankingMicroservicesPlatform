package com.smartbank.customerservice.queries.Projection;

import com.smartbank.common.events.CustomerMobileNumberRollbackedEvent;
import com.smartbank.common.events.CustomerMobileNumberUpdatedEvent;
import com.smartbank.customerservice.commands.events.CustomerCreatedEvent;
import com.smartbank.customerservice.commands.events.CustomerDeletedEvent;
import com.smartbank.customerservice.commands.events.CustomerUpdatedEvent;
import com.smartbank.customerservice.entities.Customer;
import com.smartbank.customerservice.services.ICustomerService;
import lombok.RequiredArgsConstructor;
import org.axonframework.config.ProcessingGroup;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@ProcessingGroup("customer-group")
public class CustomerProjection {
    private final ICustomerService customerService;

    @EventHandler
    public void on(CustomerCreatedEvent customerCreatedEvent)  {
        Customer customerEntity = new Customer();
        BeanUtils.copyProperties(customerCreatedEvent, customerEntity);
        customerService.createCustomer(customerEntity);
    }

    @EventHandler
    public void on(CustomerUpdatedEvent customerUpdatedEvent)  {
        customerService.updateCustomer(customerUpdatedEvent);
    }

    @EventHandler
    public void on(CustomerDeletedEvent customerDeletedEvent)  {
        customerService.deleteCustomer(customerDeletedEvent.getCustomerId());
    }

    @EventHandler
    public void on(CustomerMobileNumberUpdatedEvent customerMobileNumberUpdatedEvent) {
        customerService.updateCustomerMobileNumber(customerMobileNumberUpdatedEvent.getMobileNumber(), customerMobileNumberUpdatedEvent.getNewMobileNumber());
    }

    @EventHandler
    public void on(CustomerMobileNumberRollbackedEvent customerMobileNumberRollbackedEvent) {
        customerService.updateCustomerMobileNumber(customerMobileNumberRollbackedEvent.getNewMobileNumber(), customerMobileNumberRollbackedEvent.getMobileNumber());
    }
}
