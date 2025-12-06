package com.pranay.journalApplication.Controller;

import com.pranay.journalApplication.Entity.User;
import com.pranay.journalApplication.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PutMapping("/update-user")
    public ResponseEntity<?> updateUser(@RequestBody User newEntry){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User old = userService.findByUserName(username);
        old.setUserName(newEntry.getUserName());
        old.setPassword(newEntry.getPassword());
        userService.saveNewUser(old);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/delete-user")
    public ResponseEntity<?> deleteUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        userService.deleteUserById(username);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
