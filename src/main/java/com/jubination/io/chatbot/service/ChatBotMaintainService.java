/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jubination.io.chatbot.service;


import com.jubination.io.chatbot.backend.service.core.DashBotUpdater;
import com.jubination.io.chatbot.backend.service.core.DietChartUpdater;
import com.jubination.io.chatbot.model.dao.ChatletDAO;
import com.jubination.io.chatbot.model.dao.ChatletTagDAO;
import com.jubination.io.chatbot.model.dao.DashBotDAO;
import com.jubination.io.chatbot.model.dao.UserDAO;
import com.jubination.io.chatbot.model.pojo.Chatlet;
import com.jubination.io.chatbot.model.pojo.ChatletTag;
import com.jubination.io.chatbot.model.pojo.DashBot;
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
public class ChatBotMaintainService {
    
    
    @Autowired
    DietChartUpdater updater;
        
    @Autowired
    DashBotUpdater analyzeUpdater;
    
    @Autowired
    ChatletDAO chatletRepository;
      @Autowired
    UserDAO userRepository;
        @Autowired
    ChatletTagDAO chatletTagRepository;
          @Autowired
    DashBotDAO dashBotRepository;
       
   public Chatlet createChatlet(Chatlet chatlet){
       chatletRepository.saveObject(chatlet);
       return chatletRepository.getObject(chatlet.getId());
   }
   
    public DashBot createDashBot(DashBot dashBot){
       dashBotRepository.saveObject(dashBot);
       return dashBotRepository.getObject(dashBot.getId());
   }
     public ChatletTag createChatletTag(ChatletTag chatletTag){
       chatletTagRepository.saveObject(chatletTag);
       return chatletTagRepository.getObject(chatletTag.getId());
   }
      public User createUser(User user){
       userRepository.saveObject(user);
       return userRepository.getObject(user.getUserId());
   }
    
}
