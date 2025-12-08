package com.pranay.journalApplication.Controller;

import com.pranay.journalApplication.Entity.User;
import com.pranay.journalApplication.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    @Autowired
    private UserService userService;

    @GetMapping("/get-all-users")
    public ResponseEntity<?> getAllUsers(){
        List<User> users = userService.getAll();
        if(users.isEmpty()) return new ResponseEntity<>(null,HttpStatus.NO_CONTENT);
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @PostMapping("/save-admin")
    public ResponseEntity<?> saveAdmin(@RequestBody User user){
        userService.saveAdmin(user);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
