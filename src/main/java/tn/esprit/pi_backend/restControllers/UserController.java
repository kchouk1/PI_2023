package tn.esprit.pi_backend.restControllers;

import lombok.AllArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import tn.esprit.pi_backend.entities.Role;
import tn.esprit.pi_backend.entities.User;
import tn.esprit.pi_backend.enums.ERole;
import tn.esprit.pi_backend.payload.MessageResponse;
import tn.esprit.pi_backend.repositories.RoleRepository;
import tn.esprit.pi_backend.repositories.UserRepository;
import tn.esprit.pi_backend.service.IUser;
import tn.esprit.pi_backend.service.UserService;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.transaction.Transactional;
import javax.validation.Valid;

@CrossOrigin(maxAge = 3600)
@AllArgsConstructor
@RestController
@RequestMapping("/users")
public class UserController {

	@Autowired
    IUser iUser;
    UserService userService;
    
    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @GetMapping("/current")
    public Optional<User> getCurrentUser(Authentication authentication){
        return userRepository.findByUsername(authentication.getName());
    }
    @GetMapping()
    public List<User> getAllUsers(){
        return userRepository.findAll();
    }


    @GetMapping("/count")
    public long getUserCount() {
        return userService.getUserCount();
    }
    
    @Transactional
    @PostMapping("ajouterUser")
    ResponseEntity<?> ajouterUser(@RequestBody User user){
        if (userRepository.existsByUsername(user.getUsername())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Username is already taken!"));
        }
        if (userRepository.existsByEmail(user.getEmail())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Email is already in use!"));
        }
        Role userRole = roleRepository.findByName(ERole.ROLE_USER)
                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
        Set<Role> roles = new HashSet<>();
        roles.add(userRole);
        user.setRoles(roles);
        return ResponseEntity.status(HttpStatus.CREATED).body(iUser.ajouterUser(user));
    }

    @PostMapping("/reset")
    public ResponseEntity<?> resetPassword(@Valid @RequestBody String email) {
        User user= userRepository.findByEmail(email);
        if(user != null) {
            String otp = userService.generateOTP();
            user.setPassword(userService.encoder.encode(otp));
            String subject = "Your HR  password ";
            String body = "Your password is: " + otp;
            userService.emailService.sendFormationAddedEmail(user.getEmail(),body);
            //sendEmail(user.getEmail(), subject, body);
            return new ResponseEntity<User>(userRepository.save(user), HttpStatus.ACCEPTED);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @Transactional
    @PutMapping("updateUser/{id}")
    public User updateUser(@PathVariable(value = "id", required = false) final Long id, @RequestBody User user) {
        System.out.println("password is : "+user.getPassword());

        if(user.getPassword() == null || user.getPassword().isEmpty()){

            User userTMP = userRepository.findByUsername(user.getUsername()).get();
            user.setPassword(userTMP.getPassword());
    	    User result = userRepository.save(user);
            System.out.println("password null");
            return result;

        }
        else{
            user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));

            User result = userRepository.save(user);
            System.out.println("password nottt null");

            return result;

        }
    }


    @Transactional
    @PutMapping("updateUserPassword/{id}")
    public User updateUserPassword(@PathVariable(value = "id", required = false) final Long id, @RequestBody String password) {
            User user = userRepository.findById(id).get();
            user.setPassword(new BCryptPasswordEncoder().encode(password));
            User result = userRepository.save(user);
            return result;
    }
    @GetMapping("/count/admin")
    public int countByRoleAdmin(){
        return   iUser.countByRoleAdmin();
    }
    @GetMapping("/count/user")
    public int countByRoleUser(){
        return   iUser.countByRoleUser();
    }

    @PutMapping("/{userId}/block")
    public ResponseEntity<User> blockUser(@PathVariable Long userId) {
        User blockedUser = userService.blockUser(userId);
        return ResponseEntity.ok(blockedUser);
    }

    @PutMapping("/{userId}/unblock")
    public ResponseEntity<User> unblockUser(@PathVariable Long userId) {
        User unblockedUser = userService.unblockUser(userId);
        return ResponseEntity.ok(unblockedUser);
    }

    @Transactional
    @DeleteMapping("/removeuser/{id}")
    public void removefeedback(@PathVariable("id") long id) {

        iUser.removeuser(id);

    }
    @GetMapping("/conge/user/{userId}")
    public User getUserId(@PathVariable("userId") Long userId){
        return userRepository.getUserById(userId);
    }
}
