/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jubination.io.chatbot.service;

import com.jubination.io.chatbot.backend.pojo.core.ChatletUnit;
import com.jubination.io.chatbot.backend.pojo.core.Flow;
import com.jubination.io.chatbot.model.pojo.Chatlet;
import com.jubination.io.chatbot.model.pojo.Decider;
import com.jubination.io.chatbot.model.pojo.MessageSet;
import java.util.HashMap;
import java.util.List;
import java.util.LinkedHashMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional
public class FlowProcessingService {
    
        @Autowired
        CoreRepositoryService service;
    
        //Flow Creation
        public Flow createFlow(Flow flow) {
        for(ChatletUnit chatletUnit:flow.getChatlets()){
            switch (chatletUnit.getType()) {
                case "STC": 
                       chatletUnit.setChatlet(createSTC(
                                chatletUnit.getChatlet().getPrev(), 
                                chatletUnit.getChatlet().getId(), 
                                chatletUnit.getChatlet().getNext(), 
                                chatletUnit.getChatlet().getBotMessages(), 
                                chatletUnit.getChatlet().getValidationChatlets(), 
                                chatletUnit.getChatlet().getTagType(), 
                                chatletUnit.getChatlet().getWebVisible(), 
                                chatletUnit.getChatlet().getFbVisible()
                       )
                        );
                    break;
                case "SOC":
                        chatletUnit.setChatlet(createSOC(
                                chatletUnit.getChatlet().getPrev(), 
                                chatletUnit.getChatlet().getId(), 
                                chatletUnit.getChatlet().getNext(), 
                                chatletUnit.getChatlet().getBotMessages(), 
                                chatletUnit.getChatlet().getOptions(),
                                chatletUnit.getChatlet().getTagType(), 
                                chatletUnit.getChatlet().getWebVisible(), 
                                chatletUnit.getChatlet().getFbVisible()
                        ));
                    break;
                 case "ODC":
                        chatletUnit.setChatlet(createODC(
                                chatletUnit.getChatlet().getPrev(), 
                                chatletUnit.getChatlet().getId(), 
                                chatletUnit.getChatlet().getNext(), 
                                chatletUnit.getChatlet().getBotMessages(), 
                                chatletUnit.getChatlet().getOptions(),
                                chatletUnit.getChatlet().getDeciders(),
                                chatletUnit.getChatlet().getTagType(), 
                                chatletUnit.getChatlet().getWebVisible(), 
                                chatletUnit.getChatlet().getFbVisible()
                        ));
                    break;
                case "SHC":
                    chatletUnit.setChatlet(createSHC(
                                chatletUnit.getChatlet().getPrev(), 
                                chatletUnit.getChatlet().getId(), 
                                chatletUnit.getChatlet().getNext(), 
                                chatletUnit.getChatlet().getBotMessages(),
                                chatletUnit.getChatlet().getValidationChatlets(),  
                                chatletUnit.getChatlet().getOptions(),
                                chatletUnit.getChatlet().getTagType(), 
                                chatletUnit.getChatlet().getWebVisible(), 
                                chatletUnit.getChatlet().getFbVisible()
                    ));
                    break;
                case "COC":
                    chatletUnit.setChatlet(createCOC(
                                chatletUnit.getChatlet().getPrev(), 
                                chatletUnit.getChatlet().getId(), 
                                chatletUnit.getChatlet().getBotMessages(), 
                                chatletUnit.getChatlet().getOptions(),
                                chatletUnit.getChatlet().getTagType(), 
                                chatletUnit.getChatlet().getWebVisible(), 
                                chatletUnit.getChatlet().getFbVisible()
                    ));
                    break;
                case "TVC":
                    chatletUnit.setChatlet(createTVC(
                                chatletUnit.getChatlet().getPrev(), 
                                chatletUnit.getChatlet().getId(), 
                                chatletUnit.getChatlet().getNext(), 
                                chatletUnit.getChatlet().getBotMessages(), 
                                chatletUnit.getChatlet().getTagType(), 
                                chatletUnit.getChatlet().getWebVisible(), 
                                chatletUnit.getChatlet().getFbVisible()
                    ));
                    break;
                case "TDC":
                    chatletUnit.setChatlet(createTDC(
                                chatletUnit.getChatlet().getPrev(), 
                                chatletUnit.getChatlet().getId(), 
                                chatletUnit.getChatlet().getNext(), 
                                chatletUnit.getChatlet().getBotMessages(), 
                                chatletUnit.getChatlet().getValidationChatlets(),
                                chatletUnit.getChatlet().getDeciders(),
                                chatletUnit.getChatlet().getTagType(), 
                                chatletUnit.getChatlet().getWebVisible(), 
                                chatletUnit.getChatlet().getFbVisible()
                    ));
                    break;
                default:
                        // System.out.println("Not a valid option");
                    break;
            }
            
        }
        return flow;
                
    }
        
        //Chatlet Category creation
        public Chatlet createSTC(String prev, String id, String next,List<MessageSet> botMessages, HashMap<String, String> validationChatlet, String tagType, Boolean web, Boolean fb){
     Chatlet chatlet = new Chatlet();
     chatlet.setAnswerType("text");
     chatlet.setBotMessages(botMessages);
     chatlet.setFbVisible(fb);
     chatlet.setWebVisible(web);
     chatlet.setId(id);
     chatlet.setNext(next);
     chatlet.setPrev(prev);
     chatlet.setTagType(tagType);
     chatlet.setValidationChatlets(validationChatlet);
     return service.createChatlet(chatlet);
 }
        public Chatlet createSHC(String prev, String id, String next,List<MessageSet> botMessages, HashMap<String, String> validationChatlet, LinkedHashMap<String, String> options, String tagType, Boolean web, Boolean fb){
            Chatlet chatlet = new Chatlet();
            chatlet.setAnswerType("text-option");
            chatlet.setBotMessages(botMessages);
            chatlet.setFbVisible(fb);
            chatlet.setWebVisible(web);
            chatlet.setId(id);
            chatlet.setNext(next);
            chatlet.setPrev(prev);
            chatlet.setTagType(tagType);
            chatlet.setValidationChatlets(validationChatlet);
            chatlet.setOptions(options);
            return service.createChatlet(chatlet);
        }
        public Chatlet createSOC(String prev, String id, String next,List<MessageSet> botMessages, LinkedHashMap<String, String> options, String tagType, Boolean web, Boolean fb){
            Chatlet chatlet = new Chatlet();
            chatlet.setAnswerType("option");
            chatlet.setBotMessages(botMessages);
            chatlet.setFbVisible(fb);
            chatlet.setWebVisible(web);
            chatlet.setId(id);
            chatlet.setNext(next);
            chatlet.setPrev(prev);
            chatlet.setTagType(tagType);
            chatlet.setOptions(options);
            return service.createChatlet(chatlet);
        }
        public Chatlet createCOC(String prev, String id, List<MessageSet> botMessages, LinkedHashMap<String, String> options, String tagType, Boolean web, Boolean fb){
            Chatlet chatlet = new Chatlet();
            chatlet.setAnswerType("option");
            chatlet.setBotMessages(botMessages);
            chatlet.setFbVisible(fb);
            chatlet.setWebVisible(web);
            chatlet.setId(id);
            chatlet.setPrev(prev);
            chatlet.setTagType(tagType);
            chatlet.setOptions(options);
            chatlet.setConditionBlock(true);
            return service.createChatlet(chatlet);
        }
        public Chatlet createTVC(String prev, String id, String next,List<MessageSet> botMessages, String tagType, Boolean web, Boolean fb){
                Chatlet chatlet = new Chatlet();
                chatlet.setAnswerType("text");
                chatlet.setBotMessages(botMessages);
                chatlet.setFbVisible(fb);
                chatlet.setWebVisible(web);
                chatlet.setId(id);
                chatlet.setNext(next);
                chatlet.setPrev(prev);
                chatlet.setTagType(tagType);
                chatlet.setValidationBlock(true);
                return service.createChatlet(chatlet);
            }
        public Chatlet createTDC(String prev, String id, String next,List<MessageSet> botMessages, HashMap<String, String> validationChatlet,List<Decider> deciders,  String tagType, Boolean web, Boolean fb){
            Chatlet chatlet = new Chatlet();
            chatlet.setAnswerType("text");
            chatlet.setBotMessages(botMessages);
            chatlet.setFbVisible(fb);
            chatlet.setWebVisible(web);
            chatlet.setId(id);
            chatlet.setNext(next);
            chatlet.setPrev(prev);
            chatlet.setTagType(tagType);
            chatlet.setValidationChatlets(validationChatlet);
            chatlet.setDeciders(deciders);
            return service.createChatlet(chatlet);
        }
        public Chatlet createODC(String prev, String id, String next,List<MessageSet> botMessages, LinkedHashMap<String, String> options,List<Decider> deciders,  String tagType, Boolean web, Boolean fb){
                Chatlet chatlet = new Chatlet();
                chatlet.setAnswerType("option");
                chatlet.setBotMessages(botMessages);
                chatlet.setFbVisible(fb);
                chatlet.setWebVisible(web);
                chatlet.setId(id);
                chatlet.setNext(next);
                chatlet.setPrev(prev);
                chatlet.setTagType(tagType);
                chatlet.setOptions(options);
                chatlet.setDeciders(deciders);
                return service.createChatlet(chatlet);
            }
       
}
