/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jubination.io.chatbot.backend.pojo.chatfuel;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 *
 * @author MumbaiZone
 */
@Component
@Scope("prototype")
@JsonIgnoreProperties(ignoreUnknown = true)
public class ChatFuelet {
    
    
  
    List<Message> messages = new ArrayList<>();
    LinkedHashMap<String,String> set_attributes = new LinkedHashMap<>();
    

    

    public List<Message> getMessages() {
        return messages;
    }

    public void setMessages(List<Message> messages) {
        this.messages = messages;
    }

    public LinkedHashMap<String, String> getSet_attributes() {
        return set_attributes;
    }

    public void setSet_attributes(LinkedHashMap<String, String> set_attributes) {
        this.set_attributes = set_attributes;
    }
    
    
    
}
