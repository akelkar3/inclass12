package com.mad.homeworkgroup20.inclass10;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
/**
 * group members : ankit kelkar, shubhra sharma
 * inclass 12
 * Created by akelkar3 on 4/21/2018.
 */
public class MainActivity extends AppCompatActivity {
    EditText email, password;
    Button login, signup;
    final String TAG="test";
    public static String KEY_TOKEN="tokenKey";
    public static String KEY_NAME="userNameKey";
    public static String KEY_USERID="userIdKey";
    public static String KEY_THREADID="threadIdKey";
    String email1, password1;
public static  apiCalls caller;

   // private final OkHttpClient client = new OkHttpClient();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("Chat Room");
        caller.activity=MainActivity.this;
        caller=new apiCalls();
        String tok = caller.getToken();
        Log.d(TAG, "onCreate: "+tok);
        if (tok!=null)
        {
            Intent intent = new Intent(MainActivity.this, ThreadsActivity.class);
            intent.putExtra(KEY_TOKEN,caller.getToken());
            intent.putExtra(KEY_NAME,caller.getName());
            intent.putExtra(KEY_USERID,caller.getuserId());
            startActivity(intent);
            finish();

        }

            email=findViewById(R.id.etEmail1);
            password=findViewById(R.id.editTextPass);

        //Has the email and password value


        //Functionality of Login button
        findViewById(R.id.buttonLogin).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                email1=email.getText().toString();
                password1= password.getText().toString();
            caller.loginApi(email1,password1);

            }
        });

        //Functionality of SuignUp Button
        findViewById(R.id.buttonSignUp1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity.this, SignUpActivity.class);
                startActivity(intent);
            }
        });


    }
}
