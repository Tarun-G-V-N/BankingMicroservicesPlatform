package com.smartbank.messageservice.service;

import com.smartbank.messageservice.config.TwilioConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;


@Service
public class SmsService {

    @Autowired
    private TwilioConfig config;

    public void send(String to, String messageBody) {
        if (to == null || messageBody == null || to.isBlank() || messageBody.isBlank()) return;

        Message message = Message.creator(
                new PhoneNumber(to),
                new PhoneNumber(config.getPhoneNumber()),
                messageBody
        ).create();
    }
}

