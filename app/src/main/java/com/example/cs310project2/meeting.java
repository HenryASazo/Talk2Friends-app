package com.example.cs310project2;

import java.util.ArrayList;
import java.lang.*;


public class meeting {
    //basic information about the meeting
    public String host;
    private String location;
    private String time;
    private String topic;
    private ArrayList<String> members;
    private final String id;


    //Constructor
    meeting(String creator, String oLocation, String oTime, String oTopic) {
        host = creator;
        location = oLocation;
        time = oTime;
        topic = oTopic;
        members = new ArrayList<>();
        members.add(host);
        id = String.valueOf(System.currentTimeMillis());
    }

    //default constructor necessary
    meeting() {
        host = "";
        location = "";
        time = "";
        topic = "";
        members = new ArrayList<>();
        id = "";
    }

    //basic getters and functions for the meeting
    public String getHost() {return host;}
    public String getLocation() {
        return location;
    }

    public String getTime() {
        return time;
    }
    public String getTopic() {
        return topic;
    }

    public ArrayList<String> getMembers() {
        return members;
    }

    public String getID() {
        return id;
    }

    public boolean joinMeeting(String user) {
        if(members.contains(user)) {//check if the array already contains the user
            return false;
        }
        members.add(user);
        return true;
    }

    public boolean leaveMeeting(String user) {
        if(!members.contains(user)) {//no user, cannot remove
            return false;
        }
        members.remove(user);
        if(user.equals(host) && members.size() > 0) { //set the host to the next available user, else set to empty string
            host = members.get(0);
        } else if(user.equals(host)) {
            host = "";
        }

        return true;
    }
}
