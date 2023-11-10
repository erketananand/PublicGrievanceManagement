package com.scaler.notification.services;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class SmsService {

    @Value("${twilio.accountSid}")
    private String accountSid;

    @Value("${twilio.authToken}")
    private String authToken;

    @Value("${twilio.phoneNumber}")
    private String senderNumber; // This is your Twilio number

    // This method will be called once, during the bean instantiation
    @PostConstruct
    public void initTwilio() {
        Twilio.init(accountSid, authToken);
    }

    public void sendSms(String recipientNumber, String messageText) {
       Message message = Message.creator(
               new PhoneNumber(recipientNumber),
               new PhoneNumber(senderNumber),
               messageText).create();
       // Log the message SID or any other details if needed
       System.out.println("Message sent successfully with SID: " + message.getSid());
    }
}
