package com.pranay.journalApplication.Controller;

import com.pranay.journalApplication.Entity.User;
import com.pranay.journalApplication.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/get-all")
    public ResponseEntity<?> getAll(){
        List<User> entries = userService.getAll();
        if(entries != null && !entries.isEmpty()){
            return new ResponseEntity<>(entries,HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/new-user")
    public ResponseEntity<User> createEntry(@RequestBody User myEntry){
        try{
            userService.saveEntry(myEntry);
            return new ResponseEntity<>(myEntry,HttpStatus.CREATED);
        }catch (Exception e){
            return new ResponseEntity<>(myEntry,HttpStatus.BAD_REQUEST);
        }
    }

//    @GetMapping("/get-by-id/{myId}")
//    public ResponseEntity<User> getUserById(@PathVariable ObjectId myId){
//        Optional<User> entry = userService.getUserById(myId);
//        if(entry.isPresent()){
//            return new ResponseEntity<>(entry.get(), HttpStatus.OK);
//        }
//        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//    }
//
//    @DeleteMapping("/delete-by-id/{myId}")
//    public ResponseEntity<?> deleteUserById(@PathVariable ObjectId myId){
//        userService.deleteUserById(myId);
//        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
//    }

    @PutMapping("/update-user/{username}")
    public ResponseEntity<?> updateUserById(@RequestBody User newEntry, @PathVariable String username){
        User old = userService.findByUserName(username);
        if(old != null){
            old.setUserName(newEntry.getUserName());
            old.setPassword(newEntry.getPassword());
            userService.saveEntry(old);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

}
