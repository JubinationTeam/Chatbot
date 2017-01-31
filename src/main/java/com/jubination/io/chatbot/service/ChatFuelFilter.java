/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jubination.io.chatbot.service;

import com.jubination.io.chatbot.backend.pojo.chatfuel.Attachment;
import com.jubination.io.chatbot.backend.pojo.chatfuel.CFMessage;
import com.jubination.io.chatbot.backend.pojo.chatfuel.ChatFuelet;
import com.jubination.io.chatbot.backend.pojo.chatfuel.Payload;
import com.jubination.io.chatbot.backend.pojo.chatfuel.QuickReplies;
import com.jubination.io.chatbot.backend.pojo.web.ChatBotRequest;
import com.jubination.io.chatbot.backend.pojo.web.UserResponse;
import com.jubination.io.chatbot.model.dao.FbSessionDAO;
import com.jubination.io.chatbot.model.pojo.FbSessionIndices;
import com.jubination.io.chatbot.model.pojo.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author MumbaiZone
 */
@Service
@Transactional
public class ChatFuelFilter {

    @Autowired
    FbSessionDAO fbSessionRepo;
    
    private final String url ="http://www.jubination.com/mia/assets/";
    
    //input
      public UserResponse createUserResponse(String fbId,String lastId,String lastAnswer,String name,String gender) {
            UserResponse uRes = new UserResponse();
            FbSessionIndices index=fbSessionRepo.getObject(fbId);
            if(index==null){
               uRes.setSessionId(null);
            }
            else{
                 uRes.setSessionId(index.getSesId());
            }
            uRes.setLastAnswer(lastAnswer);
            uRes.setLastId(lastId);
            uRes.setGender(gender);
            uRes.setFbId(fbId);
            uRes.setWebId("");
            uRes.setName(name.replace("$", " "));
            
            return uRes;
        
      }
    
    //output
    public ChatFuelet convertChatBotRequestToUserResponse(ChatBotRequest chatRequest) {
        
        ChatFuelet fuelet= new ChatFuelet();
        
        
        
     if(chatRequest!=null){
   
         for(Message msg:chatRequest.getBotMessage()){
             if(msg.getType().equalsIgnoreCase("text")){
                    fuelet.getMessages().add(new CFMessage(msg.getValue()));
             }
//             else{
//                 fuelet.getMessages().add(new CFMessage(new Attachment(msg.getType(),new Payload(url+msg.getValue()))));
//             }
         }
         QuickReplies quickReplies = new QuickReplies();
        
         for(String options:chatRequest.getOptions()){
             quickReplies.getBlock_names().add(options);
         }
            fuelet.getMessages().get(fuelet.getMessages().size()-1).getQuick_replies().add(quickReplies);
        
   
    }
     return fuelet;
    }


    void refreshSessionIndex(String fbId, String sesId) {
       if(fbId!=null){
           fbSessionRepo.updateObject(fbId, sesId, "sesId");
       }
    }
    
}
