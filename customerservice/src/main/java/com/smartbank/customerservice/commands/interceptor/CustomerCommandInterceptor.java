package com.smartbank.customerservice.commands.interceptor;

import com.smartbank.common.commands.UpdateCustomerMobileNumberCommand;
import com.smartbank.customerservice.commands.CreateCustomerCommand;
import com.smartbank.customerservice.commands.DeleteCustomerCommand;
import com.smartbank.customerservice.commands.UpdateCustomerCommand;
import com.smartbank.customerservice.constants.CustomerConstants;
import com.smartbank.customerservice.entities.Customer;
import com.smartbank.customerservice.exceptions.CustomerAlreadyExistsException;
import com.smartbank.customerservice.exceptions.ResourceNotFoundException;
import com.smartbank.customerservice.repositories.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.axonframework.commandhandling.CommandMessage;
import org.axonframework.messaging.MessageDispatchInterceptor;
import org.springframework.stereotype.Component;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.Optional;
import java.util.function.BiFunction;

@Component
@RequiredArgsConstructor
public class CustomerCommandInterceptor implements MessageDispatchInterceptor<CommandMessage<?>> {
    private final CustomerRepository customerRepository;
    @Nonnull
    @Override
    public BiFunction<Integer, CommandMessage<?>, CommandMessage<?>> handle(@Nonnull List<? extends CommandMessage<?>> messages) {
        return (index, command) -> {
            if(CreateCustomerCommand.class.equals(command.getPayloadType())) {
                CreateCustomerCommand createCustomerCommand = (CreateCustomerCommand) command.getPayload();
                Optional<Customer> optionalCustomer = customerRepository.findByMobileNumberAndIsActive(createCustomerCommand.getMobileNumber(), CustomerConstants.ACTIVE_SW);
                if(optionalCustomer.isPresent()) throw new CustomerAlreadyExistsException("Customer with mobile number " + createCustomerCommand.getMobileNumber() + " already exists");
            } else if (UpdateCustomerCommand.class.equals(command.getPayloadType())) {
                UpdateCustomerCommand updateCustomerCommand = (UpdateCustomerCommand) command.getPayload();
                Customer customer = customerRepository.findByMobileNumberAndIsActive(updateCustomerCommand.getMobileNumber(), CustomerConstants.ACTIVE_SW)
                        .orElseThrow(() -> new ResourceNotFoundException("Customer", "mobileNumber", updateCustomerCommand.getMobileNumber()));
            } else if (DeleteCustomerCommand.class.equals(command.getPayloadType())) {
                DeleteCustomerCommand deleteCustomerCommand = (DeleteCustomerCommand) command.getPayload();
                Customer customer = customerRepository.findByCustomerIdAndIsActive(deleteCustomerCommand.getCustomerId(), CustomerConstants.ACTIVE_SW)
                        .orElseThrow(() -> new ResourceNotFoundException("Customer", "customerId", deleteCustomerCommand.getCustomerId()));
            } else if (UpdateCustomerMobileNumberCommand.class.equals(command.getPayloadType())) {
                UpdateCustomerMobileNumberCommand updateCustomerMobileNumberCommand = (UpdateCustomerMobileNumberCommand) command.getPayload();
                Customer customer = customerRepository.findByMobileNumberAndIsActive(updateCustomerMobileNumberCommand.getMobileNumber(), CustomerConstants.ACTIVE_SW)
                        .orElseThrow(() -> new ResourceNotFoundException("Customer", "mobileNumber", updateCustomerMobileNumberCommand.getMobileNumber()));
            }
            return command;
        };
    }
}
