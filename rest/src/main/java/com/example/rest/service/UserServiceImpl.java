package com.example.rest.service;

import com.example.rest.model.User;
import com.example.rest.repository.UserRepository;
import com.example.rest.util.CustomPasswordEncoder;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class UserServiceImpl implements UserService {


    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CustomPasswordEncoder passwordEncoder;


    @Override
    public User addUser(User user) {
        if (isUserExistsInDb(user)) {
	   throw new IllegalStateException(String.format("User %s already exist in Db ",
		  user.getName()));
        }
        User userWithEncodedPassword = encodePassword(user);

        userRepository.addUser(userWithEncodedPassword);

        user.getPermissions().stream().forEach(permission -> {
	   userRepository.addUserPermission(user.getName(), user.getEmail(), permission.name());
        });

        return user;
    }

    @Override
    public User getUserByEmail(String email) {
        return userRepository.getUserByEmail(email);
    }


    private Boolean isUserExistsInDb(User user) {
        User userByEmail = userRepository.getUserByEmail(user.getEmail());
        return userByEmail != null;
    }

    @Override
    public List<User> getBlockedUsers() {
        return userRepository.getBlockedUsers();
    }

    private User encodePassword(User user) {
        String encodedPassword = passwordEncoder.encode(user.getPassword());

        user.setPassword(encodedPassword);

        return user;
    }


}
