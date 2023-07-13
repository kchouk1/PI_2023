package tn.esprit.pi_backend.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.beans.factory.annotation.Value;
@Configuration
public class TwilioConfig {
    @Value("ACd7086ad292bed93eaae50de57b1684fb")
    private String accountSid;

    @Value("d4702c7e2d3ed6556bc2af3c5b3b49e5")
    private String authToken;

    public String getAccountSid() {
        return accountSid;
    }

    public String getAuthToken() {
        return authToken;
    }
}
