/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jubination.io.chatbot.controller;


import com.jubination.io.chatbot.backend.pojo.core.ChatBotRequest;
import com.jubination.io.chatbot.backend.pojo.core.ChatBotResponse;
import com.jubination.io.chatbot.backend.pojo.core.Flow;
import com.jubination.io.chatbot.model.pojo.Chatlet;
import com.jubination.io.chatbot.model.pojo.DashBot;
import com.jubination.io.chatbot.service.CoreRepositoryService;
import com.jubination.io.chatbot.service.PreProcessingService;
import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author MumbaiZone
 */
@Controller
public class ChatBotAPIController {
    
    
 @Autowired 
    CoreRepositoryService service;
     @Autowired 
    PreProcessingService preService;        
    
    @RequestMapping(value="/process",method=RequestMethod.POST,consumes=MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE,headers="Accept=*/*")
    public @ResponseBody ChatBotResponse process(@RequestBody ChatBotRequest cRequest,HttpServletRequest request) throws IOException{
            System.out.println("Chatbot request"+cRequest.getLastId());
       
              return null;
            
        
    }
    
     @RequestMapping(value="/",method=RequestMethod.GET)
    public ModelAndView getFirstPage(HttpServletRequest request) throws IOException{
            return new ModelAndView("index");
        
    }
    
    @RequestMapping(value="/flow/create",method=RequestMethod.POST,consumes=MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE,headers="Accept=*/*")
    public @ResponseBody Flow createFlow(@RequestBody Flow flow,HttpServletRequest request) throws IOException{
           
       return preService.createFlow(flow);
            
    }
    
     
    
    @RequestMapping(value="/chatlet/create",method=RequestMethod.POST,consumes=MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE,headers="Accept=*/*")
    public @ResponseBody Chatlet createChatlet(@RequestBody Chatlet chatlet,HttpServletRequest request) throws IOException{
           
       return service.createChatlet(chatlet);
            
    }
      @RequestMapping(value="/dashbot/create",method=RequestMethod.POST,consumes=MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE,headers="Accept=*/*")
    public @ResponseBody DashBot createDashbot(@RequestBody DashBot dashbot,HttpServletRequest request) throws IOException{
           
       return service.createDashBot(dashbot);
            
    }
    
    
    
}
