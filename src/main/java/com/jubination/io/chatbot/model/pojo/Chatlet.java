/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jubination.io.chatbot.model.pojo;

import com.jubination.io.chatbot.model.dao.CascadeSave;
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

@Document(collection = "chatlet")
@JsonIgnoreProperties(ignoreUnknown = true)
public class Chatlet {
    @Id
    String id;
    String next;
    String prev;
    HashMap<String,String> validationChatlets = new HashMap<>();
     @DBRef
//      @CascadeSave
    List<MessageSet> botMessages = new ArrayList<>();
    HashMap<String,String> options = new HashMap<>();
    
     @DBRef
//     @CascadeSave
      List<Decider> deciders= new  ArrayList<>();
    String answerType;
    Boolean validationBlock;
    Boolean conditionBlock;
      String tagType;
      Boolean fbVisible;
      Boolean webVisible;
      

    public Chatlet() {
        validationBlock=false;
        conditionBlock=false;
        fbVisible=false;
        webVisible=false;
        
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

  

    public String getAnswerType() {
        return answerType;
    }

    public void setAnswerType(String answerType) {
        this.answerType = answerType;
    }

    public Boolean getValidationBlock() {
        return validationBlock;
    }

    public void setValidationBlock(Boolean validationBlock) {
        this.validationBlock = validationBlock;
    }

    public Boolean getConditionBlock() {
        return conditionBlock;
    }

    public void setConditionBlock(Boolean conditionBlock) {
        this.conditionBlock = conditionBlock;
    }


    public String getTagType() {
        return tagType;
    }

    public void setTagType(String tagType) {
        this.tagType = tagType;
    }

    public Boolean getFbVisible() {
        return fbVisible;
    }

    public void setFbVisible(Boolean fbVisible) {
        this.fbVisible = fbVisible;
    }

    public Boolean getWebVisible() {
        return webVisible;
    }

    public void setWebVisible(Boolean webVisible) {
        this.webVisible = webVisible;
    }

   
    public List<MessageSet> getBotMessages() {
        return botMessages;
    }

    public void setBotMessages(List<MessageSet> botMessages) {
        this.botMessages = botMessages;
    }

    public List<Decider> getDeciders() {
        return deciders;
    }

    public void setDeciders(List<Decider> deciders) {
        this.deciders = deciders;
    }

    public String getNext() {
        return next;
    }

    public void setNext(String next) {
        this.next = next;
    }

    public String getPrev() {
        return prev;
    }

    public void setPrev(String prev) {
        this.prev = prev;
    }

    public HashMap<String, String> getValidationChatlets() {
        return validationChatlets;
    }

    public void setValidationChatlets(HashMap<String, String> validationChatlets) {
        this.validationChatlets = validationChatlets;
    }

    public HashMap<String, String> getOptions() {
        return options;
    }

    public void setOptions(HashMap<String, String> options) {
        this.options = options;
    }

  

    
    
}
