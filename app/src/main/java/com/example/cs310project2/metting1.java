package com.example.cs310project2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class metting1 extends AppCompatActivity {

    private meeting m;
    private User currUser;

    private Button join_btn;
    private Button leave_btn;
    private Button back_btn;

    private String userName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //set the current user and meeting
        Intent intent = getIntent();
        userName = intent.getStringExtra("user");
        String mID = intent.getStringExtra("id");

        //get the user first
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

        //now the meeting
        reference = root.getReference("meetings/" + mID);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                m = snapshot.getValue(meeting.class);
                if(m != null) {
                    String participants = "";
                    for(int i = 0; i < m.getMembers().size() - 1; i++) {
                        participants += m.getMembers().get(i);
                        participants += ", ";
                    }

                    participants += m.getMembers().get(m.getMembers().size() - 1);
                    TextView members = (TextView) findViewById(R.id.participants);
                    members.setText(participants);

                    TextView topic = (TextView) findViewById(R.id.topic);
                    topic.setText(m.getTopic());
                    TextView time = (TextView) findViewById(R.id.time);
                    time.setText(m.getTime());
                    TextView location = (TextView) findViewById(R.id.location);
                    location.setText(m.getLocation());
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_metting1);

        join_btn = (Button) findViewById(R.id.get_rsv);
        join_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                JoinMeeting();
            }
        });

        leave_btn = (Button) findViewById(R.id.cancel_rsv);
        leave_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LeaveMeeting();
            }
        });

        back_btn = (Button) findViewById(R.id.back_btn);

        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                backHome();
            }
        });
    }


    private void backHome(){
        Intent intent = new Intent(this, home.class);
        intent.putExtra("user", userName);
        startActivity(intent);
    }

    private void JoinMeeting() {//function to add a user to the current meeting
        if(m.joinMeeting(currUser.getEmail())) {//the user was sucessfully added (not already in the meeting)
            //update the meeting on firebase
            FirebaseDatabase root = FirebaseDatabase.getInstance();
            DatabaseReference reference = root.getReference("meetings/" + m.getID());
            reference.setValue(m);

            //reload the page
            Intent intent = new Intent(this, metting1.class);
            intent.putExtra("id", m.getID());
            intent.putExtra("user", currUser.getEmail());
            startActivity(intent);

        } else {//user tried to joing a meeting they are already in, do nothing

        }
    }

    private void LeaveMeeting() {//function to withdraw a user from the current meeting
        if(m.leaveMeeting(currUser.getEmail())) {//the current user was successfully removed from the meeting
            FirebaseDatabase root = FirebaseDatabase.getInstance();
            DatabaseReference reference = root.getReference("meetings/" + m.getID());
            //check the size of the users
            if(m.getMembers().size() > 0) {//greater than 0 means the meeting still exists
                //update on db and refresh the page
                reference.setValue(m);

                //reload the page
                Intent intent = new Intent(this, metting1.class);
                intent.putExtra("id", m.getID());
                intent.putExtra("user", currUser.getEmail());
                startActivity(intent);

            } else {//remove the meeting from the database
                reference.removeValue();
                //go to the home page
                Intent intent = new Intent(this, home.class);
                intent.putExtra("user", currUser.getEmail());
                startActivity(intent);
            }

        } else {

        }
    }
}