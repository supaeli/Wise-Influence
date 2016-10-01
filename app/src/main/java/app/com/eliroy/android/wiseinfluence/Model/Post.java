package app.com.eliroy.android.wiseinfluence.Model;

import java.io.Serializable;

/**
 * describes the posts in the PostFeedActivity. Each post represents a story about chosen committee activity
 */
public class Post implements Serializable{
    private String topic = "";
    private String content = "";
    private String date;
    private String imgUrl;

    public Post(){
    }

    public Post(String topic, String date, String content){
        this.topic = topic;
        this.content = content;
        this.date = date;
        imgUrl = null;
    }

    public String getTopic(){

        return topic;
    }

    public String getDate(){
        return date;
    }

    public void setDate(String date){

        this.date = date;
    }

    /*
    * use in later version
    * */
    public String getPicURL(){

        return imgUrl;
    }

    public String getContent(){
        return content;
    }

}
