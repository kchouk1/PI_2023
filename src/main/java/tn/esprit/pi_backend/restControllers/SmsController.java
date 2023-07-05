package tn.esprit.pi_backend.restControllers;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
@CrossOrigin(origins = {"http://127.0.0.1:4200","http://localhost:4200"})
@RestController
@RequestMapping("/sms")
public class SmsController {


    @PostMapping
    public ResponseEntity<String> sendSMS() {

        Twilio.init(System.getenv("ACd7086ad292bed93eaae50de57b1684fb"), System.getenv("d4702c7e2d3ed6556bc2af3c5b3b49e5"));


        Message.creator(new PhoneNumber("+21658046046"),
                new PhoneNumber("+15416354179"), "Hello from Twilio 📞").create();

        return new ResponseEntity<String>("Message sent successfully", HttpStatus.OK);
    }


}
