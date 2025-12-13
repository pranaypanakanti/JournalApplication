package com.pranay.journalApplication.Service;

import com.pranay.journalApplication.Entity.JournalEntry;
import com.pranay.journalApplication.Entity.User;
import com.pranay.journalApplication.Repository.JournalEntryRepo;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class JournalEntryService {

    @Autowired
    private JournalEntryRepo journalEntryRepo;

    @Autowired
    private UserService userService;

    public void saveEntry(JournalEntry journalEntry, String username){
        User user = userService.findByUserName(username);
        journalEntry.setDate(LocalDateTime.now());
        JournalEntry save = journalEntryRepo.save(journalEntry);
        user.getJournalList().add(save);
        userService.saveUser(user);
    }

    public void saveEntry(JournalEntry journalEntry){
        journalEntryRepo.save(journalEntry);
    }

    public List<JournalEntry> getAll(){
        return journalEntryRepo.findAll();
    }

    public Optional<JournalEntry> getJournalEntryById(ObjectId myId){
        return journalEntryRepo.findById(myId);
    }

    public void deleteJournalEntryById(ObjectId myId, String username){
        User user = userService.findByUserName(username);
        boolean removed = false;
        user.getJournalList().removeIf(x -> x.getId().equals(myId));
        userService.saveUser(user);
        journalEntryRepo.deleteById(myId);
    }

}
