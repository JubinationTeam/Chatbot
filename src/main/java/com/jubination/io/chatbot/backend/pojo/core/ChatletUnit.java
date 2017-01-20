/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jubination.io.chatbot.backend.pojo.core;

import com.jubination.io.chatbot.model.pojo.Chatlet;
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
public class ChatletUnit {
    
    
    String type;
    Chatlet chatlet;

    public ChatletUnit() {
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Chatlet getChatlet() {
        return chatlet;
    }

    public void setChatlet(Chatlet chatlet) {
        this.chatlet = chatlet;
    }
    
    
}
