package app.com.eliroy.android.wiseinfluence.Controller;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.ArrayList;
import app.com.eliroy.android.wiseinfluence.Model.Post;
import app.com.eliroy.android.wiseinfluence.R;

/**
 * Helper class for ListView implementation, defines how each 'cell' would look like
 * and how info is pushed into the list
 */
public class PostsFeedCustomAdapter extends ArrayAdapter<Post> {

    private Activity myContext;
    private ArrayList<Post> data;

    public PostsFeedCustomAdapter(Context context, int resource, ArrayList<Post> objects) {
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

        if(data.get(position).getPicURL() == null){
            viewHolder.rowThumbView.setImageResource(R.drawable.bulb_icon);
        }

        viewHolder.rowTitleView.setText(data.get(position).getTopic());
        viewHolder.rowDateView.setText(data.get(position).getDate());

        return convertView;
    }
}
