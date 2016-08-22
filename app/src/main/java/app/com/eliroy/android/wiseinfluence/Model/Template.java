package app.com.eliroy.android.wiseinfluence.Model;

import java.util.UUID;

/**
 * Created by elay1_000 on 14/08/2016.
 */
public class Template {
    private String id;
    private String topic = "";
    private String content = "";

    public Template(){
        id = UUID.randomUUID().toString();
    }

}
