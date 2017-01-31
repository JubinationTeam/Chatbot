/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jubination.io.chatbot.model.dao;

import com.jubination.io.chatbot.model.pojo.ChatletMessage;
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
public class MessageDAO implements GenericDAO<ChatletMessage>{
    
    @Autowired
    MongoTemplate mongoTemplate;
    
    public void setMongoTemplate(MongoTemplate mongoTemplate) {
		this.mongoTemplate = mongoTemplate;
	}
    
    @Override
    public List<ChatletMessage> getAllObjects() {
        return mongoTemplate.findAll(ChatletMessage.class);
    }

    @Override
    public void saveObject(ChatletMessage object) {
        mongoTemplate.insert(object);
    }

    @Override
    public ChatletMessage getObject(String id) {
        return mongoTemplate.findOne(new Query(Criteria.where("id").is(id)),ChatletMessage.class);
    }

    @Override
    public WriteResult updateObject(String id, String name) {
        return mongoTemplate.updateFirst(new Query(Criteria.where("id").is(id)),
				Update.update("name", name), ChatletMessage.class);
    }

    @Override
    public void deleteObject(String id) {
        mongoTemplate.remove(new Query(Criteria.where("id").is(id)), ChatletMessage.class);
    }

    @Override
    public void createCollection() {
       if (!mongoTemplate.collectionExists(ChatletMessage.class)) {
			mongoTemplate.createCollection(ChatletMessage.class);
		}
    }

    @Override
    public void dropCollection() {
       if (mongoTemplate.collectionExists(ChatletMessage.class)) {
			mongoTemplate.dropCollection(ChatletMessage.class);
		}
    }
    
}
