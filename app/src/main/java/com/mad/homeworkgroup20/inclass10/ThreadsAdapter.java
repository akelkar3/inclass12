package com.mad.homeworkgroup20.inclass10;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * creted by : ankit kelkar
 * for ${inclass12}
 *  on 2/26/2018.
 */

public class ThreadsAdapter extends ArrayAdapter<Thread> {
    String currentUserId;
    public ThreadsAdapter(Context context, int resource, ArrayList<Thread> objects,String UserID) {
        super(context, resource, objects);
        this.currentUserId=UserID;
    }

    @Override
    public View getView(final int position, View convertView, final ViewGroup parent) {

        final Thread threadItem = getItem(position);
        if(convertView==null)
        convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_view, parent, false);

        TextView title = (TextView)convertView.findViewById(R.id.threadTitle);
        ImageButton delete = convertView.findViewById(R.id.deleteButton);
        TextView userName =convertView.findViewById(R.id.messageUser);
        TextView messageTime = convertView.findViewById(R.id.messageTime);
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            //    Log.d("test", "onClick: deleteClicked");
                ((ListView) parent).performItemClick(view, position, 0); // Let the event be handled in onItemClick()


            }
        });
     //   ImageView image = (ImageView)convertView.findViewById(R.id.newsImage);
       title.setText(threadItem.title);
        userName.setVisibility(View.INVISIBLE);messageTime.setVisibility(View.INVISIBLE);
      //set delete visibility
        //set the data from the news object
        delete.setVisibility(View.INVISIBLE);
        if (currentUserId.equalsIgnoreCase(threadItem.user_id))
            delete.setVisibility(View.VISIBLE) ;

        return convertView;
    }


}
