/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jubination.io.chatbot.backend.pojo.chatfuel;

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
@Scope("prototype")
@JsonIgnoreProperties(ignoreUnknown = true)
public class QuickReplies {
    String title;
    String block_name;
    List<String> block_names = new ArrayList<>();

    public QuickReplies() {
    }

    public QuickReplies(String title) {
        this.title = title;
    }

    public QuickReplies(String title, String block_name) {
        this.title = title;
        this.block_name = block_name;
    }
    

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<String> getBlock_names() {
        return block_names;
    }

    public void setBlock_names(List<String> block_names) {
        this.block_names = block_names;
    }

    public String getBlock_name() {
        return block_name;
    }

    public void setBlock_name(String block_name) {
        this.block_name = block_name;
    }
    
}
