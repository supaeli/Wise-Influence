package app.com.eliroy.android.wiseinfluence.Model;


import java.io.Serializable;

public class Template implements Serializable{
    private String id;
    private String content = "";
    private String category = "";



    public Template(String id, String category, String content){
        this.id = id;
        this.content = content;
        this.category = category;
    }

    public String getId() {
        return id;
    }


    public String getContent() {
        return content;
    }
    public String getCategory() {
        return category;
    }
}
