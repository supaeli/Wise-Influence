package app.com.eliroy.android.wiseinfluence.Model;

/**
 *
 *class user describe user of app
 *userName and passwd are set later acccording to expected flow: choice of preferences
 *first and registration as an option later.
 */
public class User {
    private long id = 0;
    private String[] politicalsPrefs = {};


    //TODO add validation to methods and ctors

    public User(){
        id++;// find how to implement personal incremental id value

    }



    public void addPolitPreference(String preference){
        if(true){

        }
        else{
            System.out.println("preferences array is full");// revise that later
        }
    }

}
