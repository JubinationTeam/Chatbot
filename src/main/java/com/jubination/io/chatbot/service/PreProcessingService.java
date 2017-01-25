/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jubination.io.chatbot.service;

import com.jubination.io.chatbot.backend.pojo.core.UserResponse;
import com.jubination.io.chatbot.backend.service.core.UniqueIdHelper;
import com.jubination.io.chatbot.model.dao.ChatletDAO;
import com.jubination.io.chatbot.model.dao.UserDAO;
import com.jubination.io.chatbot.model.pojo.Chatlet;
import com.jubination.io.chatbot.model.pojo.ChatletTag;
import com.jubination.io.chatbot.model.pojo.User;
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
    @Autowired
    ChatletDAO chatletRepository;
         @Autowired
    UserDAO userRepository;
    
    public ChatletTag convertWebUserResponseIntoChatletTag(UserResponse response,String webSessionId){
                ChatletTag chatletTag = new ChatletTag();
                chatletTag.setAnswer(response.getLastAnswer());
                chatletTag.setChatletId(response.getLastId());
                chatletTag.setSessionId(getRecentSessionId(response,webSessionId));
                return chatletTag;

        
    }
    
     public String getRecentSessionId(UserResponse response,String webSessionId) {
         Chatlet chatlet = chatletRepository.getObject(response.getLastId());
         if(response.getLastId()==null){
             String val=webSessionId+idHelper.getId()+response.getWebId();
              userRepository.saveObject(new User(val,null,null));
              System.out.println(val+"init");
              return val;
         }
         if(chatlet!=null&&chatlet.getRefresh()){
             String val=webSessionId+idHelper.getId()+response.getWebId();
              userRepository.saveObject(new User(val,null,null));
              System.out.println(val+"refresh");
              return val;
         }
         
         return response.getSessionId();
    }
}
