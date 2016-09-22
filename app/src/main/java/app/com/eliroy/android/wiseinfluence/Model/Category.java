package app.com.eliroy.android.wiseinfluence.Model;


/**
 * decribes the categories to choose such as yoker hamihia etc.
 * basic implementation: each committee name would symbolize a category name
 */
public class Category {
    private String name;// knesset, finance, economics, foreign affairs, internal affairs,
    // huka, immigration, Education, labor, state control, women, science, drugs, children,
    // govInfo, urban, petitions, ethics, broadcast2015 govService, socialEquality.
    String categoryUrl;

    public Category(String name, String categoryUrl){
        this.name = name;
        this.categoryUrl = categoryUrl;

    }

    public String getName() {
        return name;
    }

    public String getCategoryUrl() {
        return categoryUrl;
    }
}
