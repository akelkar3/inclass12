package com.mad.homeworkgroup20.inclass10;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
/**
 * group members : ankit kelkar, shubhra sharma
 * inclass 12
 * Created by akelkar3 on 4/21/2018.
 */
public class SignUpActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        setTitle("Sign Up");
final TextView fname = findViewById(R.id.editTextFName);
        final TextView lname = findViewById(R.id.editTextLName);
        final TextView email = findViewById(R.id.etEmail1);
        final TextView pass = findViewById(R.id.editTextPass2);

        //SignUp functionality
        findViewById(R.id.buttonSignup2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String first_name = fname.getText().toString();
                String lastname = lname.getText().toString();
                String emailadd = email.getText().toString();
                String password = pass.getText().toString();
apiCalls.activity=SignUpActivity.this;
apiCalls caller = new apiCalls();
caller.SignUpApi(emailadd,password,first_name,lastname);
                    //    =findViewById(R.id.editTextFName).getText();
            }
        });




    }
}
