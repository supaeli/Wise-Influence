package app.com.eliroy.android.wiseinfluence.Controller;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import java.util.ArrayList;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import app.com.eliroy.android.wiseinfluence.Model.Category;
import app.com.eliroy.android.wiseinfluence.Model.Politician;
import app.com.eliroy.android.wiseinfluence.Model.Post;
import app.com.eliroy.android.wiseinfluence.Model.Template;
import app.com.eliroy.android.wiseinfluence.R;
import app.com.eliroy.android.wiseinfluence.Support.Network.ApiClient;
import app.com.eliroy.android.wiseinfluence.Support.Network.CallBack;
import app.com.eliroy.android.wiseinfluence.View.AlertDialogFragmentCategories;

/*
* Display a scrollable list of committee relevant info. Each post on list displays
* only highlight info.
* */

public class PostsFeedActivity extends FragmentActivity {

    private ApiClient client = new ApiClient();
    private ArrayList<Politician> politicians;
    public  Category[] categories;
    private ArrayList<Template> templates;
    FragmentManager fragmentManager = getSupportFragmentManager();

    //====================== Lifecycle ================//

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_posts_feed);
        prepareCategories();
        reloadFeedWithCategory(categories[0]);
        loadTemplates();
    }

    //====================== Configuration ================//

    public void reloadFeedWithCategory(Category category){
        final Context context = this;
        CallBack<ArrayList<Post>> handler = new CallBack<ArrayList<Post>>() {
            @Override
            public void handle(final ArrayList<Post> result) {
                CustomAdapter adapter = new CustomAdapter(context, R.layout.list_item_template,result);
                ListView feedList = (ListView) findViewById(R.id.feed_list_view);
                feedList.setAdapter(adapter);
                adapter.notifyDataSetChanged();//update data on adapter

                feedList.setOnItemClickListener(
                        new AdapterView.OnItemClickListener(){
                            @Override
                            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                                Intent intent = new Intent(PostsFeedActivity.this, PostDetailsActivity.class);
                                Bundle extras = new Bundle();
                                extras.putSerializable("POLITICIANS", politicians);
                                extras.putSerializable("POST", result.get(position));
                                extras.putSerializable("TEMPLATES", templates);
                                intent.putExtras(extras);
                                startActivity(intent);
                            }
                        }
                );
            }
        };

        client.reloadPostWithCategory(category, handler);
        loadPoliticians(category);
    }

    private void loadPoliticians(Category category) {
        client.loadPoliticians(category, new CallBack<ArrayList<Politician>>() {
            @Override
            public void handle(final ArrayList<Politician> result) {
                politicians = result;
            }
        });
    }

    private void loadTemplates() {
        client.loadTemplates(new CallBack<ArrayList<Template>>(){
            @Override
            public void handle(ArrayList<Template> result) {
                templates = result;
            }
        });
    }

    private void prepareCategories() {
        String[] hebrewCategoriesString = getResources().getStringArray(R.array.committee_hebrew_names);
        String[] englishCategoriesString = getResources().getStringArray(R.array.committee_english_names);
        categories = new Category[11];
        //==== create categories array ===//
        for (int i = 0; i < hebrewCategoriesString.length; i++){
            categories[i] = new Category(hebrewCategoriesString[i],englishCategoriesString[i]);
        }
    }

    //====================== Actions ================//

    /*
    * onClick method for category button
    * */
    public void showCategoriesListView(View view) {
        AlertDialogFragmentCategories alertDialogFragmentCategories = new AlertDialogFragmentCategories();
        alertDialogFragmentCategories.show(fragmentManager,"Alert Dialog Fragment");
    }

    public void setButtonText(String committeeName) {
        Button button = (Button)findViewById(R.id.btn);
        button.setText(committeeName);
    }
}
