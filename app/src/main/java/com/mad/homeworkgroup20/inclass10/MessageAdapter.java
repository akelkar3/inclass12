package com.mad.homeworkgroup20.inclass10;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import org.ocpsoft.prettytime.PrettyTime;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
/**
 * group members : ankit kelkar, shubhra sharma
 * inclass 12
 * Created by akelkar3 on 4/21/2018.
 */
public class MessageAdapter extends ArrayAdapter<UserMessage> {
    String currentUserId;
    public MessageAdapter(Context context, int resource, ArrayList<UserMessage> objects, String UserID) {
        super(context, resource, objects);
        Log.d("test", "MessageAdapter: "+ UserID);
        this.currentUserId=UserID;
    }

    @Override
    public View getView(final int position, View convertView, final ViewGroup parent) {
        final UserMessage threadItem = getItem(position);
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
               // apiCalls caller = new apiCalls();
                ((ListView) parent).performItemClick(view, position, 0); // Let the event be handled in onItemClick()
           //     caller.deleteMessage(threadItem.id);

            }
        });
     //   ImageView image = (ImageView)convertView.findViewById(R.id.newsImage);
       title.setText(threadItem.message);
        userName.setText(threadItem.name);

        PrettyTime p = new PrettyTime();
        Date date = null;
        try {
            if ( threadItem.created_at!=null&& threadItem.created_at !="" )
                //Log.d("test", "getView: createdAt "+ threadItem.created_at);
            date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(threadItem.created_at);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        messageTime.setText(p.format( date));
      //set delete visibility
        //set the data from the news object
        delete.setVisibility(View.INVISIBLE);
        Log.d("test", "getView: after adding  "+ currentUserId);
        if (currentUserId.equalsIgnoreCase(threadItem.user_id))
            delete.setVisibility(View.VISIBLE) ;

        return convertView;
    }


}
