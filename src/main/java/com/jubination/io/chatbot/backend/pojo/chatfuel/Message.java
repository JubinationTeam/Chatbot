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
public class Message {
    String text;
    Attachment attachment;
    List<QuickReplies> quick_replies = new ArrayList<>();

    public Message() {
    }

    public Message(String text) {
        this.text = text;
    }

    public Message(Attachment attachment) {
        this.attachment = attachment;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Attachment getAttachment() {
        return attachment;
    }

    public void setAttachment(Attachment attachment) {
        this.attachment = attachment;
    }

    public List<QuickReplies> getQuick_replies() {
        return quick_replies;
    }

    public void setQuick_replies(List<QuickReplies> quick_replies) {
        this.quick_replies = quick_replies;
    }

    
            
}
