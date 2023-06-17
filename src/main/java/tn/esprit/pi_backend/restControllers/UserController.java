package tn.esprit.pi_backend.restControllers;

import lombok.AllArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.esprit.pi_backend.entities.User_Users;
import tn.esprit.pi_backend.repositories.UserRepository;
import tn.esprit.pi_backend.service.IUser;
import tn.esprit.pi_backend.service.UserService;

import java.util.List;

import javax.transaction.Transactional;

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
    
    @GetMapping()
    public List<User_Users> getAllUsers(){
        return userRepository.findAll();
    }
    
    @Transactional
    @PostMapping("ajouterUser")
    User_Users ajouterUser(@RequestBody User_Users user){
        return iUser.ajouterUser(user);
    }


    @Transactional
    @PutMapping("updateUser/{id}")
    public User_Users updateUser(@PathVariable(value = "id", required = false) final Long id, @RequestBody User_Users user) {
    	User_Users result = userRepository.save(user);
        return result;
    }

    @PutMapping("/{userId}/block")
    public ResponseEntity<User_Users> blockUser(@PathVariable Long userId) {
        User_Users blockedUser = userService.blockUser(userId);
        return ResponseEntity.ok(blockedUser);
    }

    @PutMapping("/{userId}/unblock")
    public ResponseEntity<User_Users> unblockUser(@PathVariable Long userId) {
        User_Users unblockedUser = userService.unblockUser(userId);
        return ResponseEntity.ok(unblockedUser);
    }



    @Transactional
    @DeleteMapping("/removeuser/{id}")
    public void removefeedback(@PathVariable("id") long id) {

        iUser.removeuser(id);

    }





}
