package com.example.cs310project2;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.junit.Test;


public class WhiteBoxTest3 {
    @Test
    public void custom_meeting_creation() {
        meeting testMeeting = new meeting("Host", "THH 201", "12:00", "Test");
        assertEquals("Host", testMeeting.getHost());
        assertEquals("THH 201", testMeeting.getLocation());
        assertEquals("12:00", testMeeting.getTime());
        assertEquals("Test", testMeeting.getTopic());
    }
    @Test
    public void create_and_cancel() {
        meeting testMeeting = new meeting("Host", "THH 101", "12:00", "Test2");
        assertEquals("Host", testMeeting.getHost());
        assertEquals("THH 101", testMeeting.getLocation());
        assertEquals("12:00", testMeeting.getTime());
        assertEquals("Test2", testMeeting.getTopic());
        testMeeting.leaveMeeting("Test User");
        assertEquals(false, testMeeting.getMembers().contains("Test User"));
        assertEquals(0, testMeeting.getMembers().size());
    }
    @Test
    public void reserve_spot() {
        meeting testMeeting = new meeting();
        testMeeting.joinMeeting("Test User");
        assertEquals(true, testMeeting.getMembers().contains("Test User"));
        assertEquals(1, testMeeting.getMembers().size());
    }
    @Test
    public void cancel_spot() {
        meeting testMeeting = new meeting();
        testMeeting.joinMeeting("Test User");
        assertEquals(true, testMeeting.getMembers().contains("Test User"));
        assertEquals(1, testMeeting.getMembers().size());
        testMeeting.leaveMeeting("Test User");
        assertEquals(false, testMeeting.getMembers().contains("Test User"));
        assertEquals(0, testMeeting.getMembers().size());

    }
    @Test
    public void reserve_twice() {
        meeting testMeeting = new meeting();
        testMeeting.joinMeeting("Test User");
        assertEquals(true, testMeeting.getMembers().contains("Test User"));
        assertEquals(1, testMeeting.getMembers().size());
        testMeeting.joinMeeting("Test User");
        assertEquals(true, testMeeting.getMembers().contains("Test User"));
        assertEquals(1, testMeeting.getMembers().size());
    }

}
