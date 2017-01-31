/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jubination.io.chatbot.backend.pojo.web;

import com.jubination.io.chatbot.model.pojo.ChatletMessage;
import java.util.ArrayList;
import java.util.List;
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
public class ChatBotRequest {
    
    
    private String id;
    private String sessionId;
    private String gender;
    private List<ChatletMessage> botMessage = new ArrayList<>();
    private String answerType;
    private List<String> options = new ArrayList<>();


    public ChatBotRequest(){
        
    }


    public List<String> getOptions() {
        return options;
    }

    public void setOptions(List<String> options) {
        this.options = options;
    }

   

   
    public String getAnswerType() {
        return answerType;
    }

    public void setAnswerType(String answerType) {
        this.answerType = answerType;
    }

    public List<ChatletMessage> getBotMessage() {
        return botMessage;
    }

    public void setBotMessage(List<ChatletMessage> botMessage) {
        this.botMessage = botMessage;
    }

    


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    
    
    
}
