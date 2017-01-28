/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jubination.io.chatbot.backend.service.core;

import org.springframework.stereotype.Component;

/**
 *
 * @author MumbaiZone
 */
@Component
public class RandomNumberGenerator {
    
    public int generate(int maxInt){
        int value= (int) System.currentTimeMillis()%maxInt;
        return (value>0)?value:-value;
    }
    
}
