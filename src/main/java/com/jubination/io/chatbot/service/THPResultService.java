/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jubination.io.chatbot.service;

import com.jubination.io.chatbot.model.pojo.User;
import java.util.Iterator;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author MumbaiZone
 */
@Service
@Transactional
public class THPResultService {

    
    
    void saveResultsForTHP(User user) {
        Iterator<String> iterator=user.getResult().keySet().iterator();
        int count=0;
        while(iterator.hasNext()){
            switch(iterator.next()){
                case "habits":
                    count++;
                    break;
                case "gender":
                    count++;
                    break;
                case "exercise":
                    count++;
                    break;
                case "checkup":
                    count++;
                    break;
                case "height":
                    count++;
                    break;
                case "illness":
                    count++;
                    break;
                case "stress":
                    count++;
                    break;
                case "weight":
                    count++;
                    break;
                case "waistSize":
                    count++;
                    break;
                case "age":
                    count++;
                    break;
            }
        }
        System.out.println("COUNT : "+count);
    }
    
}
