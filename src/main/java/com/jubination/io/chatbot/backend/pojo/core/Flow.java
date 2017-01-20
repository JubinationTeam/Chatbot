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
@Component(value = "prototype")
@Scope("prototype")
@JsonIgnoreProperties(ignoreUnknown = true)
public class Flow {
    String prefix;
    List<ChatletUnit> chatlets = new ArrayList<>();

    public Flow() {
    }

    public List<ChatletUnit> getChatlets() {
        return chatlets;
    }

    public void setChatlets(List<ChatletUnit> chatlets) {
        this.chatlets = chatlets;
    }

    


    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }
    
    
}
