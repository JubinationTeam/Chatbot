/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jubination.io.chatbot.model.dao;

import com.jubination.io.chatbot.model.pojo.Message;
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
public class MessageDAO implements GenericDAO<Message>{
    
    @Autowired
    MongoTemplate mongoTemplate;
    
    public void setMongoTemplate(MongoTemplate mongoTemplate) {
		this.mongoTemplate = mongoTemplate;
	}
    
    @Override
    public List<Message> getAllObjects() {
        return mongoTemplate.findAll(Message.class);
    }

    @Override
    public void saveObject(Message object) {
        mongoTemplate.insert(object);
    }

    @Override
    public Message getObject(String id) {
        return mongoTemplate.findOne(new Query(Criteria.where("id").is(id)),Message.class);
    }

    @Override
    public WriteResult updateObject(String id, String name) {
        return mongoTemplate.updateFirst(new Query(Criteria.where("id").is(id)),
				Update.update("name", name), Message.class);
    }

    @Override
    public void deleteObject(String id) {
        mongoTemplate.remove(new Query(Criteria.where("id").is(id)), Message.class);
    }

    @Override
    public void createCollection() {
       if (!mongoTemplate.collectionExists(Message.class)) {
			mongoTemplate.createCollection(Message.class);
		}
    }

    @Override
    public void dropCollection() {
       if (mongoTemplate.collectionExists(Message.class)) {
			mongoTemplate.dropCollection(Message.class);
		}
    }
    
}
