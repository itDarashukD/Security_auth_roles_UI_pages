package com.example.rest.controller;

import com.example.rest.model.User;
import com.example.rest.service.UserService;
import java.util.List;
import java.util.Random;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/security")
public class ApplicationController {

    @Autowired
    private UserService userService;

    @GetMapping("/info")
    public String getRandomInfo() {
        Random random = new Random();
        final int randomInt = random.nextInt(100);

        return "MVC application № " + randomInt;
    }

    @GetMapping("/about")
    public String getAbout() {
        return "Smth about app...";
    }

    @GetMapping("/admin")
    public String getAdmin() {
        return "Smth about app...";
    }

    @PostMapping("/addUser")
    public User addUser(@RequestBody User user) {
        return userService.addUser(user);
    }

    @GetMapping("/getUser/{email}")
    public User getUserByEmail(@PathVariable("email") String email) {

        return userService.getUserByEmail(email);
    }

    @GetMapping("/getBlockedUsers")
    public List<User> getBlockedUsers() {
        return userService.getBlockedUsers();
    }


}
