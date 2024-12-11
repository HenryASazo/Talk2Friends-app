package com.example.cs310project2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class profile extends AppCompatActivity {

    private User currUser;
    public Button update_btn;
    public Button friends_btn;
    public Button meetings_btn;
    public Button back_btn;

    public String username;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        //get the current user information, and place it in the boxes
        Intent intent = getIntent();
        username = intent.getStringExtra("user");

        FirebaseDatabase root = FirebaseDatabase.getInstance();
        DatabaseReference reference = root.getReference("users/" + username);

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                currUser = snapshot.getValue(User.class);
                if(currUser != null) {
                    TextView name = (TextView) findViewById(R.id.name);
                    name.setText(currUser.getName());
                    TextView age = (TextView) findViewById(R.id.age);
                    age.setText(currUser.getAge().toString());
                    TextView email = (TextView) findViewById(R.id.email);
                    email.setText(currUser.getEmail());
                    TextView speaker = (TextView) findViewById(R.id.speaker);
                    speaker.setText(currUser.getType());
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        friends_btn = (Button) findViewById(R.id.friends_btn);
        friends_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openFriends();
            }
        });

        meetings_btn = (Button) findViewById(R.id.meetings_btn);
        meetings_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openMeetings();
            }
        });


        update_btn = (Button) findViewById(R.id.update_btn);

        update_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                update_();
            }
        });


    }


    public void update_(){
        Intent intent = new Intent(this, update.class);
        intent.putExtra("user", currUser.getEmail());
        startActivity(intent);
    }

    public void openFriends(){
        Intent intent = new Intent(this, friends.class);
        intent.putExtra("user", currUser.getEmail());
        startActivity(intent);
    }

    public void openMeetings(){
        Intent intent = new Intent(this, home.class);
        intent.putExtra("user", currUser.getEmail());
        startActivity(intent);
    }
}