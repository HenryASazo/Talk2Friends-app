# Talk2Friends - Android App for USC Students and Alumni (CSCI 310)

**Talk2Friends** is an Android application designed to help USC students and alumni connect. Native English speakers can help international students improve their English skills through practice and meaningful conversations. This is version 1.0 of the app, built with **Java** and powered by **Firebase** for secure backend data storage.

## Introduction

At USC, there is a significant population of non-native English-speaking students. **Talk2Friends** provides a platform to bridge the communication gap by connecting non-native English-speaking students and alumni with native English-speaking counterparts. The app ensures user authenticity by restricting sign-ups to valid USC email addresses.

**Key Features:**

- Create and manage personalized profiles with information like name, age, affiliation, and language proficiency.
- Suggest friends based on shared interests.
- Schedule and join meetings for English practice, with details like topics, time, and location.
- Send and receive friend requests, and invite non-users to join the platform.

## Features and Functionalities

### **User Features**

- **Invite Users:** Invite USC-affiliated friends to join Talk2Friends.
- **Join/Create Meetings:** Participate in or organize practice meetings with details like topic, time, and location.
- **Friend Suggestions:** Get friend recommendations based on shared interests and invite them to connect.
- **Update Profile:** Edit your name, age, affiliation, and interests anytime.
- **Secure Sign-Up:** Sign up with a USC email, specify your affiliation (student/alumni), and indicate your language proficiency (native/non-native speaker).

### **Enhancements**

- Checker for password strength, age validation, and native speaker updates.
- Visual confirmation of saved profile data.
- Clear error messages for invalid input.
- Improved UI/UX with separate "Friends" and "Friend Suggestions" tabs.

## System Requirements Specification

### Functional Requirements

1. **Invite Users:**
   - Send invites via valid USC email addresses.
   - Notify users of invalid emails.
2. **Meetings:**
   - View and join public meetings.
   - Create new meetings with topics, time, and locations (physical or virtual).
3. **Friend Suggestions:**
   - View suggested friends based on shared interests.
   - Send friend requests with a single tap.
4. **Profile Management:**
   - Update profile information easily.
5. **Sign-Up:**
   - Ensure valid USC-affiliated email addresses during registration.

## Technical Details

- **Frontend:** Java (Android Studio) for intuitive and responsive UI.
- **Backend:** Firebase for secure and real-time data storage.
- **Validation:**
  - Email validation for USC domain.
  - Input checks for password, age, and profile updates.
- **Error Handling:** Contextual error messages for smoother user experience.

## How It Works

### Onboarding:

1. New users create an account by entering their name, age, and USC email.
2. Specify affiliation (student/alumni) and language proficiency.
3. Receive confirmation upon successful account creation and log in.

### Meeting Management:

1. Users can create or join practice meetings.
2. Details like topics, time, and location are included for each meeting.
3. Join meetings through integrated Zoom links or physical addresses.

### Friend Connections:

1. View suggested friends based on shared interests.
2. Send and receive friend requests seamlessly.

## Version Details

This is **Version 1.0**. The app is fully functional, and all main features are implemented as intended. Future updates will include further enhancements based on user feedback.

## TESTING

Instructions on how to open and run our project

1. We are running our project on a Pixel 2 with an API Version of 24

2. The user credentials are Username: **\_\_\_\_** Password: **\_\_\_\_**

3. Potential Problems:
   When sending a friend the email invitation, we use the google gmail so there is a slight chance you will have to log into a gmail in order to see the email message being sent and verified.

4. In order to run our app, we have just hit the run button and everything should run.
