/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jubination.io.chatbot.controller;

import com.jubination.io.chatbot.backend.pojo.web.ChatBotRequest;
import com.jubination.io.chatbot.backend.pojo.web.UserResponse;
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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *
 * @author MumbaiZone
 */
@Controller
public class ChatBotWebAPIController {
        
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
    
    
    @RequestMapping(value="/process",method=RequestMethod.POST,consumes=MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE,headers="Accept=*/*")
    public @ResponseBody ChatBotRequest process(@RequestBody UserResponse uRes,HttpServletRequest request) throws IOException{
            // System.out.println("Web Id : "+uRes.getWebId());
            
            //context aware reply
            ChatBotRequest chatRequest=awareOperationService.getContextAwareResponse(uRes);
            if(chatRequest!=null){
                return chatRequest;
            }
            //normal reply
              return  
                      postService.convertWebChatletIntoChatBotMessage(
                                    operationService.getNextChatlet(
                                            preService.convertWebUserResponseIntoChatletTag(
                                                    uRes,request.getSession().getId()
                                            )
                                    ), 
                              uRes,
                                    preService.getRecentSessionId(
                                            uRes,
                                            request.getSession().getId()
                                    )
                            );
             
            
        
    }
}
