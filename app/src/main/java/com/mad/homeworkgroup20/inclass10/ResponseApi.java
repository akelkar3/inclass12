package com.mad.homeworkgroup20.inclass10;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * group members : ankit kelkar, shubhra sharma
 * inclass 12
 * Created by akelkar3 on 4/21/2018.
 */
public class ResponseApi implements Serializable {
    String status;
    String token;
    String user_id;
    String user_email;
    String user_fname;
    String user_lname;
    String user_role;
ArrayList<Thread> threads,messages;
//Thread message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    String message;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getUser_email() {
        return user_email;
    }

    public void setUser_email(String user_email) {
        this.user_email = user_email;
    }

    public String getUser_fname() {
        return user_fname;
    }

    public void setUser_fname(String user_fname) {
        this.user_fname = user_fname;
    }

    public String getUser_lname() {
        return user_lname;
    }
    public String getUserFullName() {
        return user_fname+" "+ user_lname;
    }
    public void setUser_lname(String user_lname) {
        this.user_lname = user_lname;
    }

    public String getUser_role() {
        return user_role;
    }

    public void setUser_role(String user_role) {
        this.user_role = user_role;
    }
}
