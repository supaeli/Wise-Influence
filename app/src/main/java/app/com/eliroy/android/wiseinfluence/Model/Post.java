package app.com.eliroy.android.wiseinfluence.Model;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import app.com.eliroy.android.wiseinfluence.Model.Politician;

/**
 * describes the posts in the posts list activity each represents a story about committee activity
 */
public class Post implements Serializable{
    private String topic = "";
    private String content = "";
    private String date;
    //remove later
    private String imgUrl;
    private String parentCategory;

    public Post(){
       /*
        topic += "נושא לדוגמה מהRSS למשל ועדת הכספים הצביעה על...";
        date = new SimpleDateFormat("dd-MM-yyyy").format(new Date());
        //String date = new SimpleDateFormat("dd-MM-yyyy").format(new Date()); - get this line to whereever we need
        // to present date as String.
        content = topic + "";*/
        imgUrl = null;
    }
    public Post(String topic, String date, String content, String parentCategory){
        this.topic = topic;
        this.content = content;
        this.date = date;
        this.parentCategory = parentCategory;
        imgUrl = null;
    }

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

        this.date = date;
    }

    //delete later also
    public String getPicURL(){

        return imgUrl;
    }

    public String getContent(){
        return content;
    }
    public void setContent(String content){
        this.content = content;
    }

}
