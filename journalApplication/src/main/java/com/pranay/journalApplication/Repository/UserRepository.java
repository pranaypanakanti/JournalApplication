package com.pranay.journalApplication.Repository;

import com.pranay.journalApplication.Entity.User;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.util.List;

public class UserRepository {

    private MongoTemplate mongoTemplate;

    public List<User> GetUsers(){
        Query query = new Query();
        query.addCriteria(Criteria.where("email").regex("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$"));
        query.addCriteria(Criteria.where("summery").is(true));
        List<User> users = mongoTemplate.find(query,User.class);
        return users;
    }
}
