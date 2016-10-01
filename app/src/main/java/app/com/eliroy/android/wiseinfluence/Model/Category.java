package app.com.eliroy.android.wiseinfluence.Model;


/**
 * describing the different committees, reflects user preferences
 */
public class Category {
    private String englishName;
    private String hebrewName;

    public Category(String hebrewName, String englishName) {

        this.hebrewName = hebrewName;
        this.englishName = englishName;
    }

    public String getEnglishName() {
        return englishName;
    }
    /*
    * use later version
    * */
    public String getHebrewName() {
        return hebrewName;
    }
}

