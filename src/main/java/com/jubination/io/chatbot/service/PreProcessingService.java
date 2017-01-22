/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jubination.io.chatbot.service;

import com.jubination.io.chatbot.backend.pojo.core.UserResponse;
import com.jubination.io.chatbot.backend.service.core.UniqueIdHelper;
import com.jubination.io.chatbot.model.pojo.ChatletTag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author MumbaiZone
 */

@Service
@Transactional
public class PreProcessingService {
    @Autowired
    UniqueIdHelper idHelper;
    
    public ChatletTag convertWebUserResponseIntoChatletTag(UserResponse response){
                ChatletTag chatletTag = new ChatletTag();
                chatletTag.setAnswer(response.getLastAnswer());
                chatletTag.setChatletId(response.getLastId());
                chatletTag.setSessionId(response.getSessionId());
                return chatletTag;

        
    }
    
     public String getRecentSessionId(UserResponse response) {
         if(response.getLastId()==null){
              return idHelper.getId();
         }
         return response.getSessionId();
    }
}
