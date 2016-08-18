package app.com.eliroy.android.wiseinfluence.Model;

/**
 *
 *class user describe user of app
 *userName and passwd are set later acccording to expected flow: choice of preferences
 *first and registration as an option later.
 */
public class User {
    private String fName = "";
    private String lName = "";
    private String email = "";
    private String userName = "";
    private String passwd = "";
    private String[] politicalsPrefs;
    private static int currIndex;

    //TODO add validation to methods and ctors

    public User(){
        currIndex = 0;
    }

    public User(String fname, String lname, String mail){
        this.fName += fname;
        this.lName += lname;
        this.email += mail;
        politicalsPrefs = new String[10];// TODO update according to prefs real number
        currIndex = 0;
    }

    public String getFName(){
        //TODO
        return null;
    }

    public void setFName(String name){
        //TODO
    }

    public String getLName(){
        //TODO
        return null;
    }

    public void setLName(String name){
        //TODO
    }

    public String getMail(){
        //TODO
        return null;
    }

    public void setMail(String name){
        //TODO
    }

    public String getUsername(){
        //TODO
        return null;
    }

    public void setUsername(String user){
        //TODO
    }

    public String getPasswd(){
        //TODO
        return null;
    }

    public void setPasswd(String passwd){
        //TODO
    }


    public void addPolitPreference(String preference){
        if(currIndex <  politicalsPrefs[currIndex].length()-1){
            politicalsPrefs[currIndex] = preference;
            currIndex++;
        }
        else{
            System.out.println("preferences array is full");
        }
    }

}
