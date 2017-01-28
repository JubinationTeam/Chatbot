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
        @Autowired
        THPResultService thpResultService;
    
         //Bussiness Logic
        public Chatlet getNextChatlet(ChatletTag chatletTag){
            Chatlet chatletResponse;
            if(chatletTag.getChatletId()==null||chatletTag.getChatletId().isEmpty()){
                // System.out.println("FIRST HIT:");
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
                                            // System.out.println("TVC:");
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
                                        // System.out.println("COC:");
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
          
            return chatletResponse;
        }
        
        
        //Fetch Chatlets
        private Chatlet getFirstChatlet() {
        return chatletRepository.getObject("0");
    }
        private Chatlet getSOCAndODC(Chatlet chatlet,ChatletTag chatletTag){
            
                                                //update user details
                                                chatletTag.setTag(chatletTag.getAnswer());
                                                 tagSetup(chatletTag);
                                                
                                                    //ODC
                                                    if(chatlet.getDeciders()!=null&&!chatlet.getDeciders().isEmpty()){
                                                        String deciderId=getDeciderId(chatlet, chatletTag);
                                                        
                                                        if(deciderId!=null){
                                                            // System.out.println("ODC:");
                                                                return chatletRepository.getObject(deciderId);
                                                        }
                                                            
                                                    }
                                                    //SOC
                                                    // System.out.println("SOC:");
                                                         return chatletRepository.getObject(chatlet.getNext());
        }
        private Chatlet getCOC(Chatlet chatlet,ChatletTag chatletTag){
             //update user details
             chatletTag.setTag(chatletTag.getAnswer());
              tagSetup(chatletTag);
            return chatletRepository.getObject(chatlet.getOptions().get(chatletTag.getAnswer()));
        }
        private Chatlet getSTCAndTDC(Chatlet chatlet, ChatletTag chatletTag){
                                        
                            Iterator<String> iterator=chatlet.getValidationChatlets().keySet().iterator();
                            
                            if(chatlet.getValidationChatlets().keySet().size()>0){
                                            while(iterator.hasNext()){
                                                //validating block
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
                                                            
                                                    //replace chatletTag tag with validated answer 
                                                    chatletTag.setTag(tag);
                                            }
                            }
                            else{
                                chatletTag.setTag(chatletTag.getAnswer());
                            }
                                            
                                                    //update user details
                                                    tagSetup(chatletTag);
                                                    
                                                    System.out.println(chatlet.getDeciders()+" "+chatlet.getTagType());
                                                //TDC
                                                    if(chatlet.getDeciders()!=null&&!chatlet.getDeciders().isEmpty()){
                                                        String deciderId=getDeciderId(chatlet, chatletTag);
                                                        if(deciderId!=null){
                                                            // System.out.println("TDC:");
                                                                return chatletRepository.getObject(deciderId);
                                                        }
                                                        
                                                    }
                                            //STC
                                            // System.out.println("STC:");
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
                              System.out.println("DECIDERS : "+decider.getPossibilities());
                                Iterator<String> iterator=decider.getPossibilities().keySet().iterator();
                                boolean match=false;
                                int count=0;
                           //     int index=0;
                                //match
                                while(iterator.hasNext()){
                                                String key = iterator.next();
                                                System.out.println("KEY :"+key+":"+user.getTags().get(key)+":"+decider.getPossibilities().get(key));
                                                //check presence
                                                
                                           //     System.out.println("presence"+(user.getTags().get(key.split("-")[0])!=null&&!user.getTags().get(key.split("-")[0]).isEmpty())+
                                             //           decider.getPossibilities().get(key).equalsIgnoreCase("true")+"presence"+(user.getTags().get(key.split("-")[0])!=null&&!user.getTags().get(key.split("-")[0]).isEmpty())+decider.getPossibilities().get(key).equalsIgnoreCase("false"));
                                             
                                                if(key.contains("-presence")){
                                                        if(//if presense true is true
                                                                ((user.getTags().get(key.split("-")[0])!=null&&!user.getTags().get(key.split("-")[0]).isEmpty())
                                                                &&
                                                                decider.getPossibilities().get(key).equalsIgnoreCase("true"))
                                                                //if presense false is true
                                                                ||((user.getTags().get(key.split("-")[0])==null||user.getTags().get(key.split("-")[0]).isEmpty())
                                                                &&
                                                                decider.getPossibilities().get(key).equalsIgnoreCase("false")
                                                                )){
                                                            count++;
                                                             
                                                            match=true;
                                                        }
                                                        else{
                                                            count=0;
                                                            match=false;
                                                            break;
                                                        }
                                                }
                                                //check if its equal
                                                else{
                                                    if(user.getTags().get(key)!=null&&user.getTags().get(key).equals(decider.getPossibilities().get(key))){
                                                        count++;
                                                        match=true;
                                                    }
                                                    else{
                                                        count=0;
                                                        match=false;
                                                        break;
                                                    }
                                                }
                                                
                                            // index++;
                                }
                                 
                                  if(match&&count>max){
                                                max=count;
                                                 id=decider.getLink();
                                }
                              

                       }
                        
                         
          }
                    return id;
    }

         //Prepare and Save tags in user
          private void tagSetup(ChatletTag chatletTag){
       //replace chatletTag tag with validated answer 
                                                    User user=null;
                                                    
                                                    //exclusion
                                                    if(!chatletTag.getTag().trim().equalsIgnoreCase("I want to answer this later")&&!chatletTag.getTag().trim().equalsIgnoreCase("Skip")){
                                                        
                                                                //update user details
                                                                if(chatletTag.getTagType().equals("name")||chatletTag.getTagType().equals("email")||chatletTag.getTagType().equals("phone")||chatletTag.getTagType().equals("country")||chatletTag.getTagType().equals("gender")){

                                                                            userRepository.updateObject(chatletTag.getSessionId(),chatletTag.getTag(),chatletTag.getTagType());
                                                                            user=userRepository.getObject(chatletTag.getSessionId());
                                                                            if(user!=null){
                                                                                user.getTags().put(chatletTag.getTagType(), chatletTag.getTag());
                                                                                    userRepository.updateObject(chatletTag.getSessionId(), user.getTags(), "tags");
                                                                            }
                                                                }

                                                                else{
                                                                    user=userRepository.getObject(chatletTag.getSessionId());
                                                                    if(user!=null){
                                                                        user.getTags().put(chatletTag.getTagType(), chatletTag.getTag());
                                                                            userRepository.updateObject(chatletTag.getSessionId(), user.getTags(), "tags");
                                                                    }
                                                                }
                                                    }
                                                    else{
                                                         user=userRepository.getObject(chatletTag.getSessionId());
                                                    }
                                                    
                                                    //calculate result
                                                    if(chatletTag.getTagType().contains("result")){
                                                        if(user!=null){
                                                                thpResultService.saveResultsForTHP(user);
                                                        }
                                                    }
                                                    
                                                    
   }
        

}
