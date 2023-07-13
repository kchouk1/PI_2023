package tn.esprit.pi_backend.service;

import com.twilio.Twilio;
import org.springframework.beans.factory.annotation.Autowired;
import tn.esprit.pi_backend.config.TwilioConfig;

public class MyService {

    private final TwilioConfig twilioConfig;

    @Autowired
    public MyService(TwilioConfig twilioConfig) {
        this.twilioConfig = twilioConfig;
    }

    public void someMethod() {
        Twilio.init(twilioConfig.getAccountSid(), twilioConfig.getAuthToken());

        // Use Twilio APIs for sending SMS, making calls, etc.
    }
}


