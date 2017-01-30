/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jubination.io.chatbot.model.dao;

import com.jubination.io.chatbot.model.pojo.FbSessionIndices;
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
public class FbSessionDAO  implements GenericDAO<FbSessionIndices>{
    
    @Autowired
    MongoTemplate mongoTemplate;
    
    public void setMongoTemplate(MongoTemplate mongoTemplate) {
		this.mongoTemplate = mongoTemplate;
	}
    
    @Override
    public List<FbSessionIndices> getAllObjects() {
        return mongoTemplate.findAll(FbSessionIndices.class);
    }

    @Override
    public void saveObject(FbSessionIndices object) {
        mongoTemplate.insert(object);
    }

    @Override
    public FbSessionIndices getObject(String id) {
        return mongoTemplate.findOne(new Query(Criteria.where("fbId").is(id)),FbSessionIndices.class);
    }
    
   

    @Override
    public WriteResult updateObject(String id, String name) {
        return mongoTemplate.updateFirst(new Query(Criteria.where("fbId").is(id)),
				Update.update("name", name), FbSessionIndices.class);
    }
    
   public WriteResult updateObject(String id, Object value, String type) {
        return mongoTemplate.updateFirst(new Query(Criteria.where("fbId").is(id)),
				Update.update(type, value), FbSessionIndices.class);
    }
   

    @Override
    public void deleteObject(String id) {
        mongoTemplate.remove(new Query(Criteria.where("fbId").is(id)), FbSessionIndices.class);
    }

    @Override
    public void createCollection() {
       if (!mongoTemplate.collectionExists(FbSessionIndices.class)) {
			mongoTemplate.createCollection(FbSessionIndices.class);
		}
    }

    @Override
    public void dropCollection() {
       if (mongoTemplate.collectionExists(FbSessionIndices.class)) {
			mongoTemplate.dropCollection(FbSessionIndices.class);
		}
    }

    

}
