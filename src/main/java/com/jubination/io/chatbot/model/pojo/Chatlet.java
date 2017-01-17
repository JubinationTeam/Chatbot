/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jubination.io.chatbot.model.pojo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.stereotype.Component;

/**
 *
 * @author MumbaiZone
 */
@Component
@Document
public class Chatlet {
    @Id
    Long id;
    Chatlet next;
    Chatlet prev;
    HashMap<String,Chatlet> expectionChatlets = new HashMap<>();
    List<HashMap<String,String>> botMessages = new ArrayList<>();
    HashMap<String,Chatlet> options = new HashMap<>();
      HashMap<List<String>,Chatlet> deciders= new  HashMap<>();
    String answerType;
    Boolean validationBlock;
    Boolean conditionBlock;
      Boolean decisionPossible;
      String tagType;
      Boolean fbVisible;
      Boolean webVisible;
      

    public Chatlet() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Chatlet getNext() {
        return next;
    }

    public void setNext(Chatlet next) {
        this.next = next;
    }

    public HashMap<String, Chatlet> getExpectionChatlets() {
        return expectionChatlets;
    }

    public void setExpectionChatlets(HashMap<String, Chatlet> expectionChatlets) {
        this.expectionChatlets = expectionChatlets;
    }

    public List<HashMap<String, String>> getBotMessages() {
        return botMessages;
    }

    public void setBotMessages(List<HashMap<String, String>> botMessages) {
        this.botMessages = botMessages;
    }

    public HashMap<String, Chatlet> getOptions() {
        return options;
    }

    public void setOptions(HashMap<String, Chatlet> options) {
        this.options = options;
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

 

    public Boolean getDecisionPossible() {
        return decisionPossible;
    }

    public void setDecisionPossible(Boolean decisionPossible) {
        this.decisionPossible = decisionPossible;
    }

    public String getTagType() {
        return tagType;
    }

    public void setTagType(String tagType) {
        this.tagType = tagType;
    }

    public Chatlet getPrev() {
        return prev;
    }

    public void setPrev(Chatlet prev) {
        this.prev = prev;
    }

    public HashMap<List<String>, Chatlet> getDeciders() {
        return deciders;
    }

    public void setDeciders(HashMap<List<String>, Chatlet> deciders) {
        this.deciders = deciders;
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

    
    
}
