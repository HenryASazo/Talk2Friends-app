package com.example.cs310project2;

import java.util.ArrayList;

public class User {
    private String name;
    private Integer age;
    private String email;
    private String type;
    private String password;

    private Boolean likesReading;
    private Boolean likesMusic;
    private Boolean likesSports;
    private ArrayList<String> friends;

    User() {//default constructor
        name = "";
        age = 0;
        email = "";
        type = "Native";
        password = "";
        likesReading = false;
        likesMusic = false;
        likesSports = false;
        friends = new ArrayList<>();
    }
    User(String oName, Integer oAge, String oEmail, String oType, String oPassword, Boolean oLikesReading, Boolean oLikesMusic, Boolean oLikesSports) {
        name = oName;
        age = oAge;
        email = oEmail;
        type = oType;
        password = oPassword;
        likesReading = oLikesReading;
        likesMusic = oLikesMusic;
        likesSports = oLikesSports;
        friends = new ArrayList<>();
    }

    // Getter methods
    public String getName() {
        return name;
    }

    public Integer getAge() {
        return age;
    }

    public String getEmail() {
        return email;
    }

    public String getType() {
        return type;
    }

    public String getPassword() {
        return password;
    }

    public Boolean getLikesReading() {
        return likesReading;
    }

    public Boolean getLikesMusic() {
        return likesMusic;
    }

    public Boolean getLikesSports() {
        return likesSports;
    }

    public void SetAge(int newAge) { age = newAge;}
    public void SetPassword(String newPW) {password = newPW;}
    public void SetType(String newType) {type = newType;}
    public void SetLikesReading(Boolean newReading) {likesReading = newReading;}
    public void SetLikesMusic(Boolean newMusic) {likesMusic = newMusic;}
    public void SetLikesSports(Boolean newSports) {likesSports = newSports;}

    public ArrayList<String> getFriends() {
        return new ArrayList<>(friends); // Return a copy of the friends list to prevent external modifications
    }

    public void AddFriend(String friend) { friends.add(friend);
    }

    public void RemoveFriend(String friend) {friends.remove(friend);}
}
