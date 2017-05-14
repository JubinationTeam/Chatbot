/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jubination.io.chatbot.service;

import com.jubination.io.chatbot.backend.service.core.LMSUpdater;
import com.jubination.io.chatbot.model.dao.UserDAO;
import com.jubination.io.chatbot.model.pojo.User;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.http.Consts;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author MumbaiZone
 */
@Service
@Transactional
public class ReligareService extends ResultService{

    @Autowired
        UserDAO  userRepository;  
    
    
    
    
    //Validating Text based /... return if the vslidation goes wrong  any of the cases and return the value evertime the case is true
    @Override
        String validatedText(String type, String text,User user) {
            String validatedText=null;
                switch(type){
                    case "members-characters":
                                validatedText=charOnly(text);
                                
                                validatedText=religareMembers(text,validatedText);
                        break;
                    case "members-less":
                        validatedText=lessThan(text, 1);
                                validatedText=religareMembers(text,validatedText);
                        break;
                    case "members-more":
                         validatedText=moreThan(text,4);
                                validatedText=religareMembers(text,validatedText);
                        break;
                    case "members-multipleNumbers":
                       validatedText=multipleNum(text);
                                validatedText=religareMembers(text,validatedText);
                        break;
                     case "age-charactersOnly":
                                validatedText=charOnly(text);
                        break;
                    case "age-tooYoung":
                        validatedText=lessThan(text, 1);
                        break;
                    case "age-tooOld":
                         validatedText=moreThan(text,100);
                        break;
                    case "age-multipleNumbers":
                       validatedText=multipleNum(text);
                        break;   
                        
                    case "children-characters":
                                validatedText=charOnly(text);
                                validatedText=religareChildren(text,validatedText);
                        break;
                    case "children-less":
                        validatedText=lessThan(text, 0);
                                validatedText=religareChildren(text,validatedText);
                        break;
                    case "children-more":
                         validatedText=moreThan(text,3);
                                validatedText=religareChildren(text,validatedText);
                        break;
                    case "children-multipleNumbers":
                       validatedText=multipleNum(text);
                                validatedText=religareChildren(text,validatedText);
                                if(validatedText!=null){
                                    int members=Integer.parseInt(user.getTags().get("members"));
                                    int children=Integer.parseInt(validatedText);
                                    if(children>=members){
                                        validatedText=null;
                                    }
                                }
                        break;
                    case "email-invalid":
                       validatedText=emailValidate(text);
                        break;
                    case "phone-invalid":
                         validatedText=phoneValidate(text);
                       if(validatedText!=null){
                           Random r =new Random();
                           String otp="";
                           while(otp.length()<4){
                               otp=otp.concat(Integer.toString(r.nextInt(9)));
                           }
                           user.getTags().put("otppass", otp);
                           userRepository.updateObject(user.getSesId(), user.getTags(), "tags");
                                    sendOtp(validatedText,otp);
                         }
                        break;
                    case "otp-invalid":
                        
                           if(text.trim().equals(user.getTags().get("otppass"))){
                               validatedText=text;
                           }
                           else{
                               validatedText=null;
                           }
                           break;
                    
                }
            
            return validatedText;
        }
        private String religareMembers(String text,String validatedText){
            String val=text.trim().toLowerCase();
                                switch(val){
                                    case "one": validatedText="1";
                                    break;
                                    case "two": validatedText="2";
                                    break;
                                    case "three": validatedText="3";
                                    break;
                                    case "four": validatedText="4";
                                    break;
                                }
                                return validatedText;
        }
        private String religareChildren(String text, String validatedText){
            String val=text.trim().toLowerCase();
                                switch(val){
                                    case "one": validatedText="1";
                                    break;
                                    case "two": validatedText="2";
                                    break;
                                    case "three": validatedText="3";
                                    break;
                                    case "zero": validatedText="0";
                                    break;
                                    case "none": validatedText="0";
                                    break;
                                    case "na": validatedText="0";
                                    break;
                                    case "not applicable": validatedText="0";
                                    break;
                                }
                                
                                
                                return validatedText;
        }
        private String charOnly(String text){
            String validatedText=null;
             Pattern re = Pattern.compile("[0-9]+");
                                  Matcher reMatcher = re.matcher(text);
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
                                   return validatedText;
        }
        
        private String multipleNum(String text){
            String validatedText=null;
           Pattern re = Pattern.compile("[0-9]+");
                                 Matcher  reMatcher = re.matcher(text);
                                   int count=0;
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
                                   return validatedText;
        }
        
        private String lessThan(String text, Integer value){
            String validatedText=null;
             Pattern re = Pattern.compile("(\\+|\\-)?[0-9]+");
                                   Matcher reMatcher = re.matcher(text);
                                   
                                   if(reMatcher.find()){
                                       String val=reMatcher.group();
                                       if(Integer.parseInt(val)<value){
                                           return null;
                                       }
                                       else{
                                          validatedText=val;
                                       }
                                   } 
                                   
                                  if(validatedText==null){
                                        validatedText=text;
                                   }
                                  return validatedText;
        } 
        
        
        private String moreThan(String text,Integer value){
            String validatedText=null;
             Pattern re = Pattern.compile("[0-9]+");
                                 Matcher  reMatcher = re.matcher(text);
                                   
                                   if(reMatcher.find()){
                                       String val=reMatcher.group();
                                       if(Integer.parseInt(val)>value){
                                           return null;
                                       }
                                       else{
                                           validatedText=val;
                                       }
                                   } if(validatedText==null){
                                        validatedText=text;
                                   }
                                   return validatedText;
        }
        
        private String emailValidate(String text){
            String validatedText=null;
            Pattern re = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
                        Matcher reMatcher = re.matcher(text.trim());
                         if(reMatcher.find()){
                                       validatedText= reMatcher.group();
                                   } 
                         else{
                             return null;
                         }
                         
                         return validatedText;
        }
        
        private String phoneValidate(String text){
            String validatedText=null;
           Pattern re = Pattern.compile("[0-9][0-9][0-9][0-9][0-9]([0-9]+)");
                                  Matcher reMatcher = re.matcher(text.trim());
                                   if(reMatcher.find()){
                                                validatedText= reMatcher.group();
                                       
                                   } 
                                   else{
                                       return null;
                                   }
                                    if(validatedText==null){
                                       validatedText=text;
                                   }
                                    
                                    return validatedText;
        }
        
    @Override
    void saveResults(User user) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private void sendOtp(String validatedText,String otp) {
        
        
          String responseText="";
            try {   
                String url="http://api.mVaayoo.com/mvaayooapi/MessageCompose";
                  CloseableHttpClient httpclient = HttpClients.createDefault();
                                    HttpGet httpGet = new HttpGet(new URIBuilder(url).
                                            addParameter("user", "akshay@jubination.com:jubination@1").
                                            addParameter("senderID", "JUBINA").
                                            addParameter("receipientno", "91"+validatedText).
                                            addParameter("dcs", "0").
                                            addParameter("msgtxt", "Your OTP is "+otp).
                                            addParameter("state", "4").
                                            build());
                                    
                                    
                                    HttpEntity entity = httpclient.execute(httpGet).getEntity();

                                    responseText = EntityUtils.toString(entity, "UTF-8");
                
            } 
            catch (Exception ex) {
                
            }
                  
    }

 
           

}
