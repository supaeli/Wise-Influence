package app.com.eliroy.android.wiseinfluence.Model;


import java.io.Serializable;

public class Template implements Serializable{
    private String id;
    private String name;
    private String content = "";
    private int likesCount = 0;

    public Template(String id, String name, String content, int likesCount){
        this.id = id;
        this.name = name;
        this.content = content;
        this.likesCount = likesCount;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getContent() {
        return content;
    }

    public int getLikesCount() {
        return likesCount;
    }
}
