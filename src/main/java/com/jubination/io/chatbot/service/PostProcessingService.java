/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jubination.io.chatbot.service;


import com.jubination.io.chatbot.backend.pojo.web.ChatBotRequest;
import com.jubination.io.chatbot.backend.pojo.web.UserResponse;
import com.jubination.io.chatbot.backend.service.core.DashBotUpdater;
import com.jubination.io.chatbot.backend.service.core.RandomNumberGenerator;
import com.jubination.io.chatbot.model.dao.DashBotDAO;
import com.jubination.io.chatbot.model.dao.UserDAO;
import com.jubination.io.chatbot.model.pojo.Chatlet;
import com.jubination.io.chatbot.model.pojo.DashBot;
import com.jubination.io.chatbot.model.pojo.ChatletMessage;
import com.jubination.io.chatbot.model.pojo.MessageSet;
import com.jubination.io.chatbot.model.pojo.User;
import java.util.Iterator;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author MumbaiZone
 */
@Service
@Transactional
public class PostProcessingService {
      @Autowired
    UserDAO userRepository;
      @Autowired
    DashBotDAO dashBotRepository;
          @Autowired
          DashBotUpdater dashBotUpdater;
          @Autowired
          RandomNumberGenerator gen;
          
      
      private static final int imageQuoteCount=16;
      private static final int imageAgeCount=2;
      private static final int imageHabitsCount=4;
      private static final int imageStressCount=3;
      private static final int lineBreak=45;
    
        public ChatBotRequest convertWebChatletIntoChatBotMessage(Chatlet chatlet, UserResponse res,String sessionId){
            
           ChatBotRequest req=new ChatBotRequest();
           req.setAnswerType(chatlet.getAnswerType());
           req.setId(chatlet.getId());
           req.setSessionId(sessionId);
           
           //add front end related data
           User user=userRepository.getObject(sessionId);
           if(user!=null){
                req.setGender(user.getGender());
           }
          
           
           Iterator<String> iterator=chatlet.getOptions().keySet().iterator();
           while(iterator.hasNext()){
               req.getOptions().add(iterator.next());
           }
          
           
           MessageSet messageSet=chatlet.getBotMessages().get(new Random().nextInt(chatlet.getBotMessages().size()));
            if(res.getLastAnswer().equalsIgnoreCase("I am Interested")){
                            req.getBotMessage().add(new ChatletMessage("text", "Thank you, my team will get back to you."));
        }
               for(ChatletMessage message:messageSet.getMessages()){
                   if(message.getType().equals("text")){
                       String taggedValue=doDynamicLinking(user,message.getValue());
                       Pattern re = Pattern.compile("[^.!?\\s][^.!?]*(?:[.!?](?!['\"]?\\s|$)[^.!?]*)*[.!?]?['\"]?(?=\\s|$)", Pattern.MULTILINE | Pattern.COMMENTS);
                        Matcher reMatcher = re.matcher(taggedValue);
                        while (reMatcher.find()) {
                            StringBuilder val=new StringBuilder(reMatcher.group());
                            
                            if(val.toString().length()<lineBreak){
                                if(reMatcher.find()){
                                    val.append(" ").append(reMatcher.group());
                                }
                            }
                            
                            
                            req.getBotMessage().add(new ChatletMessage(message.getType(), val.toString()));
                        }
                       
                   }
                   else{
                            String stringValue=doDynamicLinking(user, message.getValue());
//                            String stringValue=val.toString();
                            req.getBotMessage().add(new ChatletMessage(message.getType(), stringValue));
                            // System.out.println(stringValue);
                   }
               }
                 
            //save and send outgoing dashBot data
            if(req.getBotMessage()!=null){
                for(ChatletMessage message:req.getBotMessage()){
                        DashBot outgoing = new DashBot(sessionId,message.getValue());
                        dashBotUpdater.sendAutomatedUpdate(outgoing, "outgoing");
                        dashBotRepository.saveObject(outgoing);
                        // System.out.println(message.getValue());
                }
            }
           
          
           return req;

        }

    private String doDynamicLinking(User user,String text) {
//        System.out.println("in : "+text);
        if(text!=null){
      
       text=text.replace("[<>]", "");
       
          while(text.contains("[")&&text.contains("<")&&text.contains(">")&&text.contains("]")){

                        String preTagText="";
                        String postTagText="";

                        //pre
                        String preText=text.split("\\[")[0];

                        //pretag
                        if(text.split("<")[0].charAt(text.split("<")[0].length()-1)=='['){
                            preTagText="";
                        }else{
                         preTagText=text.split("<")[0].split("\\[")[1];

                        }
                    //tag
                        String tag=text.split("\\]")[0].split("\\[")[1].split(">")[0].split("<")[1];

                    //posttag
                     if(text.split("\\]")[0].charAt(text.split("\\]")[0].length()-1)=='>'){
                            postTagText="";
                        }else{
                         postTagText=text.split("\\]")[0].split(">")[1];
                        }

                    //post
                       String postText="";
                       int index=text.indexOf("]");
                       
                       if(index>=0&&(index<text.length()-1)){
                            postText=text.substring(index+1);
                       }
                       else{
                           text=text.replace("]", "");
                       }
                       
           //get tagged text
                    text=preText+getTagText(user,preTagText,tag, postTagText)+postText;
                      // System.out.println(text+"||||||||||||||||"+tag);
             
        }
        }
        
//        System.out.println("out : "+text);
        return text;
    }

    private String getTagText(User user,String preTag,String tag,String postTag) {
        // System.out.println(sessionId+"SESSION TAG:::::::");
        
        String value=null;
        // System.out.println(tag+"TAG:::::::::::::::::::::::;");
        //User details
        if(user!=null){
                switch(tag){
                    case "name":
                        value=user.getName();
                        break;
                    case "country":
                        value=user.getCountry();
                        break;
                    case "email":
                        value=user.getEmail();
                        break;
                    case "phone":
                        value=user.getPhone();
                        break;
                    default:
                        if(user.getResult()!=null){
                            value=user.getResult().get(tag);
                        }
                        break;
                }
        }
        
        //Image location
        if(tag.contains("image")){
            String path="";
            for(int i=0;i<tag.split("-").length;i++){
                if(i==0){
                    path+=tag.split("-")[i];
                }
                else{
                    path+="/"+tag.split("-")[i];
                }
            }
            if(tag.contains("drinkAdvice")){
                path=path+"-0";
            }
            else if(tag.contains("age")){
                path+="-"+gen.generate(imageAgeCount+1);
            }
            else if(tag.contains("habits")){
                path+="-"+gen.generate(imageHabitsCount+1);
            }
            else if(tag.contains("quote")){
                path+="-"+gen.generate(imageQuoteCount+1);
            }
             else if(tag.contains("stress")){
                path+="-"+gen.generate(imageStressCount+1);
            }
            return preTag+path+postTag;
        }
        
        //font design
//        if(tag.split("%")!=null&&tag.split("%").length>=2){
//            String text=tag.split("%")[0];
//            return preTag+"<"+tag.split("%")[1]+">"+text+"</"+tag.split("%")[1]+">"+postTag;
//            
//        }
        
        if(value==null){
            return "";
        }
        // System.out.println(value+"TAG:::::::::::::::::::::::;");
        return preTag+value+postTag;
        
        
    }
    
    
        
}
