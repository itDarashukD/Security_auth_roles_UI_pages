package com.example.rest.service;

import com.example.rest.model.User;
import java.util.List;

public interface UserService {


    User addUser(User user);

    User getUserByEmail(String email);

    List<User> getBlockedUsers();


}
