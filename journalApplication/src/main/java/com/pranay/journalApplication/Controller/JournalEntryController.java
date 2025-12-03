package com.pranay.journalApplication.Controller;

import com.pranay.journalApplication.Entity.JournalEntry;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class JournalEntryController {


    @GetMapping("/get-all")
    public List<JournalEntry> getAll(){
        return null;
    }

    @PostMapping("/new-entry")
    public boolean createEntry(@RequestBody JournalEntry myEntry){
        return true;
    }

    @GetMapping("/get-by-id/{myId}")
    public JournalEntry getJournalEntryById(@PathVariable Long myId){
        return null;
    }

    @DeleteMapping("/delete-by-id/{myId}")
    public JournalEntry deleteJournalEntryById(@PathVariable Long myId){
        return null;
    }

    @PutMapping("/update-journal/{myId}")
    public boolean updateJournalEntryById(@PathVariable long myId, @RequestBody JournalEntry myEntry){
        return true;
    }

}
