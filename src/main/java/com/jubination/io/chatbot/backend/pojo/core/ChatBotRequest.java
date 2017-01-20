/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jubination.io.chatbot.backend.pojo.core;

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
    
    
    private int serialNumber;
    private int id;
    private List<String> botMessage = new ArrayList<>();
    private String answerType;
    private String answer;
    private String sessionId;
    private List<String> options = new ArrayList<>();

    public ChatBotRequest(){
        
    }
    
    public ChatBotRequest(int id, List<String> botMessage, String answerType, List<String> options) {
        this.id = id;
        this.botMessage = botMessage;
        this.answerType = answerType;
        if(options!=null){
            this.options=options;
        }
    }
    
    
    

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public List<String> getOptions() {
        return options;
    }

    public void setOptions(List<String> options) {
        this.options = options;
    }

   
    public int getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(int serialNumber) {
        this.serialNumber = serialNumber;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<String> getBotMessage() {
        return botMessage;
    }

    public void setBotMessage(List<String> botMessage) {
        this.botMessage = botMessage;
    }

   
    public String getAnswerType() {
        return answerType;
    }

    public void setAnswerType(String answerType) {
        this.answerType = answerType;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    
    
    
}
