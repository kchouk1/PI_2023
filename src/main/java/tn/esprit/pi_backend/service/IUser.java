package tn.esprit.pi_backend.service;

import tn.esprit.pi_backend.entities.User;

public interface IUser {

    User ajouterUser(User user);
    User updateUser (User user);
    User removeuser(Long  id);

}
