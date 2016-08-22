package app.com.eliroy.android.wiseinfluence.Model;

import java.util.UUID;

/**
 * decribes the categories to choose such as yoker hamihia etc.
 * basic implementation: each committee name would symbolize a category name
 */
public class Category {
    private String id;// knesset, finance, economics, foreign affairs, internal affairs,
    // huka, immigration, Education, labor, state control, women, science, drugs, children,
    // govInfo, urban, petitions, ethics, broadcast2015 govService, socialEquality.
    private Post[] posts;
    private Template[] templates;
    public Category(){
        id = UUID.randomUUID().toString();
    }



}
