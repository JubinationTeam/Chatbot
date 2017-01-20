/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jubination.io.chatbot.model.pojo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 *
 * @author MumbaiZone
 */
@Document(collection = "decider")
@JsonIgnoreProperties(ignoreUnknown = true)
public class Decider {
     @Id
    String id;
     HashMap<String,String> possibilities = new HashMap<>();
    String link;

    public HashMap<String, String> getPossibilities() {
        return possibilities;
    }

    public void setPossibilities(HashMap<String, String> possibilities) {
        this.possibilities = possibilities;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

 

 

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    
    
}
