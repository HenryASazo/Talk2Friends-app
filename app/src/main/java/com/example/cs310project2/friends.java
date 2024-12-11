package com.example.cs310project2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class friends extends AppCompatActivity {
    public Button meetings_btn;
    public Button profile_btn;
    private User currUser;
    public ArrayList<User> members;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friends);
        //set the current user
        Intent intent = getIntent();
        String userName = intent.getStringExtra("user");


        FirebaseDatabase root = FirebaseDatabase.getInstance();
        DatabaseReference reference = root.getReference("users/" + userName);

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                currUser = snapshot.getValue(User.class);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });

        LinearLayout line = (LinearLayout) findViewById(R.id.friends);
        LinearLayout sug = (LinearLayout) findViewById(R.id.suggestions);
        //get all the meetings from the database
        reference = root.getReference("users");
        Context context = this;
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot userSnapshot : snapshot.getChildren()) {
                    User u = userSnapshot.getValue(User.class);
                    if (u != null) {
                        if(ValidUser(u)) {
                            //Create the text view first
                            TextView tv = new TextView(context);
                            tv.setLayoutParams(new LinearLayout.LayoutParams(
                                    LinearLayout.LayoutParams.MATCH_PARENT,
                                    LinearLayout.LayoutParams.WRAP_CONTENT));
                            tv.setText(u.getName());
                            tv.setTypeface(null, Typeface.BOLD);
                            tv.setTextSize(30);

                            sug.addView(tv);

                            // Create Button
                            Button btn = new Button(context);
                            btn.setLayoutParams(new LinearLayout.LayoutParams(
                                    LinearLayout.LayoutParams.WRAP_CONTENT,
                                    LinearLayout.LayoutParams.WRAP_CONTENT));
                            btn.setText("Add");
                            btn.setTag(u.getEmail());
                            btn.setTextSize(30);
                            btn.setTextColor(Color.WHITE);
                            btn.setPadding(10, 0, 10, 5);
                            // Create LayoutParams with the desired margins
                            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                                    LinearLayout.LayoutParams.WRAP_CONTENT, // Width
                                    LinearLayout.LayoutParams.WRAP_CONTENT // Height
                            );

                            layoutParams.setMargins(10, 0, 10, 0); // Left, Top, Right, Bottom
                            btn.setHeight(55);

                            // Set the LayoutParams for the button
                            btn.setLayoutParams(layoutParams);
                            ColorStateList colorStateList = ColorStateList.valueOf(getResources().getColor(R.color.main_color));

                            // Set the background color of the button
                            btn.setBackgroundTintList(colorStateList);
                            btn.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    String friendName = (String) v.getTag();
                                    //Go to a meeting details view, passing in meeting and user ID
                                    AddFriend(friendName);
                                }
                            });

                            sug.addView(btn); // Add Button to LinearLayout
                        } else if(currUser.getFriends().contains(u.getEmail())) {//current user and u are friends
                            Log.d("create", "football friend");
                            //Create the text view first
                            TextView tv = new TextView(context);
                            tv.setLayoutParams(new LinearLayout.LayoutParams(
                                    LinearLayout.LayoutParams.MATCH_PARENT,
                                    LinearLayout.LayoutParams.WRAP_CONTENT));
                            tv.setText(u.getName());
                            tv.setTypeface(null, Typeface.BOLD);
                            tv.setTextSize(30);

                            line.addView(tv);

                            // Create Button
                            Button btn = new Button(context);
                            btn.setLayoutParams(new LinearLayout.LayoutParams(
                                    LinearLayout.LayoutParams.WRAP_CONTENT,
                                    LinearLayout.LayoutParams.WRAP_CONTENT));
                            btn.setText("Remove");
                            btn.setTag(u.getEmail());
                            btn.setTextSize(30);
                            btn.setTextColor(Color.WHITE);
                            btn.setPadding(10, 0, 10, 5);
                            // Create LayoutParams with the desired margins
                            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                                    LinearLayout.LayoutParams.WRAP_CONTENT, // Width
                                    LinearLayout.LayoutParams.WRAP_CONTENT // Height
                            );

                            layoutParams.setMargins(10, 0, 10, 0); // Left, Top, Right, Bottom
                            btn.setHeight(55);

                            // Set the LayoutParams for the button
                            btn.setLayoutParams(layoutParams);
                            ColorStateList colorStateList = ColorStateList.valueOf(getResources().getColor(R.color.main_color));

                            // Set the background color of the button
                            btn.setBackgroundTintList(colorStateList);
                            btn.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    String friendName = (String) v.getTag();
                                    //Go to a meeting details view, passing in meeting and user ID
                                    RemoveFriend(friendName);
                                }
                            });

                            line.addView(btn); // Add Button to LinearLayout
                        }
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });

                // Adding the buttons
        Button btn_i = new Button(context);
        btn_i.setLayoutParams(new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                ));
        btn_i.setHeight(45);
        btn_i.setText("Invite Friends");
        btn_i.setTextSize(30);
        btn_i.setTextColor(Color.WHITE);
        btn_i.setPadding(10, 0, 10, 5);

        ColorStateList colorStateList2 = ColorStateList.valueOf(getResources().getColor(R.color.main_color));
        btn_i.setBackgroundTintList(colorStateList2);

        // Set an OnClickListener for the button
        btn_i.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                invitation();
            }
        });
        line.addView(btn_i);

        meetings_btn = (Button) findViewById(R.id.meetings_btn);
        meetings_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openMeetings();
            }
        });

        profile_btn = (Button) findViewById(R.id.profile_btn);
        profile_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openProfile();
            }
        });
    }


    public void openMeetings(){
        Intent intent = new Intent(this, home.class);
        intent.putExtra("user", currUser.getEmail());
        startActivity(intent);
    }
    public void openProfile(){
        Intent intent = new Intent(this, profile.class);
        intent.putExtra("user", currUser.getEmail());
        startActivity(intent);
    }
    public void invitation(){
        Intent intent = new Intent(this, email.class);
        intent.putExtra("user", currUser.getEmail());
        startActivity(intent);
    }

    private void AddFriend(String userName) {
        //Log.d("create", userName);
        currUser.AddFriend(userName);
        FirebaseDatabase root = FirebaseDatabase.getInstance();
        DatabaseReference reference = root.getReference("users/" + currUser.getEmail());
        reference.setValue(currUser);
        DatabaseReference reference2 = root.getReference("users/" + userName);
        reference2.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User other = snapshot.getValue(User.class);
                if(other != null) {
                    other.AddFriend(currUser.getEmail());
                    reference2.setValue(other);
                    Intent intent = new Intent(friends.this, friends.class);
                    intent.putExtra("user", currUser.getEmail());
                    startActivity(intent);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });


    }


    private void RemoveFriend(String userName) {
        //Log.d("create", userName);
        currUser.RemoveFriend(userName);
        FirebaseDatabase root = FirebaseDatabase.getInstance();
        DatabaseReference reference = root.getReference("users/" + currUser.getEmail());
        reference.setValue(currUser);
        DatabaseReference reference2 = root.getReference("users/" + userName);
        reference2.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User other = snapshot.getValue(User.class);
                if(other != null) {
                    other.RemoveFriend(currUser.getEmail());
                    reference2.setValue(other);
                    Intent intent = new Intent(friends.this, friends.class);
                    intent.putExtra("user", currUser.getEmail());
                    startActivity(intent);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }

    private boolean ValidUser(User friend) {
        if(currUser.getEmail().equals(friend.getEmail())) {
            return false;
        }
        if(currUser.getFriends().contains(friend.getEmail())) {
            return false;
        }
        if(currUser.getLikesMusic() && friend.getLikesMusic()) {
            return  true;
        }
        if(currUser.getLikesReading() && friend.getLikesReading()) {
            return true;
        }
        if(currUser.getLikesSports() && friend.getLikesSports()) {
            return true;
        }

        return false;
    }

    //Return the user for testing purposes
    public User getUser() {return currUser;}

}