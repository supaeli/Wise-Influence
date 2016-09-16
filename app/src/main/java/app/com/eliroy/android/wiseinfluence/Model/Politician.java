package app.com.eliroy.android.wiseinfluence.Model;

import java.util.UUID;

/**
 * Created by elay1_000 on 16/08/2016.
 */
public class Politician {
    private String id;
    private String name;
    private String email = "";
    private String facebook = "";
    private String phone = "";

    public Politician(String id, String name, String email, String facebook, String phone){
        this.id = id;
        this.name = name;
        this.email = email;
        this.facebook = facebook;
        this.phone = phone;
    }

}
