package app.com.eliroy.android.wiseinfluence.Model;


/**
 * decribes the categories to choose such as yoker hamihia etc.
 * basic implementation: each committee name would symbolize a category name
 */
public class Category {
    private String englishName;// knesset, finance, economics, foreign affairs, internal affairs,
    // huka, immigration, Education, labor, state control, women, science, drugs, children,
    // govInfo, urban, petitions, ethics, broadcast2015 govService, socialEquality.
    private String hebrewName;

    public Category(String hebrewName, String englishName) {

        this.hebrewName = hebrewName;
        this.englishName = englishName;
    }

    public String getEnglishName() {
        return englishName;
    }

    public String getHebrewName() {
        return hebrewName;
    }
}

