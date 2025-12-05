package com.pranay.journalApplication.Service;

import com.pranay.journalApplication.Entity.User;
import com.pranay.journalApplication.Repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.List;

@Component
public class UserService {

    @Autowired
    private UserRepo userRepo;

    public void saveEntry(User user){
        userRepo.save(user);
    }

    public List<User> getAll(){
        return userRepo.findAll();
    }

    public User findByUserName(String username){
        return userRepo.findByUserName(username);
    }

//    public void deleteUserById(ObjectId myId){
//        userRepo.deleteById(myId);
//    }

}
