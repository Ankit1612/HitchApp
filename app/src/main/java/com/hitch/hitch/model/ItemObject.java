package com.hitch.hitch.model;

/**
 * Created by Ankit Shah on 15-Sep-17.
 */
public class ItemObject {

    private String Name,Batch,Branch;
    int Profile;

    public ItemObject(String Name, String Batch, String Branch, int Profile){
        this.Name = Name;
        this.Batch = Batch;
        this.Branch = Branch;
        this.Profile = Profile;
    }

    public String getName(){
        return Name;
    }

    public void setName(String Name){
        this.Name = Name;
    }

    public String getBatch(){
        return Batch;
    }

    public void setBatch(String Batch){
        this.Branch = Batch;
    }

    public String getBranch(){
        return Branch;
    }

    public int getProfile(){
        return Profile;
    }

    public void setProfile(int Profile){
        this.Profile = Profile;
    }
}
