package com.example.project_management_system.controller;

import com.example.project_management_system.model.User;
import com.example.project_management_system.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("/profile")

 private ResponseEntity<User> getUserProfile(@RequestHeader ("Authorization")String jwt) throws Exception {
     User user = userService.findUserProfileByJwt(jwt);
     return new ResponseEntity<>(user, HttpStatus.OK);
 }

}
