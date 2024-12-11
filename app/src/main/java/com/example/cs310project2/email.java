package com.example.cs310project2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.content.Intent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Random;



public class email extends AppCompatActivity {
    private EditText mEditTextTo;
    public Button send_btn;

    private User currUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        //get the current user information, and place it in the boxes
        Intent intent = getIntent();
        String username = intent.getStringExtra("user");

        FirebaseDatabase root = FirebaseDatabase.getInstance();
        DatabaseReference reference = root.getReference("users/" + username);

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                currUser = snapshot.getValue(User.class);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_email);
        mEditTextTo = findViewById(R.id.email_input);

        send_btn = findViewById(R.id.send_btn);
        send_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendMail();
            }
        });
    }

    private void sendMail() {
        String recipientList = mEditTextTo.getText().toString();
        String[] recipients = recipientList.split(",");

        int code = generateRandom4DigitNumber();
        String subject = "Invitation to Talk2Friends";
        String message = "Hello Friend,\nPlease enter this code "+ code + " as your password and your current email to join our app!"+
                "\n\n Best, \n Talk2Friends Team";



        FirebaseDatabase root = FirebaseDatabase.getInstance();
        for(int i = 0; i < recipients.length; i++) {
            DatabaseReference reference = root.getReference("users/" + recipients[i]);
            User friend = new User("Name", 0, recipients[i], "Native Speaker", Integer.toString(code), true, true, true);
            friend.AddFriend(currUser.getEmail());
            currUser.AddFriend(recipients[i]);
            reference.setValue(friend);
        }

        //write the friend to the db
        DatabaseReference reference = root.getReference("users/" + currUser.getEmail());
        reference.setValue(currUser);

        // Write the modified currUser to the database
        reference.setValue(currUser, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                if (databaseError != null) {
                    // Handle the error, e.g., log it, display a message to the user, etc.
                } else {
                    // Proceed with sending the email since the database operation is complete
                    Intent intent = new Intent(Intent.ACTION_SEND);
                    intent.putExtra(Intent.EXTRA_EMAIL, recipients);
                    intent.putExtra(Intent.EXTRA_SUBJECT, subject);
                    intent.putExtra(Intent.EXTRA_TEXT, message);
                    intent.setType("message/rfc822");
                    startActivity(Intent.createChooser(intent, "Choose an email client"));
                }
            }
        });

        /*Intent intent = new Intent(Intent.ACTION_SEND);
        intent.putExtra(Intent.EXTRA_EMAIL, recipients);
        intent.putExtra(Intent.EXTRA_SUBJECT, subject);
        intent.putExtra(Intent.EXTRA_TEXT, message);
        intent.setType("message/rfc822");
        startActivity(Intent.createChooser(intent, "Choose an email client"));*/
    }

    public static int generateRandom4DigitNumber() {
        // Create an instance of the Random class
        Random random = new Random();

        // Generate a random integer between 1000 and 9999
        int random4DigitNumber = random.nextInt(9000) + 1000;

        return random4DigitNumber;
    }
}