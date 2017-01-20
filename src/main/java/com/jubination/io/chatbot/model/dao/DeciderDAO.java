/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jubination.io.chatbot.model.dao;

import com.jubination.io.chatbot.model.pojo.Decider;
import com.mongodb.WriteResult;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

/**
 *
 * @author MumbaiZone
 */
@Repository
public class DeciderDAO  implements GenericDAO<Decider>{
    
    @Autowired
    MongoTemplate mongoTemplate;
    
    public void setMongoTemplate(MongoTemplate mongoTemplate) {
		this.mongoTemplate = mongoTemplate;
	}
    
    @Override
    public List<Decider> getAllObjects() {
        return mongoTemplate.findAll(Decider.class);
    }

    @Override
    public void saveObject(Decider object) {
        mongoTemplate.insert(object);
    }

    @Override
    public Decider getObject(String id) {
        return mongoTemplate.findOne(new Query(Criteria.where("id").is(id)),Decider.class);
    }

    @Override
    public WriteResult updateObject(String id, String name) {
        return mongoTemplate.updateFirst(new Query(Criteria.where("id").is(id)),
				Update.update("name", name), Decider.class);
    }

    @Override
    public void deleteObject(String id) {
        mongoTemplate.remove(new Query(Criteria.where("id").is(id)), Decider.class);
    }

    @Override
    public void createCollection() {
       if (!mongoTemplate.collectionExists(Decider.class)) {
			mongoTemplate.createCollection(Decider.class);
		}
    }

    @Override
    public void dropCollection() {
       if (mongoTemplate.collectionExists(Decider.class)) {
			mongoTemplate.dropCollection(Decider.class);
		}
    }
}
