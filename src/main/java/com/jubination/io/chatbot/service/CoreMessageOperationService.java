/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jubination.io.chatbot.service;

import com.jubination.io.chatbot.model.dao.ChatletDAO;
import com.jubination.io.chatbot.model.dao.UserDAO;
import com.jubination.io.chatbot.model.pojo.Chatlet;
import com.jubination.io.chatbot.model.pojo.ChatletTag;
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
        PostProcessingService postProcessor;
    
         //Bussiness Logic
        public Chatlet getNextChatlet(ChatletTag chatletTag){
            Chatlet chatletResponse;
            if(chatletTag==null){
                chatletResponse=getFirstChatlet();
            }
            else{
                Chatlet chatlet =  chatletRepository.getObject(chatletTag.getChatletId());
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
                                                                return chatletRepository.getObject(deciderId);
                                                        }
                                                        
                                                    }
            return chatletRepository.getObject(chatlet.getNext());
        }
        private Chatlet getCOC(Chatlet chatlet,ChatletTag chatletTag){
            return chatletRepository.getObject(chatlet.getOptions().get(chatletTag.getAnswer()));
        }
        private Chatlet getSTCAndTDC(Chatlet chatlet, ChatletTag chatletTag){
                                        
                            Iterator<String> iterator=chatlet.getValidationChatlets().keySet().iterator();
                                            while(iterator.hasNext()){
                                                String nextKey=iterator.next();
                                                if(!postProcessor.validateText(chatlet.getTagType()+"-"+nextKey,chatletTag.getAnswer())){
                                                    return chatletRepository.getObject(chatlet.getValidationChatlets().get(nextKey));
                                                }
                                            }
                                                //TDC
                                                    if(chatlet.getDeciders()!=null&&!chatlet.getDeciders().isEmpty()){
                                                        String deciderId=getDeciderId(chatlet, chatletTag);
                                                        if(deciderId!=null){
                                                                return chatletRepository.getObject(deciderId);
                                                        }
                                                        
                                                    }
                                            //STC
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
             User user=userRepository.getObjectBySession(chatletTag.getSessionId());
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
