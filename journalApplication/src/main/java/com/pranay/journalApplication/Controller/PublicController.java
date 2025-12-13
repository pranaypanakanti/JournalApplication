package com.pranay.journalApplication.Controller;

import com.pranay.journalApplication.Entity.User;
import com.pranay.journalApplication.Service.EmailService;
import com.pranay.journalApplication.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/public")
public class PublicController {

    @Autowired
    private UserService userService;

    @Autowired
    private EmailService emailService;

    @GetMapping("/send-mail")
    public void sendMail(){
        emailService.sendEmail("garagadharma24@gmail.com","Gentle Remainder from Pranay","Late ayindhi padukora pu..");
    }

    @PostMapping("/new-user")
    public ResponseEntity<User> createNewUser(@RequestBody User myEntry){
        try{
            userService.saveNewUser(myEntry);
            return new ResponseEntity<>(myEntry, HttpStatus.CREATED);
        }catch (Exception e){
            return new ResponseEntity<>(myEntry,HttpStatus.BAD_REQUEST);
        }
    }
}
