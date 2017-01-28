/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jubination.io.chatbot.service;

import com.jubination.io.chatbot.model.dao.UserDAO;
import com.jubination.io.chatbot.model.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author MumbaiZone
 */
@Service
@Transactional
public class THPResultService {

    @Autowired
        UserDAO  userRepository;  
    
    
    void saveResultsForTHP(User user) {
        
        Boolean gender=false;
        Boolean smoke =false;
        Boolean drink=false;
        Boolean exercise=false;
        Boolean heartFlag=false;
        Boolean diabetesFlag=false;
        Boolean checkup=false;
        Boolean overweight=false;
        Boolean waistFat=false;
        Boolean stressed=false;
        Boolean aged=false;
        
        int diabetesCount=0;
        int heartCount=0;
        int liverCount=0;
        int kidneyCount=0;
        int vitaminCount=0;
        
        String diabetes="";
        String heart="";
        String kidney="";
        String liver="";
        String vitamin="";
        String healthGoals="";
        
        
        //BMI
        String height=user.getTags().get("height");
        String weight=user.getTags().get("weight");
        Double heightMeters=0d;
        if(height.split(".").length>=2){
           heightMeters= Double.parseDouble(height.split(".")[0])*0.3048+Double.parseDouble(height.split(".")[1])*0.0254;
        }
        else{
             heightMeters= Double.parseDouble(height)*0.3048;
        }
        Double bmi = Double.parseDouble(weight)/(Math.pow(heightMeters,2));
        
        //AGED
        Double age=Double.parseDouble(user.getTags().get("age"));
        aged=age>=40;
        
        //GENDER
        if(user.getTags().get("gender").trim().equalsIgnoreCase("male")){
            gender=true;
        }
        
        //WAIST
        Double waistSize=Double.parseDouble(user.getTags().get("waistSize"));
        waistFat=(gender&&waistSize>37)||(!gender&&waistSize>31.5);
        
        //HABITS
        if(user.getTags().get("habits").contains("drink")){
            drink=true;
        }
       else  if(user.getTags().get("habits").contains("smoke")){
            smoke=true;
        }
        else if(user.getTags().get("habits").contains("both")){
            smoke=true;
            drink=true;
        }
        
        //EXERCISE
        if(user.getTags().get("exercise").trim().equalsIgnoreCase("yes")){
            exercise=true;
        }
        
        //ILLNESS
        if(user.getTags().get("illness").contains("Heart")){
            heartFlag=true;
        }
       if(user.getTags().get("illness").contains("Diabetes")){
            diabetesFlag=true;
        }
       if(user.getTags().get("illness").contains("both")){
            heartFlag=true;
            diabetesFlag=true;
        }
        
        //STRESS
        Integer stress=0;
        if(user.getTags().get("stress").trim().equalsIgnoreCase("high")){
            stress+=2;
        }
        else if(user.getTags().get("stress").trim().equalsIgnoreCase("manageable")){
            stress+=1;
        }
        stressed=stress>0;
        
        
        //CHECKUP
        if(user.getTags().get("checkup").trim().equalsIgnoreCase("yes")){
            checkup=true;
        }
        
        //OVERWEIGHT
        if(bmi>=25){
            overweight=true;
        }
        
       //RISKS
        if(overweight){
            vitamin+="Overweight, ";
            vitaminCount++;
            liver+="Overweight, ";
            liverCount++;
            diabetes+="Overweight, ";
            diabetesCount++;
            heart+="Overweight, ";
            heartCount++;
        }
        
        if(aged){
            diabetes+="Age, ";
            diabetesCount++;
            heart+="Age, ";
            heartCount++;
        }
        
        if(smoke){
            diabetes+="Smoke, ";
            diabetesCount++;
            heart+="Smoke, ";
            heartCount++;
            liver+="Smoke, ";
            liverCount++;
            kidney+="Smoke, ";
            kidneyCount++;
            
        }
         if(drink){
            diabetes+="Drink, ";
            diabetesCount++;
            heart+="Drink, ";
            heartCount++;
            liver+="Drink, ";
            liverCount++;
            kidney+="Drink, ";
            kidneyCount++;
        }
         
         if(!exercise){
            diabetes+="Lack of exercise, ";
            diabetesCount++;
            heart+="Lack of exercise, ";
            heartCount++;
        }
         
         //Health Goals
          if(waistFat||overweight){
                healthGoals+="&#9679; Manage your <b>Weight</b><br/><br/>";
            }
          if(stressed){
               healthGoals+="&#9679; Manage your <b>Stress-Levels</b><br/><br/>";
          }
          if(!checkup){
              healthGoals+="&#9679; Opt for a <b>Comprehensive Health-checkup</b> with the right tests<br/><br/>";
          }
           if(heartFlag||diabetesFlag){
               String illness="";
               if(heartFlag){
                   illness+="Heart";
               }
               if(heartFlag&&diabetesFlag){
                   illness+=" and ";
               }
               if(diabetesFlag){
                   illness+="Diabetes";
               }
              healthGoals+="&#9679; Get into a regular regime of <b>Managing your Health condition</b> ("+illness+") <br/><br/>";
          }
           if(!exercise){
               healthGoals+="&#9679; Start getting <b>Physically active</b>  <br/><br/>";
           }
            healthGoals+="&#9679; Get into a regime of <b>Eating healthy</b> <br/><br/>";

            if(smoke){
                 healthGoals+="&#9679; Quit <b>Smoking</b> <br/><br/>";
            }
           if(drink){
                    healthGoals+="&#9679; Moderate <b>Alcohol intake</b> <br/><br/>";
           }
           
            user.getResult().put("diabetes-text", diabetes);
            user.getResult().put("heart-text", heart);
            user.getResult().put("kidney-text", kidney);
            user.getResult().put("liver-text", liver);
            user.getResult().put("vitamin-text", vitamin);
            
            user.getResult().put("diabetes-count", String.valueOf(diabetesCount));
            user.getResult().put("heart-count", String.valueOf(heartCount));
            user.getResult().put("kidney-count", String.valueOf(kidneyCount));
            user.getResult().put("liver-count", String.valueOf(liverCount));
            user.getResult().put("vitamin-count", String.valueOf(vitaminCount));
            
            user.getResult().put("health-goals", healthGoals);
            
            userRepository.updateObject(user.getSesId(), user.getResult(), "result");

            user.getTriggers().put("gender", gender);
            user.getTriggers().put("smoke", smoke);
            user.getTriggers().put("drink", drink);
            user.getTriggers().put("exercise", exercise);
            user.getTriggers().put("heart", heartFlag);
            user.getTriggers().put("diabetes", diabetesFlag);
            user.getTriggers().put("checkup", checkup);
            user.getTriggers().put("overweight", overweight);
            user.getTriggers().put("waistSize", waistFat);
            user.getTriggers().put("stressed", stressed);
            user.getTriggers().put("aged", aged);
            
            userRepository.updateObject(user.getSesId(), user.getTriggers(), "triggers");
            
            
            
            user.getTags().put("smoke", String.valueOf(smoke));
            user.getTags().put("drink", String.valueOf(drink));
            user.getTags().put("heart", String.valueOf(heartFlag));
            user.getTags().put("diabetes", String.valueOf(diabetesFlag));
            
             userRepository.updateObject(user.getSesId(), user.getTags(), "tags");
            
    }
    
}
