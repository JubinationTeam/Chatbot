/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jubination.io.chatbot.backend.service.core;


import com.jubination.io.chatbot.model.pojo.DashBot;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.stereotype.Component;

/**
 *
 * @author MumbaiZone
 */
@Component
public class DashBotUpdater {
       public String sendAutomatedUpdate(DashBot dashbot, String type){
            String responseText="";
            try {   
                String url="https://tracker.dashbot.io/track?platform=generic&v=0.8.2-rest&type="+type+"&apiKey=bJt7U0oEG79HSUm4nJWUQTPm1nhjKu3gieZ83M0O";
                ObjectMapper mapper = new ObjectMapper();
                //Object to JSON in String
                String jsonString= mapper.writeValueAsString(dashbot);
                HttpClient httpClient = HttpClientBuilder.create().build();
                // System.out.println(jsonString+"STRING JSON TO DASH BOT");
                StringEntity requestEntity = new StringEntity(
                jsonString,
                ContentType.APPLICATION_JSON);
                HttpPost postMethod = new HttpPost(url);
                postMethod.setEntity(requestEntity);
                HttpResponse response = httpClient.execute(postMethod);
                HttpEntity entity = response.getEntity();
                responseText = EntityUtils.toString(entity, "UTF-8");
                
            } 
            catch (Exception ex) {
                           Logger.getLogger(DashBotUpdater.class.getName()).log(Level.SEVERE, null, ex);
            }
            // System.out.println(responseText);
            return responseText;
    }
}
