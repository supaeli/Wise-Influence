package app.com.eliroy.android.wiseinfluence.Model;

import java.util.UUID;

/**
 * Created by elay1_000 on 16/08/2016.
 */
public class Politician {
    private String id;
    private String mail = "";
    private String facebook = "";
    private String phone = "";

    public Politician(){
        id = UUID.randomUUID().toString();
    }
}
