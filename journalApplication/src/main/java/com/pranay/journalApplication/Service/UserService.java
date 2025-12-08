package com.pranay.journalApplication.Service;

import com.pranay.journalApplication.Entity.User;
import com.pranay.journalApplication.Repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
public class UserService {

    @Autowired
    private UserRepo userRepo;

    private static final PasswordEncoder encoder = new BCryptPasswordEncoder();


    public void saveNewUser(User user){
        user.setPassword(encoder.encode(user.getPassword()));
        user.setRoles(Arrays.asList("USER"));
        userRepo.save(user);
    }

    public void saveUser(User user){
        user.setRoles(Arrays.asList("USER"));
        userRepo.save(user);
    }

    public void saveAdmin(User user) {
        user.setPassword(encoder.encode(user.getPassword()));
        user.setRoles(Arrays.asList("USER","ADMIN"));
        userRepo.save(user);
    }

    public List<User> getAll(){
        return userRepo.findAll();
    }

    public User findByUserName(String username){
        return userRepo.findByUserName(username);
    }

    public void deleteUserById(String username){
        userRepo.deleteByUserName(username);
    }

}
