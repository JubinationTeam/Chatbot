/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jubination.io.chatbot.backend.service.core;

import com.jubination.io.chatbot.model.pojo.User;
import java.io.IOException;
import java.io.StringReader;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.apache.http.Consts;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Component;
import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;


/**
 *
 * @author MumbaiZone
 */
@Component
public class LMSUpdater {
 
    public boolean createLead(User user) throws IOException{
        
                            String responseText=null;
                            Document doc=null;
                            CloseableHttpResponse response=null;
                            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
                            DocumentBuilder builder;
                            InputSource is;
                            try { 
                                    //requesting exotel to initiate call
                                    CloseableHttpClient httpclient = HttpClients.createDefault();
                                    HttpPost httpPost = new HttpPost("http://188.166.253.79/save_enquiry");
                                    List<NameValuePair> formparams = new ArrayList<>();
                                    
                                    
                                    formparams.add(new BasicNameValuePair("full_name",user.getName()));
                                    formparams.add(new BasicNameValuePair("email",user.getEmail()));
                                    formparams.add(new BasicNameValuePair("phone",user.getEmail()));
                                    formparams.add(new BasicNameValuePair("contact_no",user.getPhone()));
                                    formparams.add(new BasicNameValuePair("city",user.getCountry()));
                                    formparams.add(new BasicNameValuePair("ip","na"));
                                    if(user.getFbId()!=null){
                                        formparams.add(new BasicNameValuePair("campaign_id","162"));
                                            formparams.add(new BasicNameValuePair("source","fb-chatbot"));
                                    }
                                    else{
                                        formparams.add(new BasicNameValuePair("campaign_id","161"));
                                        formparams.add(new BasicNameValuePair("source","web-chatbot"));
                                    }
                                    formparams.add(new BasicNameValuePair("step_2","no"));
                                    formparams.add(new BasicNameValuePair("step_2_created_at",new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date())));
                                    LocalDateTime backdate = LocalDateTime.of(2013, Month.JANUARY, 1, 0, 0);
                                    DateTimeFormatter formatter =DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                                    formparams.add(new BasicNameValuePair("step_2_inform_at",backdate.format(formatter)));
                                    formparams.add(new BasicNameValuePair("chat_id",user.getSesId()));
                                    for(Entry<String, Boolean> trigger:user.getTriggers().entrySet()){
                                            formparams.add(new BasicNameValuePair("chat_"+trigger.getKey(),trigger.getValue().toString()));
                                    }
                                    
                                    UrlEncodedFormEntity uEntity = new UrlEncodedFormEntity(formparams, Consts.UTF_8);
                                    httpPost.setEntity(uEntity);
                                    response = httpclient.execute(httpPost);
                                    HttpEntity entity = response.getEntity();

                                    responseText = EntityUtils.toString(entity, "UTF-8");
                            } 
                            catch(IOException | ParseException | DOMException e){
                                    e.printStackTrace();
                            }
                            finally {
                                    if(response!=null){
                                            response.close();
                                    }
                           }
                            System.out.println(responseText);
        return responseText!=null;
    }
    
    
    
}
