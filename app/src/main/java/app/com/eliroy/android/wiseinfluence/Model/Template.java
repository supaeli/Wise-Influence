package app.com.eliroy.android.wiseinfluence.Model;


import java.io.Serializable;

public class Template implements Serializable{
    private String id;
    private String topic = "";
    private String content = "";
    private String category = "";



    public Template(String id, String topic, String content, String category){
        this.id = id;
        this.topic = topic;
        this.content = content;
        this.category = category;
    }

    public String getId() {
        return id;
    }

    public String getTopic() {
        return topic;
    }

    public String getContent() {
        return content;
    }
    public String getCategory() {
        return category;
    }
}
