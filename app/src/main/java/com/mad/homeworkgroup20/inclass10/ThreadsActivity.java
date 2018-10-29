package com.mad.homeworkgroup20.inclass10;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.Headers;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
/**
 * group members : ankit kelkar, shubhra sharma
 * inclass 12
 * Created by akelkar3 on 4/21/2018.
 */
public class ThreadsActivity extends AppCompatActivity {
    private static final String TAG = "test";

    public ArrayList<Thread>threads=new ArrayList<Thread>();
    public  apiCalls caller;
    public String token;
    DatabaseReference threadRef;
    ListView listView;
    ThreadsAdapter threadsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_threads);
        caller= new apiCalls();
       apiCalls.activity=this ;
         token =getIntent().getExtras().getString(MainActivity.KEY_TOKEN);
        String userName = getIntent().getExtras().getString(MainActivity.KEY_NAME);
         final String userID =getIntent().getExtras().get(MainActivity.KEY_USERID).toString();
        TextView displayName= (TextView) findViewById(R.id.displayName);
        displayName.setText(userName);
        ImageView logout = findViewById(R.id.logoutImage);
        Log.d(TAG, "onCreate: threads token"+token);
         listView = (ListView)findViewById(R.id.messagesListView);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapter, View view, int position, long arg) {
                long viewId = view.getId();
                if (viewId == R.id.deleteButton) {
                    apiCalls caller = new apiCalls();
                    caller.deleteThreads(threads.get(position).id);
                }else {
                    Intent appInfo = new Intent(ThreadsActivity.this, ChatroomActivity.class);
                    appInfo.putExtra(MainActivity.KEY_NAME, threads.get(position).title);
                    appInfo.putExtra(MainActivity.KEY_USERID, threads.get(position).id);

                    startActivity(appInfo);
                }
            }
        });
        threadRef = caller.mDatabase.getReference("threads");
         listView = (ListView)findViewById(R.id.messagesListView);
        threadsAdapter   = new ThreadsAdapter(ThreadsActivity.this, R.layout.item_view, threads, caller.getuserId());

        listView.setAdapter(threadsAdapter);
      getThreads(token,userID);
      /*  Thread th = new Thread();
        th.title="titletest";
        threads = new ArrayList<>();
        threads.add(th);*/
     /*   ListView listView = (ListView)findViewById(R.id.threadsListView);
        ThreadsAdapter  threadsAdapter= new ThreadsAdapter(ThreadsActivity.this,R.layout.item_view,threads);
        listView.setAdapter(threadsAdapter);*/
        final ImageButton add = findViewById(R.id.addThread);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
             TextView   newTitle = findViewById(R.id.threadName);
                addThread(newTitle.getText().toString(),token,userID);
            }
        });
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences preferences =getSharedPreferences("mypref",MainActivity.MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                editor.clear();
                editor.apply();
               // finish();
           // caller.deleteToken();
                Intent intent = new Intent(ThreadsActivity.this, MainActivity.class);
             startActivity(intent);
                finish();
            }
        });
    }
    public void getThreads(String token,String currentUserId1)
    {
        threadRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                threads.clear();
                for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
            Thread post = postSnapshot.getValue(Thread.class);
                    threads.add(post);
                }
               //     Log.d(TAG, "getThread: "+ newtread);
                Log.d(TAG, "Value is: " + threads.size());
                //threads.add(newtread);

    threadsAdapter.notifyDataSetChanged();

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
    public void addThread( String title,String token,String userID)
    { final String useToken =token;
final String UserId=userID;

        Thread newThread = new Thread();
        newThread.title = title;
        newThread.user_id=caller.getuserId();
      //  newThread.messages = new ArrayList<UserMessage>();
      //  newThread.created_at= new Date();
       newThread.id= threadRef.push().getKey();
        threadRef.child(newThread.id).setValue(newThread);
        TextView tv =findViewById(R.id.threadName);
        tv.setText("");
         /*   final OkHttpClient client = new OkHttpClient();
            RequestBody formBody = new FormBody.Builder()
                    .add("title", title)

                    .build();
            Request request = new Request.Builder()
                    .url("http://ec2-54-91-96-147.compute-1.amazonaws.com/api/thread/add")
                    .addHeader("Authorization","BEARER "+token)
                    .post(formBody)
                    .build();

            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    Log.d(TAG, "onFailure: ");
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    String str;
                    try (ResponseBody responseBody = response.body()) {
                        if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);

                        Headers responseHeaders = response.headers();
                        for (int i = 0, size = responseHeaders.size(); i < size; i++) {
                            System.out.println(responseHeaders.name(i) + ": " + responseHeaders.value(i));
                        }

                        //System.out.println(responseBody.string());
                        str=responseBody.string();
                    }
                    // str= response.body().string();
                    Log.d(TAG, "onResponse: "+str );
                    Gson gson = new Gson();

                    final ResponseApi result=  (ResponseApi) gson.fromJson(str, ResponseApi.class); // Fails to deserialize foo.value as Bar
                    if (!result.status.equalsIgnoreCase("error")) {

                            getThreads(useToken,UserId);


                       // saveToken(result.token.toString(),result.getUserFullName(),result.getUser_id());
                    }
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (result.status.equalsIgnoreCase("error")) {
                                Toast.makeText(ThreadsActivity.this, result.getMessage(), Toast.LENGTH_SHORT).show();
                            }else {
                            TextView   threadTitle=  findViewById(R.id.threadName);
                            threadTitle.setText("");
                            }
                            //   Toast.makeText(activity, "token created successfully", Toast.LENGTH_SHORT).show();
                            //do something more.

                        }
                    });
                }

            });*/

    }

}
