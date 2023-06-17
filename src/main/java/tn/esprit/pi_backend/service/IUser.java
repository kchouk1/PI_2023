package tn.esprit.pi_backend.service;

import tn.esprit.pi_backend.entities.User_Users;

public interface IUser {

    User_Users ajouterUser(User_Users user);
    User_Users updateUser (User_Users user);
    User_Users removeuser(Long  id);

}
