/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jubination.io.chatbot.backend.pojo.facebook;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.springframework.stereotype.Component;

/**
 *
 * @author MumbaiZone
 */

    @JsonIgnoreProperties(ignoreUnknown = true)
public class FBSendChatBotRequest {
    
        
        public Recipient recipient;
        
        public Message message;
        public String sender_action;
        public String notification;
   

    
    
    
}
