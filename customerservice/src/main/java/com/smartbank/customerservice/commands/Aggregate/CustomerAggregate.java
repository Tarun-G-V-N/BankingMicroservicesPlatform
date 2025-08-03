package com.smartbank.customerservice.commands.Aggregate;

import com.smartbank.common.commands.RollbackCustomerMobileNumberCommand;
import com.smartbank.common.commands.UpdateCustomerMobileNumberCommand;
import com.smartbank.common.events.CustomerDataChangedEvent;
import com.smartbank.common.events.CustomerMobileNumberRollbackedEvent;
import com.smartbank.common.events.CustomerMobileNumberUpdatedEvent;
import com.smartbank.customerservice.commands.CreateCustomerCommand;
import com.smartbank.customerservice.commands.DeleteCustomerCommand;
import com.smartbank.customerservice.commands.UpdateCustomerCommand;
import com.smartbank.customerservice.commands.events.CustomerCreatedEvent;
import com.smartbank.customerservice.commands.events.CustomerDeletedEvent;
import com.smartbank.customerservice.commands.events.CustomerUpdatedEvent;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;
import org.springframework.beans.BeanUtils;

//@Aggregate(snapshotTriggerDefinition = "customerSnapshotTrigger")
@Aggregate
public class CustomerAggregate {

    @AggregateIdentifier
    private String customerId;
    private String name;
    private String email;
    private String mobileNumber;
    private boolean isActive;
    private String errorMessage;

    protected CustomerAggregate() {}

    @CommandHandler
    public CustomerAggregate(CreateCustomerCommand createCustomerCommand) {
        CustomerCreatedEvent customerCreatedEvent = new CustomerCreatedEvent();
        BeanUtils.copyProperties(createCustomerCommand, customerCreatedEvent);
        CustomerDataChangedEvent customerDataChangedEvent = new CustomerDataChangedEvent();
        BeanUtils.copyProperties(createCustomerCommand, customerDataChangedEvent);
        AggregateLifecycle.apply(customerCreatedEvent);
        AggregateLifecycle.apply(customerDataChangedEvent);
    }

    @EventSourcingHandler
    public void on(CustomerCreatedEvent customerCreatedEvent) {
        this.customerId = customerCreatedEvent.getCustomerId();
        this.name = customerCreatedEvent.getName();
        this.email= customerCreatedEvent.getEmail();
        this.mobileNumber = customerCreatedEvent.getMobileNumber();
        this.isActive = customerCreatedEvent.isActive();
    }

    @CommandHandler
    public void handle(UpdateCustomerCommand updateCustomerCommand) {
//        Reading data from Event store.
//        List<?> commands = eventStore.readEvents(updateCustomerCommand.getCustomerId()).asStream().toList();
//        if(commands.isEmpty()) throw new ResourceNotFoundException("Customer", "customerId", updateCustomerCommand.getCustomerId());
        CustomerUpdatedEvent customerUpdatedEvent = new CustomerUpdatedEvent();
        BeanUtils.copyProperties(updateCustomerCommand, customerUpdatedEvent);
        CustomerDataChangedEvent customerDataChangedEvent = new CustomerDataChangedEvent();
        BeanUtils.copyProperties(updateCustomerCommand, customerDataChangedEvent);
        AggregateLifecycle.apply(customerUpdatedEvent);
        AggregateLifecycle.apply(customerDataChangedEvent);
    }

    @EventSourcingHandler
    public void on(CustomerUpdatedEvent customerUpdatedEvent) {
        this.name = customerUpdatedEvent.getName();
        this.email= customerUpdatedEvent.getEmail();
    }

    @CommandHandler
    public void handle(DeleteCustomerCommand deleteCustomerCommand) {
        CustomerDeletedEvent customerDeletedEvent = new CustomerDeletedEvent();
        BeanUtils.copyProperties(deleteCustomerCommand, customerDeletedEvent);
        AggregateLifecycle.apply(customerDeletedEvent);
    }

    @EventSourcingHandler
    public void on(CustomerDeletedEvent customerDeletedEvent) {
        this.isActive = customerDeletedEvent.isActive();
    }

    @CommandHandler
    public void handle(UpdateCustomerMobileNumberCommand updateCustomerMobileNumberCommand) {
        CustomerMobileNumberUpdatedEvent customerMobileNumberUpdatedEvent = new CustomerMobileNumberUpdatedEvent();
        BeanUtils.copyProperties(updateCustomerMobileNumberCommand, customerMobileNumberUpdatedEvent);
        AggregateLifecycle.apply(customerMobileNumberUpdatedEvent);
    }

    @EventSourcingHandler
    public void on(CustomerMobileNumberUpdatedEvent customerMobileNumberUpdatedEvent ) {
        this.mobileNumber = customerMobileNumberUpdatedEvent.getNewMobileNumber();
    }

    @CommandHandler
    public void handle(RollbackCustomerMobileNumberCommand rollbackCustomerMobileNumberCommand) {
        CustomerMobileNumberRollbackedEvent customerMobileNumberRollbackedEvent = new CustomerMobileNumberRollbackedEvent();
        BeanUtils.copyProperties(rollbackCustomerMobileNumberCommand, customerMobileNumberRollbackedEvent);
        AggregateLifecycle.apply(customerMobileNumberRollbackedEvent);
    }

    @EventSourcingHandler
    public void on(CustomerMobileNumberRollbackedEvent customerMobileNumberRollbackedEvent) {
        this.mobileNumber = customerMobileNumberRollbackedEvent.getMobileNumber();
        this.errorMessage = customerMobileNumberRollbackedEvent.getErrorMessage();
    }
}
