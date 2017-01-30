/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jubination.io.chatbot.service;

import com.jubination.io.chatbot.model.pojo.User;

/**
 *
 * @author MumbaiZone
 */
public abstract class ResultService {
    
   abstract void saveResults(User user);
   abstract String validatedText(String type, String text);
    
}
