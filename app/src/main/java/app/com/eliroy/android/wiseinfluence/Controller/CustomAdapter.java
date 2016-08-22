package app.com.eliroy.android.wiseinfluence.Controller;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import app.com.eliroy.android.wiseinfluence.Model.Post;
import app.com.eliroy.android.wiseinfluence.R;

/**
 * Created by elay1_000 on 22/08/2016.
 */
public class CustomAdapter extends ArrayAdapter<Post> {

    private Activity myContext;
    private Post[] data;

    public CustomAdapter(Context context, int resource, Post[] objects) {
        super(context, resource, objects);
        myContext = (Activity) context;
        data = objects;
    }

    public View getView(int position, View convertView, ViewGroup parent){
        LayoutInflater myInflater = myContext.getLayoutInflater();
        View rowView = myInflater.inflate(R.layout.list_item_template, null);
        ImageView imageView = (ImageView)rowView.findViewById(R.id.elisImage);

        if(data[position].getPicURL() == null){
            imageView.setImageResource(R.drawable.bulb_icon);
        }
        TextView rowTitleView = (TextView) rowView.findViewById(R.id.elisText);
        rowTitleView.setText(data[position].getTopic());
        TextView rowDateView = (TextView) rowView.findViewById(R.id.elisDate);
        rowDateView.setText(data[position].getDate());
        return rowView;
    }

}
