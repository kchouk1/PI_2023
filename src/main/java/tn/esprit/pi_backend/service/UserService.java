package tn.esprit.pi_backend.service;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import tn.esprit.pi_backend.entities.Role;
import tn.esprit.pi_backend.entities.User;
import tn.esprit.pi_backend.repositories.RoleRepository;
import tn.esprit.pi_backend.repositories.UserRepository;

import javax.validation.Valid;
import java.nio.charset.Charset;
import java.util.Random;

@Service
@AllArgsConstructor
public class UserService implements IUser {

    UserRepository userRepository;

    RoleRepository roleRepository;
    public EmailService emailService;
    @Autowired
    public PasswordEncoder encoder;

    @Override
    public User ajouterUser(User user) {
        String otp = generateOTP();
        user.setPassword(encoder.encode(otp));
        System.out.println(user.toString());
        String subject = "Your HR one-time password (OTP)";
        String body = "Your OTP is: " + otp;
        emailService.sendFormationAddedEmail(user.getEmail(),body);
                //sendEmail(user.getEmail(), subject, body);
        return userRepository.save(user);
    }

    @Override
    public User updateUser(User user) {
        if (user.getPassword() != null) {
            return userRepository.save(user);

        }


        return user;
    }
    public long getUserCount() {
        return userRepository.count();
    }
    @Override
    public User removeuser(Long id) {

        userRepository.deleteById(id);
        return null;
    }


    @Override
    public int countByRoleAdmin() {
        Role role = roleRepository.findById(2).get();
        return userRepository.countByRoles(role);
    }

    @Override
    public int countByRoleUser() {
        Role role = roleRepository.findById(1).get();
        return userRepository.countByRoles(role);
    }

    public User blockUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        user.setBlocked(true);

        return userRepository.save(user);
    }

    public User unblockUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        user.setBlocked(false);

        return userRepository.save(user);
    }

    public String generateOTP() {
        // Logic to generate OTP
        int leftLimit = 97; // letter 'a'
        int rightLimit = 122; // letter 'z'
        int targetStringLength = 10;
        Random random = new Random();
        StringBuilder buffer = new StringBuilder(targetStringLength);
        for (int i = 0; i < targetStringLength; i++) {
            int randomLimitedInt = leftLimit + (int)
                    (random.nextFloat() * (rightLimit - leftLimit + 1));
            buffer.append((char) randomLimitedInt);
        }
        String generatedString = buffer.toString();
        return generatedString;
    }

}

