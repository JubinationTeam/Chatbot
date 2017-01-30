/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jubination.io.chatbot.model.pojo;

import java.util.HashMap;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 *
 * @author MumbaiZone
 */
@Document(collection = "fb_session_indices")
@JsonIgnoreProperties(ignoreUnknown = true)
public class FbSessionIndices {
    @Id
    String fbId;
    String sesId;

    public String getFbId() {
        return fbId;
    }

    public void setFbId(String fbId) {
        this.fbId = fbId;
    }

    public String getSesId() {
        return sesId;
    }

    public void setSesId(String sesId) {
        this.sesId = sesId;
    }

   
    
    
    
}
