package app.com.eliroy.android.wiseinfluence.Model;

import java.util.Date;
import java.util.UUID;

import app.com.eliroy.android.wiseinfluence.Model.Politician;

/**
 * describes the posts in the posts list activity each represents a story about committee activity
 */
public class Post{
    private String id = "";
    private String topic = "";
    private String content = "";
    private Date date;

    public Post(){
        id = UUID.randomUUID().toString();
        topic += "נושא לדוגמה מהRSS למשל ועדת הכספים הצביעה על...";
        date = new Date();
        //String date = new SimpleDateFormat("dd-MM-yyyy").format(new Date()); - get this line to whereever we need
        // to present date as String.
        content = topic + "";
    }
    //TODO getters and setters



}
