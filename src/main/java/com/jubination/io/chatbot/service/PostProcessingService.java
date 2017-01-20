/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jubination.io.chatbot.service;


import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author MumbaiZone
 */
@Service
@Transactional
public class PostProcessingService {
    //Validating Text based
        boolean validateText(String type, String text) {
            boolean validationError=false;
                switch(type){
                    
                    case "country-number":
                        break;
                        
                    case "name-number":
                        break;
                        
                    case "age-charactersOnly":
                        break;
                    case "age-tooYoung":
                        break;
                    case "age-tooOld":
                        break;
                    case "age-multipleNumbers":
                        break;
                        
                    case "height-huge":
                        break;
                    case "height-charactersOnly":
                        break;
                    case "height-less":
                        break;
                    case "height-multipleNumbers":
                        break;
                    
                    case "weight-huge":
                        break;
                    case "weight-charactersOnly":
                        break;
                    case "weight-less":
                        break;
                    case "weight-multipleNumbers":
                        break;
                    
                   case "waistSize-huge":
                        break;
                    case "waistSize-charactersOnly":
                        break;
                    case "waistSize-less":
                        break;
                    case "waistSize-multipleNumbers":
                        break;
                        
                    case "email-invalid":
                        break;
                    case "phone-invalid":
                        break;
                    
                }
            
            return validationError;
        }
}
