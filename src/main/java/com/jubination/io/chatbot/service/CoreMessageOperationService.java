/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jubination.io.chatbot.service;

import com.jubination.io.chatbot.backend.service.core.DashBotUpdater;
import com.jubination.io.chatbot.model.dao.ChatletDAO;
import com.jubination.io.chatbot.model.dao.ChatletTagDAO;
import com.jubination.io.chatbot.model.dao.DashBotDAO;
import com.jubination.io.chatbot.model.dao.UserDAO;
import com.jubination.io.chatbot.model.pojo.Chatlet;
import com.jubination.io.chatbot.model.pojo.ChatletTag;
import com.jubination.io.chatbot.model.pojo.DashBot;
import com.jubination.io.chatbot.model.pojo.Decider;
import com.jubination.io.chatbot.model.pojo.User;
import java.util.Iterator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author MumbaiZone
 */
@Service
@Transactional
public class CoreMessageOperationService {
    
        @Autowired
    ChatletDAO chatletRepository;
          @Autowired
    UserDAO userRepository;
           @Autowired
    ChatletTagDAO chatletTagRepository;
          @Autowired
    DashBotDAO dashBotRepository;
          @Autowired
          DashBotUpdater dashBotUpdater;
        @Autowired
        PostProcessingService postProcessor;
    
         //Bussiness Logic
        public Chatlet getNextChatlet(ChatletTag chatletTag){
            Chatlet chatletResponse;
            if(chatletTag.getChatletId()==null||chatletTag.getChatletId().isEmpty()){
                System.out.println("FIRST HIT:");
                chatletResponse=getFirstChatlet();
            }
            else{
                
                //save and send incoming dashBot data
                DashBot incoming = new DashBot(chatletTag.getSessionId(),chatletTag.getAnswer());
                dashBotRepository.saveObject(incoming);
                dashBotUpdater.sendAutomatedUpdate(incoming, "incoming");
                
                
                Chatlet chatlet =  chatletRepository.getObject(chatletTag.getChatletId());
                
               
                //set chatletTag
                chatletTag.setAnswerType(chatlet.getAnswerType());
                chatletTag.setChatletId(chatlet.getId());
                chatletTag.setQuestion(chatlet.getBotMessages().toString());
                chatletTag.setTagType(chatlet.getTagType());
                
                
                switch(chatlet.getAnswerType()){
                    //STC,TVC,TDC
                    case "text":
                        
                                    //TDC and STC
                                    if(!chatlet.getValidationBlock()){
                                            chatletResponse=getSTCAndTDC(chatlet, chatletTag);
                                            
                                    }
                                    //TVC
                                    else{
                                            chatletResponse=getTVC(chatlet,chatletTag);
                                            System.out.println("TVC:");
                                    }
                        break;
                    //COC,SOC,ODC
                    case "option":
                                
                                
                                //SOC,ODC-Non Conditional
                                    if(!chatlet.getConditionBlock()){
                                        chatletResponse=getSOCAndODC(chatlet, chatletTag);
                                    }
                                    //COC-Conditonal
                                    else{
                                        chatletResponse=getCOC(chatlet, chatletTag);
                                        System.out.println("COC:");
                                    }
                        break;
                    //SHC
                    case "text-option":
                                        chatletResponse=getSHC(chatlet,chatletTag);
                        break;
                    default:
                                        chatletResponse=null;
                        break; 
                }            
            }
            //save chatletTag data
            chatletTagRepository.saveObject(chatletTag);
            
            //save and send outgoing dashBot data
            if(chatletResponse!=null&&chatletResponse.getBotMessages()!=null){
                DashBot outgoing = new DashBot(chatletTag.getSessionId(),chatletResponse.getBotMessages().toString());
                dashBotRepository.saveObject(outgoing);
                dashBotUpdater.sendAutomatedUpdate(outgoing, "outgoing");
            System.out.println(chatletResponse.getBotMessages());
            }
            return chatletResponse;
        }
        
        
        //Fetch Chatlets
        private Chatlet getFirstChatlet() {
        return chatletRepository.getObject("0");
    }
        private Chatlet getSOCAndODC(Chatlet chatlet,ChatletTag chatletTag){
                                                //ODC
                                                    if(chatlet.getDeciders()!=null&&!chatlet.getDeciders().isEmpty()){
                                                        String deciderId=getDeciderId(chatlet, chatletTag);
                                                        if(deciderId!=null){
                                                            System.out.println("ODC:");
                                                                return chatletRepository.getObject(deciderId);
                                                        }
                                                        
                                                    }
                                                    //SOC
                                                    System.out.println("SOC:");
            return chatletRepository.getObject(chatlet.getNext());
        }
        private Chatlet getCOC(Chatlet chatlet,ChatletTag chatletTag){
            return chatletRepository.getObject(chatlet.getOptions().get(chatletTag.getAnswer()));
        }
        private Chatlet getSTCAndTDC(Chatlet chatlet, ChatletTag chatletTag){
                                        
                            Iterator<String> iterator=chatlet.getValidationChatlets().keySet().iterator();
                                            while(iterator.hasNext()){
                                                String nextKey=iterator.next();
                                                String tag=postProcessor.validatedText(chatlet.getTagType()+"-"+nextKey,chatletTag.getAnswer());
                                                if(tag==null){
                                                    
                                                    Chatlet validationChatlet=chatletRepository.getObject(chatlet.getValidationChatlets().get(nextKey));
                                                    if(validationChatlet.getNext()==null){
                                                        chatlet.setBotMessages(validationChatlet.getBotMessages());
                                                        return chatlet;
                                                    }
                                                    else{
                                                        return validationChatlet;
                                                    }
                                                }
                                                else{
                                                    //replace chatletTag tag with validated answer 
                                                    chatletTag.setTag(tag);
                                                    //update user details
                                                    if(chatletTag.getTagType().equals("name")||chatletTag.getTagType().equals("email")||chatletTag.getTagType().equals("phone")||chatletTag.getTagType().equals("city")){
                                                       
                                                                userRepository.updateObject(chatletTag.getSessionId(),chatletTag.getTag(),chatletTag.getTagType());
                                                           
                                                    }
                                                }
                                            }
                                                //TDC
                                                    if(chatlet.getDeciders()!=null&&!chatlet.getDeciders().isEmpty()){
                                                        String deciderId=getDeciderId(chatlet, chatletTag);
                                                        if(deciderId!=null){
                                                            System.out.println("TDC:");
                                                                return chatletRepository.getObject(deciderId);
                                                        }
                                                        
                                                    }
                                            //STC
                                            System.out.println("STC:");
                                             return chatletRepository.getObject(chatlet.getNext());
        }   
        private Chatlet getTVC(Chatlet chatlet, ChatletTag chatletTag) {
                                            //Parent
                                            if(chatlet.getNext()==null){
                                                    Chatlet workChatlet =  chatletRepository.getObject(chatlet.getPrev());
                                                    return getSTCAndTDC(workChatlet, chatletTag);
                                            }
                                            //Self
                                                    return getSTCAndTDC(chatlet, chatletTag);
    } 
        private Chatlet getSHC(Chatlet chatlet, ChatletTag chatletTag) {
         Iterator<String> iterator=chatlet.getOptions().keySet().iterator();
                            while(iterator.hasNext()){
                               String nextKey= iterator.next();
                               if(nextKey.equals(chatletTag.getAnswer())){
                                   return getSOCAndODC(chatlet, chatletTag);
                               }
                            }
                            
                            return getSTCAndTDC(chatlet, chatletTag);
    }
        
        
        //Deciding next link id
         private String getDeciderId(Chatlet chatlet, ChatletTag chatletTag) {
             User user=userRepository.getObject(chatletTag.getSessionId());
             String id=null;
             int max=0;
              if(user!=null&&user.getTags()!=null&&!user.getTags().isEmpty()){
                        for(Decider decider:chatlet.getDeciders()){
                                Iterator<String> iterator=decider.getPossibilities().keySet().iterator();
                                boolean match=false;
                                int count=0;
                                        while(iterator.hasNext()){
                                            String key = iterator.next();
                                            if(user.getTags().get(key)!=null&&user.getTags().get(key).equals(decider.getPossibilities().get(key))){
                                                count++;
                                                match=true;
                                            }
                                            else{
                                                match=false;
                                                break;
                                            }
                                        }
                                        
                                        if(match&&count>max){
                                            id=decider.getLink();
                                        }
                                
                            }
                    }
                    return id;
    }

   
        

}
