/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jubination.io.chatbot.service;

import com.jubination.io.chatbot.backend.pojo.chatfuel.Attachment;
import com.jubination.io.chatbot.backend.pojo.chatfuel.Message;
import com.jubination.io.chatbot.backend.pojo.chatfuel.ChatFuelet;
import com.jubination.io.chatbot.backend.pojo.chatfuel.Payload;
import com.jubination.io.chatbot.backend.pojo.chatfuel.QuickReplies;
import com.jubination.io.chatbot.backend.pojo.web.ChatBotRequest;
import com.jubination.io.chatbot.backend.pojo.web.UserResponse;
import com.jubination.io.chatbot.model.dao.FbSessionDAO;
import com.jubination.io.chatbot.model.dao.UserDAO;
import com.jubination.io.chatbot.model.pojo.Chatlet;
import com.jubination.io.chatbot.model.pojo.FbSessionIndices;
import com.jubination.io.chatbot.model.pojo.ChatletMessage;
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
public class ChatFuelService {

    @Autowired
    FbSessionDAO fbSessionRepo;
    @Autowired
    UserDAO userRepository;
    @Autowired
    THPResultService resultService;
    
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
    public ChatFuelet convertChatBotRequestToUserResponse(ChatBotRequest chatRequest,Chatlet chatlet) {
                    ChatFuelet fuelet= new ChatFuelet();
                    if(chatRequest!=null){
                        
                    if(chatlet.getAnswerType().equalsIgnoreCase("text")){
                     
                        for(ChatletMessage msg:chatRequest.getBotMessage()){
                            if(msg.getType().equalsIgnoreCase("text")){
                                   fuelet.getMessages().add(new Message(msg.getValue()));
                            }
//                            else{
//                                fuelet.getMessages().add(new Message(new Attachment(msg.getType(),new Payload(url+msg.getValue()))));
//                            }
                        }
                        
                       
                        
                    }
                    else if(chatlet.getAnswerType().equalsIgnoreCase("option")){
                     StringBuilder text=new StringBuilder();
                        for(ChatletMessage msg:chatRequest.getBotMessage()){
                            if(msg.getType().equalsIgnoreCase("text")){
                                text.append(msg.getValue());
                                   
                            }
//                            else{
//                                fuelet.getMessages().add(new Message(new Attachment(msg.getType(),new Payload(url+msg.getValue()))));
//                            }
                        }
                        fuelet.getMessages().add(new Message(text.toString()));
                        
                      
                       Iterator<String> iterator=chatlet.getOptions().keySet().iterator();
                        while(iterator.hasNext()){
                            String option=iterator.next();
                           fuelet.getMessages().get(fuelet.getMessages().size()-1).getQuick_replies().add(new QuickReplies(option,chatlet.getOptions().get(option)));
                        }
                    }
                       
                   }
                    return fuelet;
    }


    void refreshSessionIndex(String fbId, String sesId) {
       if(fbId!=null){
           fbSessionRepo.updateObject(fbId, sesId, "sesId");
       }
    }

    
    //Chat fuelSimple service-------------------------------------------------------
    public void createUser(String fbId, String name, String gender) {
        User user = new User(fbId+":cf:fb", name, gender, fbId);
        user.getTags().put("gender", gender);
        user.getTags().put("name", name);
                
        if(userRepository.getObject(fbId+":cf:fb")==null){
                userRepository.saveObject(user);
        }
        
    }

    public void updateUser(String fbId, String type, String value) {
        //update user details
       
                        User user=userRepository.getObject(fbId+":cf:fb");
                           if(user!=null){     
                            if(type.equals("name")||type.equals("email")||type.equals("phone")||type.equals("country")||type.equals("gender")){

                                        userRepository.updateObject(fbId+":cf:fb",value,type);
                                        user=userRepository.getObject(fbId+":cf:fb");
                                        if(user!=null){
                                            user.getTags().put(type, value);
                                                userRepository.updateObject(fbId+":cf:fb", user.getTags(), "tags");
                                        }
                            }

                            else{
                                user=userRepository.getObject(fbId+":cf:fb");
                                if(user!=null){
                                    user.getTags().put(type, value);
                                        userRepository.updateObject(fbId+":cf:fb", user.getTags(), "tags");
                                }
                            }
                           }
    }
    public void saveResult(String fbId) {
        //update user details
                        User user=userRepository.getObject(fbId+":cf:fb");
                        if(user!=null){
                            resultService.saveResults(user);
                        }
    }

   
    public ChatFuelet prepareRisk(String fbId) {
        ChatFuelet fuelet = new ChatFuelet();
        fuelet.getMessages().add(new Message("Lifestyle diseases pose a large threat to our health and families."));
       fuelet.getMessages().add(new Message("Good thing however is they are preventable and we can help you take early action."));
       fuelet.getMessages().add(new Message("Anyways, here are your risks we need to manage. We are discussing the most prevelant lifestyle diseases here."));
       fuelet.getMessages().add(new Message("1) Diabetes: You have [<diabetes-count>] risk-factors : [<diabetes-text>]"));
       fuelet.getMessages().add(new Message("2) Heart Disease: You have [<heart-count>] risk-factors : [<heart-text>]"));
       fuelet.getMessages().add(new Message("3) Chronic Liver Disease: You have [<liver-count>] risk-factors : [<liver-text>]"));
       fuelet.getMessages().add(new Message("4) Chronic Kidney Disease: You have [<kidney-count>] risk-factors : [<kidney-text>]"));
       fuelet.getMessages().add(new Message("5) Vitamin D and Vitamin B12 deficiencies: You have [<vitamin-count>] risk-factors : [<vitamin-text>]"));
       return fuelet;
    
    }
    
    
}
