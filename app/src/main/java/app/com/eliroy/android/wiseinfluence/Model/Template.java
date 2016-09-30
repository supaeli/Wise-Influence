package app.com.eliroy.android.wiseinfluence.Model;


import java.io.Serializable;

public class Template implements Serializable{
    private String name;
    private String content = "";
    private int likesCount = 0;

    public Template(String name, String content, int likesCount){
        this.name = name;
        this.content = content;
        this.likesCount = likesCount;
    }

    @Override
    public String toString() {
        return name;
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
