package app.com.eliroy.android.wiseinfluence.Model;

import java.util.UUID;

/**
 *
 *class user describe user of app
 *for the moment we would not hold any user personal details information
 *
 */
public class User {
    private String id = "";
    private String[] politicalsPrefs = {};


    //TODO add validation to methods and ctors

    public User(){
        id = UUID.randomUUID().toString();

    }



    public void addPolitPreference(String preference){
        if(true){

        }
        else{
            System.out.println("preferences array is full");// revise that later
        }
    }

}
