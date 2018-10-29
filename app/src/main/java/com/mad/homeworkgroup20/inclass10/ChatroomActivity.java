package com.mad.homeworkgroup20.inclass10;
/**
 * group members : ankit kelkar, shubhra sharma
 * inclass 12
 * Created by akelkar3 on 4/21/2018.
 */
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ChatroomActivity extends AppCompatActivity {
    private static final String TAG = "test";
    final apiCalls caller = new apiCalls();
    MessageAdapter  messageAdapter;
    ArrayList<UserMessage> messages = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chatroom);
        apiCalls.activity=this;
      //  token =getIntent().getExtras().getString(MainActivity.KEY_TOKEN);
        String threadName = getIntent().getExtras().getString(MainActivity.KEY_NAME);
         final String threadId =getIntent().getExtras().get(MainActivity.KEY_USERID).toString();
        TextView displayName= (TextView) findViewById(R.id.displayName);
        displayName.setText(threadName);
        ListView listView = (ListView)findViewById(R.id.messagesListView);

        // Log.d(TAG, "run: in main ui thread "+ call.getuserId());
          messageAdapter= new MessageAdapter(this,R.layout.item_view,messages,caller.getuserId());
        listView.setAdapter(messageAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapter, View view, int position, long arg) {
                long viewId = view.getId();
                if (viewId == R.id.deleteButton) {
                    //   apiCalls caller = new apiCalls();
                    caller.deleteMessages(messages.get(position).id,threadId);
                }
            }
        });
        ImageButton home = findViewById(R.id.homeImage);
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        getMessages("",threadId);
         ImageButton add = findViewById(R.id.addMessage);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TextView   newMessage = findViewById(R.id.messageName);
              caller.addMessage(newMessage.getText().toString(),threadId);


            }
        });

      //  Log.d(TAG, "onCreate: threads token"+token);

    }
    public void getMessages(String token, String threadID)
    { DatabaseReference threadRef= caller.mDatabase.getReference("threads/"+threadID+"/messages");
        threadRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                messages.clear();
                for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
                    UserMessage post = postSnapshot.getValue(UserMessage.class);
                    messages.add(post);
                }
                //     Log.d(TAG, "getThread: "+ newtread);
                Log.d(TAG, "Value is: " + messages.size());
                //threads.add(newtread);

                messageAdapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });
/*

final String currentUserId= currentUserId1;
        final OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("http://ec2-54-91-96-147.compute-1.amazonaws.com/api/thread")
                .addHeader("Authorization","BEARER "+token)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d(TAG, "onFailure getThreads: ");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String str;
                try (ResponseBody responseBody = response.body()) {
                    if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);

                    Headers responseHeaders = response.headers();

                    //System.out.println(responseBody.string());
                    str=responseBody.string();
                }
                // str= response.body().string();
                Log.d(TAG, "onResponse: "+str );
                Gson gson = new Gson();

                final ResponseApi result=  (ResponseApi) gson.fromJson(str, ResponseApi.class); // Fails to deserialize foo.value as Bar
                if (!result.status.equalsIgnoreCase("error")) {
                    Log.d(TAG, "onResponse: getThread"+result.threads.size());

                    //  saveToken(result.token.toString(),result.getUserFullName(),result.getUser_id());
                }

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (result.status.equalsIgnoreCase("error")) {
                            Toast.makeText(ThreadsActivity.this, result.getMessage(), Toast.LENGTH_SHORT).show();
                        }else {
                            Log.d(TAG, "run: here");
                            ListView listView = (ListView)findViewById(R.id.messagesListView);
                            ThreadsAdapter  threadsAdapter= new ThreadsAdapter(ThreadsActivity.this,R.layout.item_view,result.threads,currentUserId);
                            listView.setAdapter(threadsAdapter);
                            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> adapter, View view, int position, long arg) {
                                    long viewId = view.getId();
                                    if (viewId == R.id.deleteButton) {
                                        apiCalls caller = new apiCalls();
                                        caller.deleteThreads(result.threads.get(position).id);
                                    }else {
                                        Intent appInfo = new Intent(ThreadsActivity.this, ChatroomActivity.class);
                                        appInfo.putExtra(MainActivity.KEY_NAME, result.threads.get(position).title);
                                        appInfo.putExtra(MainActivity.KEY_USERID, result.threads.get(position).id);

                                        startActivity(appInfo);
                                    }
                                }
                            });
                          //  threadsAdapter.notifyDataSetChanged();
                        }
                        //   Toast.makeText(activity, "token created successfully", Toast.LENGTH_SHORT).show();
                        //do something more.

                        Log.d(TAG, "run: accessing ui " );
                    }
                });
            }
        });*/

    }
}
