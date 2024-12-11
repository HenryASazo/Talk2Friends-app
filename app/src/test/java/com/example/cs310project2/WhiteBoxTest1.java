package com.example.cs310project2;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.junit.Test;

import java.util.concurrent.CountDownLatch;

public class WhiteBoxTest1 {
    @Test
    public void custom_user_creation() {
        User testUser = new User("Name", 20, "Email", "Native Speaker", "PW", true, true, true);
        assertEquals("Name", testUser.getName());
        assertEquals(String.valueOf(20), String.valueOf(testUser.getAge()));
        assertEquals("Email", testUser.getEmail());
        assertEquals("Native Speaker", testUser.getType());
        assertEquals("PW", testUser.getPassword());
        assertEquals(true, testUser.getLikesReading());
        assertEquals(true, testUser.getLikesMusic());
        assertEquals(true, testUser.getLikesSports());
    }

    @Test
    public void default_user_creation() {
        User testUser = new User();
        assertEquals("", testUser.getName());
        assertEquals(String.valueOf(0), String.valueOf(testUser.getAge()));
        assertEquals("", testUser.getEmail());
        assertEquals("Native", testUser.getType());
        assertEquals("", testUser.getPassword());
        assertEquals(false, testUser.getLikesReading());
        assertEquals(false, testUser.getLikesMusic());
        assertEquals(false, testUser.getLikesSports());
    }

    @Test
    public void add_friend() {
        User testUser = new User();
        testUser.AddFriend("TestFriend");
        assertEquals(true, testUser.getFriends().contains("TestFriend"));
        assertEquals(1, testUser.getFriends().size());
    }

    @Test
    public void remove_friend() {
        User testUser = new User();
        testUser.AddFriend("TestFriend");
        assertEquals(true, testUser.getFriends().contains("TestFriend"));
        assertEquals(1, testUser.getFriends().size());
        testUser.RemoveFriend("TestFriend");
        assertEquals(false, testUser.getFriends().contains("TestFriend"));
        assertEquals(0, testUser.getFriends().size());
    }

    @Test
    public void update_user() {
        //Create a user, and check for default values
        User testUser = new User();
        assertEquals("", testUser.getName());
        assertEquals(String.valueOf(0), String.valueOf(testUser.getAge()));
        assertEquals("", testUser.getEmail());
        assertEquals("Native", testUser.getType());
        assertEquals("", testUser.getPassword());
        assertEquals(false, testUser.getLikesReading());
        assertEquals(false, testUser.getLikesMusic());
        assertEquals(false, testUser.getLikesSports());

        //update the user's values and make sure they are the updated ones
        testUser.SetAge(10);
        testUser.SetType("International");
        testUser.SetPassword("PW");
        testUser.SetLikesReading(true);
        testUser.SetLikesMusic(true);
        testUser.SetLikesSports(true);
        assertEquals(String.valueOf(10), String.valueOf(testUser.getAge()));
        assertEquals("International", testUser.getType());
        assertEquals("PW", testUser.getPassword());
        assertEquals(true, testUser.getLikesReading());
        assertEquals(true, testUser.getLikesMusic());
        assertEquals(true, testUser.getLikesSports());
    }
}
