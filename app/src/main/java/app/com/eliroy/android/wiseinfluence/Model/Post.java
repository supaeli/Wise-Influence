package app.com.eliroy.android.wiseinfluence.Model;

import java.text.SimpleDateFormat;
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
    private String date;
    //remove later
    private String imgUrl;

    public Post(){
       /* id = UUID.randomUUID().toString();
        topic += "נושא לדוגמה מהRSS למשל ועדת הכספים הצביעה על...";
        date = new SimpleDateFormat("dd-MM-yyyy").format(new Date());
        //String date = new SimpleDateFormat("dd-MM-yyyy").format(new Date()); - get this line to whereever we need
        // to present date as String.
        content = topic + "";*/
        imgUrl = null;
    }
    //TODO getters and setters
    public String getTopic(){
        return topic;
    }
    public void setTopic(String topic){
        this.topic = topic;

    }

    public String getDate(){
        return date;
    }
    public void setDate(String date){
        date = date;
    }

    //delete later also
    public String getPicURL(){
        return imgUrl;
    }


}
