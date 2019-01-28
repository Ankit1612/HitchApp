package com.hitch.hitch.model;

/**
 * Created by Ankit Shah on 25-Sep-17.
 */

public class UserAccountSettings {

    private String about_me;
    private String awards;
    private String batch_start_date;
    private String branch;
    private String college_location;
    private String college_name;
    private String cover_page_image;
    private String degree_programme;
    private String dob;
    private String fullname;
    private String graduate;
    private String hobbies;
    private String hometown;
    private String profile_image;
    private String project_publication;
    private String society_ngo;
    private String university;
    private String username;
    private String website_blogs;
    private String status;
    private String user_id;
    private String profile_id;

    public UserAccountSettings(){

    }

    public UserAccountSettings(String about_me, String awards, String batch_start_date, String branch,
                               String college_location, String college_name, String cover_page_image,
                               String degree_programme, String dob, String fullname, String graduate,
                               String hobbies, String hometown, String profile_image,
                               String project_publication, String society_ngo, String university,
                               String username, String website_blogs, String status, String user_id,
                               String profile_id)
    {
        this.about_me = about_me;
        this.awards = awards;
        this.batch_start_date = batch_start_date;
        this.branch = branch;
        this.college_location = college_location;
        this.college_name = college_name;
        this.cover_page_image = cover_page_image;
        this.degree_programme = degree_programme;
        this.dob = dob;
        this.fullname = fullname;
        this.graduate = graduate;
        this.hobbies = hobbies;
        this.hometown = hometown;
        this.profile_image = profile_image;
        this.project_publication = project_publication;
        this.society_ngo = society_ngo;
        this.university = university;
        this.username = username;
        this.website_blogs = website_blogs;
        this.status = status;
        this.user_id = user_id;
        this.profile_id = profile_id;
    }

    public String getAbout_me() {
        return about_me;
    }

    public void setAbout_me(String about_me) {
        this.about_me = about_me;
    }

    public String getAwards() {
        return awards;
    }

    public void setAwards(String awards) {
        this.awards = awards;
    }

    public String getBatch_start_date() {
        return batch_start_date;
    }

    public void setBatch_start_date(String batch_start_date) {
        this.batch_start_date = batch_start_date;
    }

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public String getCollege_location() {
        return college_location;
    }

    public void setCollege_location(String college_location) {
        this.college_location = college_location;
    }

    public String getCollege_name() {
        return college_name;
    }

    public void setCollege_name(String college_name) {
        this.college_name = college_name;
    }

    public String getCover_page_image() {
        return cover_page_image;
    }

    public void setCover_page_image(String cover_page_image) {
        this.cover_page_image = cover_page_image;
    }

    public String getDegree_programme() {
        return degree_programme;
    }

    public void setDegree_programme(String degree_programme) {
        this.degree_programme = degree_programme;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getGraduate() {
        return graduate;
    }

    public void setGraduate(String graduate) {
        this.graduate = graduate;
    }

    public String getHobbies() {
        return hobbies;
    }

    public void setHobbies(String hobbies) {
        this.hobbies = hobbies;
    }

    public String getHometown() {
        return hometown;
    }

    public void setHometown(String hometown) {
        this.hometown = hometown;
    }

    public String getProfile_image() {
        return profile_image;
    }

    public void setProfile_image(String profile_image) {
        this.profile_image = profile_image;
    }

    public String getProject_publication() {
        return project_publication;
    }

    public void setProject_publication(String project_publication) {
        this.project_publication = project_publication;
    }

    public String getSociety_ngo() {
        return society_ngo;
    }

    public void setSociety_ngo(String society_ngo) {
        this.society_ngo = society_ngo;
    }

    public String getUniversity() {
        return university;
    }

    public void setUniversity(String university) {
        this.university = university;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getWebsite_blogs() {
        return website_blogs;
    }

    public void setWebsite_blogs(String website_blogs) {
        this.website_blogs = website_blogs;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getProfile_id() {
        return profile_id;
    }

    public void setProfile_id(String profile_id) {
        this.profile_id = profile_id;
    }

    @Override
    public String toString() {
        return "UserAccountSettings{" +
                "about_me='" + about_me + '\'' +
                ", awards='" + awards + '\'' +
                ", batch_start_date='" + batch_start_date + '\'' +
                ", branch='" + branch + '\'' +
                ", college_location='" + college_location + '\'' +
                ", college_name='" + college_name + '\'' +
                ", cover_page_image='" + cover_page_image + '\'' +
                ", degree_programme='" + degree_programme + '\'' +
                ", dob='" + dob + '\'' +
                ", fullname='" + fullname + '\'' +
                ", graduate='" + graduate + '\'' +
                ", hobbies='" + hobbies + '\'' +
                ", hometown='" + hometown + '\'' +
                ", profile_image='" + profile_image + '\'' +
                ", project_publication='" + project_publication + '\'' +
                ", society_ngo='" + society_ngo + '\'' +
                ", university='" + university + '\'' +
                ", username='" + username + '\'' +
                ", website_blogs='" + website_blogs + '\'' +
                ", status='" + status + '\'' +
                ", user_id='" + user_id + '\'' +
                ", profile_id='" + profile_id + '\'' +
                '}';
    }
}
