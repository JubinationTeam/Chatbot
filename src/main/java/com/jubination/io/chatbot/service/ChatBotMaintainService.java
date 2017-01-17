/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jubination.io.chatbot.service;


import com.jubination.io.chatbot.backend.service.core.DashBotUpdater;
import com.jubination.io.chatbot.backend.service.core.DietChartUpdater;
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
    private  DietChartUpdater updater;
        
    @Autowired
    private  DashBotUpdater analyzeUpdater;
       
   public Chatlet createChatlet(Chatlet chatlet){
       
       return chatlet;
   }
    
}
