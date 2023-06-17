package tn.esprit.pi_backend.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import tn.esprit.pi_backend.entities.User_Users;
import tn.esprit.pi_backend.repositories.UserRepository;

@Service
@AllArgsConstructor
public class UserService implements IUser{

    UserRepository userRepository;
    @Override
    public User_Users ajouterUser(User_Users user) {

        return userRepository.save(user);
    }

    @Override
    public User_Users updateUser(User_Users user) {
        if (user.getPassword() != null) {
            return userRepository.save(user);

        }


        return user;
    }
    @Override
    public User_Users removeuser(Long id) {

        userRepository.deleteById(id);
        return null;
    }


    public User_Users blockUser(Long userId) {
        User_Users user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        user.setBlocked(true);

        return userRepository.save(user);
    }

    public User_Users unblockUser(Long userId) {
        User_Users user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        user.setBlocked(false);

        return userRepository.save(user);
    }


}

