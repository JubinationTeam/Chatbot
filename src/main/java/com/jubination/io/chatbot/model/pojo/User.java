/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jubination.io.chatbot.model.pojo;

import java.util.HashMap;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 *
 * @author MumbaiZone
 */


@Document(collection = "user")
@JsonIgnoreProperties(ignoreUnknown = true)
public class User {
    @Id
     String sesId;
    String email;
    String name;
    String phone;
    String gender;
    String country;
    String fbId;
    HashMap<String,String> tags =new HashMap<>();
    HashMap<String,String> result =new HashMap<>();
  HashMap<String,Boolean> triggers =new HashMap<>();
    
    public User() {
    }

    public User(String sesId, String name, String gender,String fbId) {
        this.sesId = sesId;
        this.name = name;
        this.fbId = fbId;
        this.gender=gender;
    }

   

    
    public HashMap<String, String> getTags() {
        return tags;
    }

    public void setTags(HashMap<String, String> tags) {
        this.tags = tags;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

   


    public String getFbId() {
        return fbId;
    }

    public void setFbId(String fbId) {
        this.fbId = fbId;
    }

   
    public String getSesId() {
        return sesId;
    }

    public void setSesId(String sesId) {
        this.sesId = sesId;
    }

    public HashMap<String, String> getResult() {
        return result;
    }

    public void setResult(HashMap<String, String> result) {
        this.result = result;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public HashMap<String, Boolean> getTriggers() {
        return triggers;
    }

    public void setTriggers(HashMap<String, Boolean> triggers) {
        this.triggers = triggers;
    }

    
}
