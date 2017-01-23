/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jubination.io.chatbot.service;


import com.jubination.io.chatbot.backend.pojo.core.ChatBotRequest;
import com.jubination.io.chatbot.model.pojo.Chatlet;
import com.jubination.io.chatbot.model.pojo.Message;
import com.jubination.io.chatbot.model.pojo.MessageSet;
import java.util.Iterator;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author MumbaiZone
 */
@Service
@Transactional
public class PostProcessingService {
    
    //Validating Text based /... return if the vslidation goes wrong  any of the cases and return the value evertimeth case is true
        String validatedText(String type, String text) {
            String validatedText=null;
                switch(type){
                    
                    case "country-number":
                                  Pattern re = Pattern.compile("[0-9]+");
                                    Matcher reMatcher = re.matcher(text);
                                   while(reMatcher.find()){
                                       return null;
                                   } 
                                   validatedText=text.substring(0, 1).toUpperCase()+text.substring(1).toLowerCase();
                       
                        break;
                        
                    case "name-number":
                                    re = Pattern.compile("[0-9]+");
                                   reMatcher = re.matcher(text);
                                   while(reMatcher.find()){
                                       return null;
                                   } 
                                   if(text!=null){
                                    text=text.trim();
                                    if(text.contains(" ")){
                                         
                                         if(text.split(" ")[text.split(" ").length-1].length()==1&&text.split(" ").length>1){
                                             text=text.split(" ")[text.split(" ").length-2];
                                             validatedText=text.substring(0, 1).toUpperCase()+text.substring(1).toLowerCase();
                                         }
                                         else{
                                             text=text.split(" ")[text.split(" ").length-1];
                                             validatedText=text.substring(0, 1).toUpperCase()+text.substring(1).toLowerCase();
                                         }

                                      }
                                    else{
                                        validatedText=text.substring(0, 1).toUpperCase()+text.substring(1).toLowerCase();
                                    }
                                }
                        break;
                        
                    case "age-charactersOnly":
                                re = Pattern.compile("[0-9]+");
                                   reMatcher = re.matcher(text);
                                   int count=0;
                                   while (reMatcher.find()){
                                        if(count==0){
                                            validatedText= reMatcher.group();
                                        }
                                        count++;
                                   }
                                   if(count<1){
                                       return null;
                                   }
                                   if(validatedText==null){
                                       validatedText=text;
                                   }
                        break;
                    case "age-tooYoung":
                        re = Pattern.compile("[0-9]+");
                                   reMatcher = re.matcher(text);
                                   
                                   if(reMatcher.find()){
                                       String val=reMatcher.group();
                                       if(Integer.parseInt(val)<1){
                                           return null;
                                       }
                                       else{
                                          validatedText=val;
                                       }
                                   } 
                                   
                                  if(validatedText==null){
                                        validatedText=text;
                                   }
                        break;
                    case "age-tooOld":
                         re = Pattern.compile("[0-9]+");
                                   reMatcher = re.matcher(text);
                                   
                                   if(reMatcher.find()){
                                       String val=reMatcher.group();
                                       if(Integer.parseInt(val)>100){
                                           return null;
                                       }
                                       else{
                                           validatedText=val;
                                       }
                                   } 
                                   if(validatedText==null){
                                        validatedText=text;
                                   }
                        break;
                    case "age-multipleNumbers":
                        re = Pattern.compile("[0-9]+");
                                   reMatcher = re.matcher(text);
                                   count=0;
                                   while (reMatcher.find()){
                                        if(count==0){
                                            validatedText= reMatcher.group();
                                        }
                                        count++;
                                   }
                                   if(count>1){
                                       return null;
                                   }
                                   if(validatedText==null){
                                       validatedText=text;
                                   }
                        break;
                        
                    case "height-huge":
                              re = Pattern.compile("[0-9]+");
                                   reMatcher = re.matcher(text);
                                   if(reMatcher.find()){
                                       String val=reMatcher.group();
                                       if(Integer.parseInt(val)>8){
                                           return null;
                                       }
                                       else{
                                           
                                                String subVal="0";
                                                    if(reMatcher.find()){
                                                             subVal=reMatcher.group();
                                                     }
                                                    validatedText=val+"."+subVal;

                                            }
                                   } 
                                   if(validatedText==null){
                                        validatedText=text;
                                   }
                        break;
                    case "height-charactersOnly":
                                 re = Pattern.compile("[0-9]+");
                                   reMatcher = re.matcher(text);
                                  
                                   count=0;
                                   while (reMatcher.find()){
                                        if(count==0){
                                                    validatedText=reMatcher.group();
                                        }
                                        else if(count==1){
                                            validatedText=validatedText+"."+reMatcher.group();
                                        }
                                        count++;
                                   }
                                   if(count<1){
                                       return null;
                                   }
                                   
                                   if(validatedText==null){
                                       validatedText=text;
                                   }
                                   
                        break;
                    case "height-less":
                         re = Pattern.compile("[0-9]+");
                                   reMatcher = re.matcher(text);
                                   if(reMatcher.find()){
                                       String val=reMatcher.group();
                                       if(Integer.parseInt(val)<2){
                                           return null;
                                       }
                                       else{
                                           String subVal="0";
                                                    if(reMatcher.find()){
                                                             subVal=reMatcher.group();
                                                     }
                                                    validatedText=val+"."+subVal;
                                       }
                                   } 
                                   if(validatedText==null){
                                        validatedText=text;
                                   }
                        break;
                    case "height-multipleNumbers":
                        re = Pattern.compile("[0-9]+");
                                   reMatcher = re.matcher(text);
                                   
                                   count=0;
                                   while (reMatcher.find()){
                                        if(count==0){
                                            validatedText= reMatcher.group();
                                        }
                                        else if(count==1){
                                            int inches=Integer.parseInt(reMatcher.group());
                                            if(inches>12){
                                                return null;
                                            }validatedText=validatedText+"."+inches;
                                        }
                                        count++;
                                   }
                                   if(count>2){
                                                return null;
                                   }
                                   if(validatedText==null){
                                       validatedText=text;
                                   }
                        break;
                    
                    case "weight-huge":
                        re = Pattern.compile("[0-9]+");
                                   reMatcher = re.matcher(text);
                                   
                                   if(reMatcher.find()){
                                       String val=reMatcher.group();
                                       if(Integer.parseInt(val)>650){
                                           return null;
                                       }
                                       else{
                                           validatedText=val;
                                       }
                                   }
                                   if(validatedText==null){
                                        validatedText=text;
                                   }
                        break;
                    case "weight-charactersOnly":
                        re = Pattern.compile("[0-9]+");
                                   reMatcher = re.matcher(text);
                                  count=0;
                                   while (reMatcher.find()){
                                        if(count==0){
                                            validatedText= reMatcher.group();
                                        }
                                        count++;
                                   }
                                   if(count<1){
                                       return null;
                                   }
                                   if(validatedText==null){
                                       validatedText=text;
                                   }
                        break;
                    case "weight-less":
                        re = Pattern.compile("[0-9]+");
                                   reMatcher = re.matcher(text);
                                   
                                   if(reMatcher.find()){
                                       String val=reMatcher.group();
                                       if(Integer.parseInt(val)<10){
                                           return null;
                                       }
                                       else{
                                          validatedText=val;
                                       }
                                   }
                                   if(validatedText==null){
                                        validatedText=text;
                                   }
                        break;
                    case "weight-multipleNumbers":
                         re = Pattern.compile("[0-9]+");
                                   reMatcher = re.matcher(text);
                                  count=0;
                                   while (reMatcher.find()){
                                        if(count==0){
                                            validatedText= reMatcher.group();
                                        }
                                        count++;
                                   }
                                   if(count>1){
                                       return null;
                                   }
                                   if(validatedText==null){
                                       validatedText=text;
                                   }
                        break;
                    
                    case "waistSize-huge":
                        re = Pattern.compile("[0-9]+");
                                   reMatcher = re.matcher(text);
                                   
                                   if(reMatcher.find()){
                                       String val=reMatcher.group();
                                       if(Integer.parseInt(val)>100){
                                           return null;
                                       }
                                       else{
                                           validatedText=val;
                                       }
                                   } if(validatedText==null){
                                        validatedText=text;
                                   }
                        break;
                    case "waistSize-charactersOnly":
                        re = Pattern.compile("[0-9]+");
                                   reMatcher = re.matcher(text);
                                   count=0;
                                   while (reMatcher.find()){
                                        if(count==0){
                                            validatedText= reMatcher.group();
                                        }
                                        count++;
                                   }
                                   if(count<1){
                                       return null;
                                   }
                                   if(validatedText==null){
                                       validatedText=text;
                                   }
                        break;
                    case "waistSize-less":
                        re = Pattern.compile("[0-9]+");
                                   reMatcher = re.matcher(text);
                                   
                                   if(reMatcher.find()){
                                       String val=reMatcher.group();
                                       if(Integer.parseInt(val)<10){
                                           return null;
                                       }
                                       else{
                                          validatedText=val;
                                       }
                                   } 
                                   if(validatedText==null){
                                        validatedText=text;
                                   }
                        break;
                    case "waistSize-multipleNumbers":
                         re = Pattern.compile("[0-9]+");
                                   reMatcher = re.matcher(text);
                                   count=0;
                                   while (reMatcher.find()){
                                        if(count==0){
                                            validatedText= reMatcher.group();
                                        }
                                        count++;
                                   }
                                   if(count<1){
                                       return null;
                                   }
                                   if(validatedText==null){
                                       validatedText=text;
                                   }
                        break;
                        
                    case "email-invalid":
                        re = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
                        reMatcher = re.matcher(text);
                         if(reMatcher.find()){
                                       validatedText= reMatcher.group();
                                   } 
                         else{
                             return null;
                         }
                        break;
                    case "phone-invalid":
                         re = Pattern.compile("[0-9][0-9][0-9][0-9][0-9]([0-9]+)");
                                   reMatcher = re.matcher(text);
                                   if(reMatcher.find()){
                                                validatedText= reMatcher.group();
                                       
                                   } 
                                   else{
                                       return null;
                                   }
                                    if(validatedText==null){
                                       validatedText=text;
                                   }
                        break;
                    
                }
            
            return validatedText;
        }
        
        public ChatBotRequest convertWebChatletIntoChatBotMessage(Chatlet chatlet, String sessionId){
            
           ChatBotRequest req=new ChatBotRequest();
           req.setAnswerType(chatlet.getAnswerType());
           req.setId(chatlet.getId());
           req.setSessionId(sessionId);
           
           Iterator<String> iterator=chatlet.getOptions().keySet().iterator();
           while(iterator.hasNext()){
               req.getOptions().add(iterator.next());
           }
          
           
           MessageSet messageSet=chatlet.getBotMessages().get(new Random().nextInt(chatlet.getBotMessages().size()));
               for(Message message:messageSet.getMessages()){
                   if(message.getType().equals("text")){
                       Pattern re = Pattern.compile("[^.!?\\s][^.!?]*(?:[.!?](?!['\"]?\\s|$)[^.!?]*)*[.!?]?['\"]?(?=\\s|$)", Pattern.MULTILINE | Pattern.COMMENTS);
                        Matcher reMatcher = re.matcher(message.getValue());
                        while (reMatcher.find()) {
                            System.out.println(reMatcher.group());
                            req.getBotMessage().add(new Message(message.getType(), reMatcher.group()));
                        }
                       
                   }
                   else{
                       req.getBotMessage().add(message);
                   }
               }
           
          
           return req;

        }
        
}
