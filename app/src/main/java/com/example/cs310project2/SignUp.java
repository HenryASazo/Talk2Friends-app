package com.example.cs310project2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.CheckBox;

import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SignUp extends AppCompatActivity {

    private FirebaseDatabase root;
    private DatabaseReference reference;

    public Button submit_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);


        //Initializing the button for sign up
        submit_btn = (Button) findViewById(R.id.sign_up_submit_btn);
        submit_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SignUp();
            }
        });
    }


    public void SignUp() {
        root = FirebaseDatabase.getInstance();

        String[] inChar = {"!", "#", "$", "%", "^", "&", "*", "(", ")", "<", ">", ":", ";", "{", "}", "[", "]"};
        boolean containsInvalidCharacter = false;


        EditText emailEditText = findViewById(R.id.email_input);
        String email = emailEditText.getText().toString();

        String[] username = email.split("@");
        reference = root.getReference("users/" + username[0]);

        for (String invalid : inChar) {
            if (email.contains(invalid)) {
                containsInvalidCharacter = true;
                break;
            }
        }
        if (username[1].equals("usc.edu") && containsInvalidCharacter == false) {

            EditText nameEditText = findViewById(R.id.name_input);
            String name = nameEditText.getText().toString();

            EditText ageEditText = findViewById(R.id.age_input);
            int age = Integer.valueOf(ageEditText.getText().toString());

            EditText statusEditText = findViewById(R.id.status_input);
            String status = statusEditText.getText().toString();

            EditText passwordEditText = findViewById(R.id.si_password_input);
            String password = passwordEditText.getText().toString();

            CheckBox readingCheckBox = findViewById(R.id.reading_input);
            boolean reading = readingCheckBox.isChecked();

            CheckBox musicCheckBox = findViewById(R.id.music_input);
            boolean music = musicCheckBox.isChecked();

            CheckBox sportsCheckBox = findViewById(R.id.sports_input);
            boolean sports = sportsCheckBox.isChecked();


            User temp = new User(name, age, username[0], status, password, reading, music, sports);
            reference.setValue(temp);

            Intent intent = new Intent(this, home.class);
            intent.putExtra("user", temp.getEmail());
            startActivity(intent);
        }
    }
}