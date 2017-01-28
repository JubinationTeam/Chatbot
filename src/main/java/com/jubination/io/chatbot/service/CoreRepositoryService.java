/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jubination.io.chatbot.service;


import com.jubination.io.chatbot.backend.pojo.core.ChatletUnit;
import com.jubination.io.chatbot.backend.pojo.core.Flow;
import com.jubination.io.chatbot.model.dao.ChatletDAO;
import com.jubination.io.chatbot.model.dao.ChatletTagDAO;
import com.jubination.io.chatbot.model.dao.DashBotDAO;
import com.jubination.io.chatbot.model.dao.DeciderDAO;
import com.jubination.io.chatbot.model.dao.MessageDAO;
import com.jubination.io.chatbot.model.dao.MessageSetDAO;
import com.jubination.io.chatbot.model.dao.UserDAO;
import com.jubination.io.chatbot.model.pojo.Chatlet;
import com.jubination.io.chatbot.model.pojo.ChatletTag;
import com.jubination.io.chatbot.model.pojo.DashBot;
import com.jubination.io.chatbot.model.pojo.Decider;
import com.jubination.io.chatbot.model.pojo.Message;
import com.jubination.io.chatbot.model.pojo.MessageSet;
import com.jubination.io.chatbot.model.pojo.User;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author MumbaiZone
 */
@Service
@Transactional
public class CoreRepositoryService {
    
    
    @Autowired
    PostProcessingService postProcessor;
    
    @Autowired
    ChatletDAO chatletRepository;
      @Autowired
    UserDAO userRepository;
        @Autowired
    ChatletTagDAO chatletTagRepository;
          @Autowired
    DashBotDAO dashBotRepository;
          @Autowired
    MessageDAO messageRepository;
      @Autowired
    MessageSetDAO messageSetRepository;
        @Autowired
    DeciderDAO deciderRepository;
       
        
        //Chatlet creation
        public Chatlet createChatlet(Chatlet chatlet){
       
                    int outerIndex=0;
                    for(MessageSet messageSet:chatlet.getBotMessages()){
                                int innerIndex=0;
                                for(Message message:messageSet.getMessages()){
                                    // System.out.println(message.getValue()+"::::::::::::::::::::::::::::::::::::::");
                                            message.setId(chatlet.getId()+outerIndex+innerIndex);
                                            messageRepository.saveObject(message);
                                            innerIndex++;
                                }
                                messageSet.setId(chatlet.getId()+outerIndex);
                                messageSetRepository.saveObject(messageSet);
                                outerIndex++;
                    }
                    outerIndex=0;
                    for(Decider decider:chatlet.getDeciders()){
                                decider.setId(chatlet.getId()+outerIndex);
                                deciderRepository.saveObject(decider);
                                outerIndex++;
                    }
                    chatletRepository.saveObject(chatlet);
                    return chatletRepository.getObject(chatlet.getId());
        }
        
        //Chatlet Deletion
        public void dropAllChatlets(){
            try{
            List<Chatlet> chatlets=chatletRepository.getAllObjects();
            for(Chatlet chatlet:chatlets){
                    for(MessageSet messageSet:chatlet.getBotMessages()){
                                for(Message message:messageSet.getMessages()){
                                            messageRepository.deleteObject(message.getId());
                                }
                                messageSetRepository.deleteObject(messageSet.getId());
                    }
                    for(Decider decider:chatlet.getDeciders()){
                                deciderRepository.deleteObject(decider.getId());
                    }
                    chatletRepository.deleteObject(chatlet.getId());
            }
            }
            catch(Exception e){
                e.printStackTrace();
            }
        }
        
        //ChatletTag Creation
        public ChatletTag createChatletTag(ChatletTag chatletTag){
       chatletTagRepository.saveObject(chatletTag);
       return chatletTagRepository.getObject(chatletTag.getId());
   }
        
        //User Creation
        public User createUser(User user){
       userRepository.saveObject(user);
       return userRepository.getObject(user.getSesId());
   }
        
        //DashBot Creation 
        public DashBot createDashBot(DashBot dashBot){
       dashBotRepository.saveObject(dashBot);
       return dashBotRepository.getObject(dashBot.getId());
   }
        
   
        

   
    
}
