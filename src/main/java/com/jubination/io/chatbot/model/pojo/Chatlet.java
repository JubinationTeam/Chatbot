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
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.stereotype.Component;

/**
 *
 * @author MumbaiZone
 */

@Document(collection = "chatlet")
@JsonIgnoreProperties(ignoreUnknown = true)
public class Chatlet {
    @Id
    String id;
    Chatlet next;
    Chatlet prev;
    HashMap<String,Chatlet> validationChatlets = new HashMap<>();
    List<Message> botMessages = new ArrayList<>();
    HashMap<String,Chatlet> options = new HashMap<>();
      List<Decider> deciders= new  ArrayList<>();
    String answerType;
    Boolean validationBlock;
    Boolean conditionBlock;
      Boolean decisionPossible;
      String tagType;
      Boolean fbVisible;
      Boolean webVisible;
      

    public Chatlet() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Chatlet getNext() {
        return next;
    }

    public void setNext(Chatlet next) {
        this.next = next;
    }

    public Chatlet getPrev() {
        return prev;
    }

    public void setPrev(Chatlet prev) {
        this.prev = prev;
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

    public HashMap<String, Chatlet> getValidationChatlets() {
        return validationChatlets;
    }

    public void setValidationChatlets(HashMap<String, Chatlet> validationChatlets) {
        this.validationChatlets = validationChatlets;
    }

    public HashMap<String, Chatlet> getOptions() {
        return options;
    }

    public void setOptions(HashMap<String, Chatlet> options) {
        this.options = options;
    }

  

    
    
}
