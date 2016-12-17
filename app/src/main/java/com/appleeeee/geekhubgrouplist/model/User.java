package com.appleeeee.geekhubgrouplist.model;

import io.realm.RealmObject;

public class User extends RealmObject{

    private String name;
    private String nameInLowerCase;
    private String gitUrl;
    private String googleUrl;
    private String gitNickName;
    private String googleNickName;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGitUrl() {
        return gitUrl;
    }

    public void setGitUrl(String gitUrl) {
        this.gitUrl = gitUrl;
    }

    public String getGoogleUrl() {
        return googleUrl;
    }

    public void setGoogleUrl(String googleUrl) {
        this.googleUrl = googleUrl;
    }

    public String getGitNickName() {
        return gitNickName;
    }

    public void setGitNickName(String gitNickName) {
        this.gitNickName = gitNickName;
    }

    public String getGoogleNickName() {
        return googleNickName;
    }

    public void setGoogleNickName(String googleNickName) {
        this.googleNickName = googleNickName;
    }

    public String getNameInLowerCase() {
        return nameInLowerCase;
    }

    public void setNameInLowerCase(String nameInLowerCase) {
        this.nameInLowerCase = nameInLowerCase;
    }

    public User(String name, String gitUrl, String googleUrl, String gitNickName, String googleNickName) {
        this.name = name;
        this.nameInLowerCase = name.toLowerCase();
        this.gitUrl = gitUrl;
        this.googleUrl = googleUrl;
        this.gitNickName = gitNickName;
        this.googleNickName = googleNickName;
    }

    public User() {
    }
}
