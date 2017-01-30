/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jubination.io.chatbot.service;

import com.jubination.io.chatbot.model.dao.UserDAO;
import com.jubination.io.chatbot.model.pojo.User;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author MumbaiZone
 */
@Service
@Transactional
public class THPResultService extends ResultService{

    @Autowired
        UserDAO  userRepository;  
    private final String overweightText="You are over-weight,";
    private final String exerciseText="You don't exercise regularly,";
    private final String smokeText="Your habits of smoking, ";
    private final String drinkText="Alcohol consumption, ";
    private final String ageText="Your age is in the high risk-group, ";
    private final String thyroidText="6) Thyroid : Also your gender pre-disposes you to Thyroid and other hormonal conditions";
    
    @Override
    void saveResults(User user) {
        
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
        if(user.getTags().get("habits").contains("drink")||user.getTags().get("habits").contains("alcohol")){
            drink=true;
        }
       if(user.getTags().get("habits").contains("smoke")){
            smoke=true;
        }
        if(user.getTags().get("habits").contains("both")){
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
            vitamin+=overweightText;
            vitaminCount++;
            liver+=overweightText;
            liverCount++;
            diabetes+=overweightText;
            diabetesCount++;
            heart+=overweightText;
            heartCount++;
        }
        
        if(aged){
            diabetes+=ageText;
            diabetesCount++;
            heart+=ageText;
            heartCount++;
        }
        
        if(smoke){
            diabetes+=smokeText;
            diabetesCount++;
            heart+=smokeText;
            heartCount++;
            liver+=smokeText;
            liverCount++;
            kidney+=smokeText;
            kidneyCount++;
            
        }
         if(drink){
            diabetes+=drinkText;
            diabetesCount++;
            heart+=drinkText;
            heartCount++;
            liver+=drinkText;
            liverCount++;
            kidney+=drinkText;
            kidneyCount++;
        }
         
         if(!exercise){
            diabetes+=exerciseText;
            diabetesCount++;
            heart+=exerciseText;
            heartCount++;
        }
         
         //Health Goals
          if(waistFat||overweight){
                healthGoals+="&#9679; Manage your <b>Weight</b> (With this one step, we can reverse a lot of your health-risks) <br/><br/>";
            }
          if(stressed){
               healthGoals+="&#9679; Manage your <b>Stress-Levels</b> (Stress affects our mind, body and productivity, I will help you manage stress better)  <br/><br/>";
          }
          if(!checkup){
              healthGoals+="&#9679; Opt for a <b>Comprehensive Health-checkup</b> (I will also tell you the exact package you require) <br/><br/>";
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
            healthGoals+="&#9679; Get into a regime of <b>Eating healthy</b> (I will get my experts involved and suggest what's right)  <br/><br/>";

            if(smoke){
                 healthGoals+="&#9679; Quit <b>Smoking</b> (You would have read the warnings I am sure, still :(  )  <br/><br/>";
            }
           if(drink){
                    healthGoals+="&#9679; Moderate <b>Alcohol intake</b> <br/><br/>";
           }
           
           if(!gender){
               user.getResult().put("thyroid-text", thyroidText);
           }
           if(smoke&&drink){
               user.getResult().put("number-checkup", "<b>1 out of 6</b>");
           user.getResult().put("number-phy", "<b>2 out of 6</b>");
            user.getResult().put("number-diet", "<b>3 out of 6</b>");
            user.getResult().put("number-smoke", "<b>4 out of 6</b>");
            user.getResult().put("number-drink", "<b>5 out of 6</b>");
            user.getResult().put("number-med", "<b>6 out of 6</b>");
           }
           else if(smoke){
               user.getResult().put("number-checkup", "<b>1 out of 5</b>");
           user.getResult().put("number-phy", "<b>2 out of 5</b>");
            user.getResult().put("number-diet", "<b>3 out of 5</b>");
            user.getResult().put("number-smoke", "<b>4 out of 5</b>");
            user.getResult().put("number-med", "<b>5 out of 5</b>");
            }
           else if(drink){
            user.getResult().put("number-checkup", "<b>1 out of 5</b>");
           user.getResult().put("number-phy", "<b>2 out of 5</b>");
            user.getResult().put("number-diet", "<b>3 out of 5</b>");
            user.getResult().put("number-drink", "<b>4 out of 5</b>");
            user.getResult().put("number-med", "<b>5 out of 5</b>");
           }
           else{
                user.getResult().put("number-checkup", "<b>1 out of 4</b>");
           user.getResult().put("number-phy", "<b>2 out of 4</b>");
            user.getResult().put("number-diet", "<b>3 out of 4</b>");
            user.getResult().put("number-med", "<b>4 out of 4</b>");
           }
           
            user.getResult().put("diabetes-text", diabetes);
            user.getResult().put("heart-text", heart);
            user.getResult().put("kidney-text", kidney);
            user.getResult().put("liver-text", liver);
            user.getResult().put("vitamin-text", vitamin);
            user.getResult().put("name-of-tests", "<br/>&#9679; <b>Diabetes</b><br/><br/>&#9679; <b>Lipid Profile</b><br/><br/>&#9679; <b>Liver Profile</b><br/><br/>&#9679; <b>Kidney Profile</b><br/><br/>");
            
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
          
    //Validating Text based /... return if the vslidation goes wrong  any of the cases and return the value evertime the case is true
    @Override
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
                        re = Pattern.compile("(\\+|\\-)?[0-9]+");
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
                         re = Pattern.compile("(\\+|\\-)?[0-9]+");
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
                        if(text.startsWith(".")){
                            return null;
                        }
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
                        re = Pattern.compile("(\\+|\\-)?[0-9]+");
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
                        re = Pattern.compile("(\\+|\\-)?[0-9]+");
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
                        reMatcher = re.matcher(text.trim());
                         if(reMatcher.find()){
                                       validatedText= reMatcher.group();
                                   } 
                         else{
                             return null;
                         }
                        break;
                    case "phone-invalid":
                         re = Pattern.compile("[0-9][0-9][0-9][0-9][0-9]([0-9]+)");
                                   reMatcher = re.matcher(text.trim());
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

 
           

}
