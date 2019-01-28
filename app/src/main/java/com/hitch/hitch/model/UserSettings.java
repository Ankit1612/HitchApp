package com.hitch.hitch.model;

/**
 * Created by Ankit Shah on 25-Sep-17.
 */

public class UserSettings {

    private Users users;
    private UserAccountSettings userAccountSettings;

    public UserSettings(){

    }

    public UserSettings(Users users, UserAccountSettings userAccountSettings) {
        this.users = users;
        this.userAccountSettings = userAccountSettings;
    }

    public Users getUsers() {
        return users;
    }

    public void setUsers(Users users) {
        this.users = users;
    }

    public UserAccountSettings getUserAccountSettings() {
        return userAccountSettings;
    }

    public void setUserAccountSettings(UserAccountSettings userAccountSettings) {
        this.userAccountSettings = userAccountSettings;
    }

    @Override
    public String toString() {
        return "UserSettings{" +
                "users=" + users +
                ", userAccountSettings=" + userAccountSettings +
                '}';
    }
}
