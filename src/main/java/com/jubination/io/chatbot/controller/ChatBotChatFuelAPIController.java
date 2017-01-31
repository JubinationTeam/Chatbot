/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jubination.io.chatbot.controller;

import com.jubination.io.chatbot.backend.pojo.chatfuel.ChatFuelet;
import com.jubination.io.chatbot.backend.pojo.web.ChatBotRequest;
import com.jubination.io.chatbot.backend.pojo.web.UserResponse;
import com.jubination.io.chatbot.model.pojo.Chatlet;
import com.jubination.io.chatbot.service.ChatFuelFilter;
import com.jubination.io.chatbot.service.ContextAwareMessageOperationService;
import com.jubination.io.chatbot.service.CoreMessageOperationService;
import com.jubination.io.chatbot.service.CoreRepositoryService;
import com.jubination.io.chatbot.service.FlowProcessingService;
import com.jubination.io.chatbot.service.PostProcessingService;
import com.jubination.io.chatbot.service.PreProcessingService;
import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *
 * @author MumbaiZone
 */
@Controller
public class ChatBotChatFuelAPIController {
        
 @Autowired 
    CoreRepositoryService service;
  @Autowired 
    PreProcessingService preService;
  @Autowired
  CoreMessageOperationService operationService;
    @Autowired
  CoreRepositoryService repService;
    @Autowired 
    PostProcessingService postService;
     @Autowired 
    FlowProcessingService flowService;       
     @Autowired
     ContextAwareMessageOperationService awareOperationService;
     @Autowired
      ChatFuelFilter filter;     
    
        
    @RequestMapping(value="/chatfuel/{fb_id}/{fb_name}/{fb_gender}/{last_processed_block_name}/{last_clicked_button_name}",method=RequestMethod.GET,produces = MediaType.APPLICATION_JSON_VALUE,headers="Accept=*/*")
    public @ResponseBody ChatFuelet process(@PathVariable("fb_id") String fbId,@PathVariable("fb_name") String name,@PathVariable("fb_gender") String gender,@PathVariable("last_processed_block_name") String lastId,@PathVariable("last_clicked_button_name") String lastAnswer,HttpServletRequest request) throws IOException{
        System.out.println("input:"+fbId+name+gender);
        
        
           UserResponse uRes=filter.createUserResponse(fbId, lastId, lastAnswer, name, gender);
           
        
            ChatBotRequest chatRequest=awareOperationService.getContextAwareResponse(uRes);
            if(chatRequest!=null){
                return filter.convertChatBotRequestToUserResponse(chatRequest,null);
            }
            //normal reply
            String sessionId=preService.getRecentSessionId(
                                            uRes,
                                            ""
                                    );
            Chatlet chatlet= operationService.getNextChatlet(
                                            preService.convertWebUserResponseIntoChatletTag(
                                                    uRes,""
                                            )
                                    );
              return  
                      filter.convertChatBotRequestToUserResponse(
                      postService.convertWebChatletIntoChatBotMessage(
                                chatlet, 
                              uRes,sessionId
                            ),chatlet
                      );
             
            
        
    }
}
