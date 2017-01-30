/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jubination.io.chatbot.backend.pojo.web;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 *
 * @author MumbaiZone
 */

@Component
@Scope
    @JsonIgnoreProperties(ignoreUnknown = true)
public class UserResponse {
    
    
    private String lastAnswer;
    private String lastId;
    private String sessionId;
    private String webId; 
    private String name; 
    private String gender;
    private String fbId;

   

    public String getLastAnswer() {
        return lastAnswer;
    }

    public void setLastAnswer(String lastAnswer) {
        this.lastAnswer = lastAnswer;
    }

    public String getLastId() {
        return lastId;
    }

    public void setLastId(String lastId) {
        this.lastId = lastId;
    }

   

    

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public String getWebId() {
        return webId;
    }

    public void setWebId(String webId) {
        this.webId = webId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getFbId() {
        return fbId;
    }

    public void setFbId(String fbId) {
        this.fbId = fbId;
    }

    
   

    
    
    
}
