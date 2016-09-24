package app.com.eliroy.android.wiseinfluence.Model;


/**
 * decribes the categories to choose such as yoker hamihia etc.
 * basic implementation: each committee name would symbolize a category name
 */
public class Category {
    private String name;// knesset, finance, economics, foreign affairs, internal affairs,
    // huka, immigration, Education, labor, state control, women, science, drugs, children,
    // govInfo, urban, petitions, ethics, broadcast2015 govService, socialEquality.
    String postsURL;
    String politiciansURL;

    public Category(String name, String postsURL, String politiciansURL){
        this.name = name;
        this.postsURL = postsURL;
        this.politiciansURL = politiciansURL;

    }

    public String getName() {
        return name;
    }

    public String getPostsURL() {
        return postsURL;
    }

    public String getPoliticiansURL() {
        return politiciansURL;
    }
}
