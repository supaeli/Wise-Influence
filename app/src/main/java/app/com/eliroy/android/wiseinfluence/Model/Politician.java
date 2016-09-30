package app.com.eliroy.android.wiseinfluence.Model;

import java.io.Serializable;
import java.util.UUID;

/**
 * Created by elay1_000 on 16/08/2016.
 */
public class Politician implements Serializable{
    private String name;
    private String email = "";
    private String facebook = "";
    private String phone = "";

    public Politician(String name, String email, String facebook, String phone){
        this.name = name;
        this.email = email;
        this.facebook = facebook;
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getFacebook() {
        return facebook;
    }

    public String getPhone() {
        return phone;
    }
}
