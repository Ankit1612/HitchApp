package com.hitch.hitch.model;

/**
 * Created by Ankit Shah on 24-Sep-17.
 */

public class Users {

    private String user_id;
    private String email_id;
    private String username;

    public Users(){

    }

    public Users(String user_id, String email, String username) {
        this.user_id = user_id;
        this.email_id = email;
        this.username = username;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getEmail_id() {
        return email_id;
    }

    public void setEmail_id(String email_id) {
        this.email_id = email_id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String toString() {
        return "Users{" +
                "user_id='" + user_id + '\'' +
                ", email_id='" + email_id + '\'' +
                ", username='" + username + '\'' +
                '}';
    }
}
