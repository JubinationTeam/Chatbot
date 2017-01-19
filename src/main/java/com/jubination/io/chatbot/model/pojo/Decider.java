/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jubination.io.chatbot.model.pojo;

import java.util.ArrayList;
import java.util.List;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;

/**
 *
 * @author MumbaiZone
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Decider {
    List<String> possibilities =new ArrayList<>();
    Chatlet link;

    public List<String> getPossibilities() {
        return possibilities;
    }

    public void setPossibilities(List<String> possibilities) {
        this.possibilities = possibilities;
    }

    public Chatlet getLink() {
        return link;
    }

    public void setLink(Chatlet link) {
        this.link = link;
    }
    
    
}
