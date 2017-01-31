/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jubination.io.chatbot.service;

import com.jubination.io.chatbot.backend.pojo.web.ChatBotRequest;
import com.jubination.io.chatbot.backend.pojo.web.UserResponse;
import com.jubination.io.chatbot.model.dao.ChatletDAO;
import com.jubination.io.chatbot.model.dao.UserDAO;
import com.jubination.io.chatbot.model.pojo.Chatlet;
import com.jubination.io.chatbot.model.pojo.ChatletMessage;
import com.jubination.io.chatbot.model.pojo.User;
import java.util.regex.Pattern;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author MumbaiZone
 */
@Service
@Transactional
public class ContextAwareMessageOperationService {
    
    @Autowired
    ChatletDAO chatletRepository;
    @Autowired
    UserDAO userRepository;
    
     public ChatBotRequest getContextAwareResponse(UserResponse res){
         if(Pattern.compile(Pattern.quote("fuck "), Pattern.CASE_INSENSITIVE).matcher(res.getLastAnswer()).find()||Pattern.compile(Pattern.quote(" fuck"), Pattern.CASE_INSENSITIVE).matcher(res.getLastAnswer()).find()){
             ChatBotRequest request = new ChatBotRequest();
             Chatlet chatlet=chatletRepository.getObject(res.getLastId());
             if(chatlet!=null){
                    request.setAnswerType(chatlet.getAnswerType());
                    request.setId(res.getLastId());
                    request.setSessionId(res.getSessionId());
                    for(String options:chatlet.getOptions().keySet()){
                        request.getOptions().add(options);
                    }
                    for(String options:chatlet.getOptions().keySet()){
                        request.getOptions().add(options);
                    }
                    request.getBotMessage().add(new ChatletMessage("text","I look like a nice person. But, very averse to abuses. Plase enter details correctly."));
                    User user=userRepository.getObject(res.getSessionId());
                    if(user!=null){
                        request.setGender(user.getGender());
                    }
             }
         }
         return null;
     }
     
      
    
}
