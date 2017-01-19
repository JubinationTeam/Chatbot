/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jubination.io.chatbot.model.dao;

import com.jubination.io.chatbot.model.pojo.ChatletTag;
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
public class ChatletTagDAO implements GenericDAO<ChatletTag>{
    
    @Autowired
    MongoTemplate mongoTemplate;
    
    public void setMongoTemplate(MongoTemplate mongoTemplate) {
		this.mongoTemplate = mongoTemplate;
	}
    
    @Override
    public List<ChatletTag> getAllObjects() {
        return mongoTemplate.findAll(ChatletTag.class);
    }

    @Override
    public void saveObject(ChatletTag object) {
        mongoTemplate.insert(object);
    }

    @Override
    public ChatletTag getObject(String id) {
        return mongoTemplate.findOne(new Query(Criteria.where("id").is(id)),ChatletTag.class);
    }

    @Override
    public WriteResult updateObject(String id, String name) {
        return mongoTemplate.updateFirst(new Query(Criteria.where("id").is(id)),
				Update.update("name", name), ChatletTag.class);
    }

    @Override
    public void deleteObject(String id) {
        mongoTemplate.remove(new Query(Criteria.where("id").is(id)), ChatletTag.class);
    }

    @Override
    public void createCollection() {
       if (!mongoTemplate.collectionExists(ChatletTag.class)) {
			mongoTemplate.createCollection(ChatletTag.class);
		}
    }

    @Override
    public void dropCollection() {
       if (mongoTemplate.collectionExists(ChatletTag.class)) {
			mongoTemplate.dropCollection(ChatletTag.class);
		}
    }
}
