package com.pranay.journalApplication.Controller;

import com.pranay.journalApplication.Entity.JournalEntry;
import com.pranay.journalApplication.Entity.User;
import com.pranay.journalApplication.Service.JournalEntryService;
import com.pranay.journalApplication.Service.UserService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController
@RequestMapping("/api/journal")
public class JournalEntryController {

    @Autowired
    private JournalEntryService journalEntryService;

    @Autowired
    private UserService userService;

    @GetMapping("/get-all")
    public ResponseEntity<?> getAllJournals(){
        List<JournalEntry> entries = journalEntryService.getAll();
        if(entries != null && !entries.isEmpty()){
            return new ResponseEntity<>(entries,HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/get-all/{username}")
    public ResponseEntity<?> getAllJournalsOfUser(@PathVariable String username){
        User user = userService.findByUserName(username);
        List<JournalEntry> entries = user.getJournalList();
        if(entries != null && !entries.isEmpty()){
            return new ResponseEntity<>(entries,HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/new-entry/{username}")
    public ResponseEntity<JournalEntry> createEntry(@RequestBody JournalEntry myEntry, @PathVariable String username){
        try{
            journalEntryService.saveEntry(myEntry,username);
            return new ResponseEntity<>(myEntry,HttpStatus.CREATED);
        }catch (Exception e){
            return new ResponseEntity<>(myEntry,HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/get-by-id/{myId}")
    public ResponseEntity<JournalEntry> getJournalEntryById(@PathVariable ObjectId myId){
        Optional<JournalEntry> entry = journalEntryService.getJournalEntryById(myId);
        if(entry.isPresent()){
            return new ResponseEntity<>(entry.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/delete-by-id/{myId}/{username}")
    public ResponseEntity<?> deleteJournalEntryById(@PathVariable ObjectId myId, @PathVariable String username){
        journalEntryService.deleteJournalEntryById(myId,username);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/update-journal/{myId}/{username}")
    public ResponseEntity<?> updateJournalEntryById(@PathVariable ObjectId myId,
                                                    @PathVariable String username,
                                                    @RequestBody JournalEntry newEntry){
        JournalEntry old = journalEntryService.getJournalEntryById(myId).orElse(null);
        if(old != null){
            old.setName(newEntry.getName() != null && !newEntry.getName().equals("") ? newEntry.getName() : old.getName());
            old.setContent(newEntry.getContent() != null && !newEntry.getContent().equals("") ? newEntry.getContent() : old.getContent());
            journalEntryService.saveEntry(old);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

}
