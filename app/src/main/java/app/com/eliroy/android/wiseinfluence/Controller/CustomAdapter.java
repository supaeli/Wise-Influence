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
    static class ViewHolder {
        TextView rowTitleView;
        TextView rowDateView;
        ImageView rowThumbView;
    }


    public View getView(int position, View convertView, ViewGroup parent){
        ViewHolder viewHolder;

        if (convertView == null) {//conserves findViewById calls
            LayoutInflater myInflater = myContext.getLayoutInflater();
            convertView = myInflater.inflate(R.layout.list_item_template, null);

            viewHolder = new ViewHolder();
            viewHolder.rowThumbView = (ImageView)convertView.findViewById(R.id.elisImage);
            viewHolder.rowTitleView = (TextView) convertView.findViewById(R.id.elisText);
            viewHolder.rowDateView = (TextView) convertView.findViewById(R.id.elisDate);
            convertView.setTag(viewHolder);
        }
        else{
            viewHolder = (ViewHolder) convertView.getTag();
        }

        if(data[position].getPicURL() == null){
            viewHolder.rowThumbView.setImageResource(R.drawable.bulb_icon);
        }


        viewHolder.rowTitleView.setText(data[position].getTopic());
        viewHolder.rowDateView.setText(data[position].getDate());

        return convertView;
    }

}
