/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jubination.io.chatbot.service;


import com.jubination.io.chatbot.backend.service.core.DashBotUpdater;
import com.jubination.io.chatbot.backend.service.core.DietChartUpdater;
import com.jubination.io.chatbot.model.dao.ChatletDAO;
import com.jubination.io.chatbot.model.pojo.Chatlet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author MumbaiZone
 */
@Component
public class ChatBotMaintainService {
    
    
    @Autowired
    DietChartUpdater updater;
        
    @Autowired
    DashBotUpdater analyzeUpdater;
    
    @Autowired
    ChatletDAO chatletRepository;
       
   public Chatlet createChatlet(Chatlet chatlet){
       chatletRepository.saveObject(chatlet);
       return chatletRepository.getObject(chatlet.getId());
   }
    
}
