package com.example.shoppingcart;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ActionsActivity extends AppCompatActivity {
    private static final String LOG_TAG = ActionsActivity.class.getName();
    private FirebaseUser user;
    private FirebaseAuth mAuth;

    Button listBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actions);

        //listBtn = findViewById(R.id.listButton);

        mAuth = FirebaseAuth.getInstance();
        user = FirebaseAuth.getInstance().getCurrentUser();

        if(user != null) {
            Log.d(LOG_TAG, "Authenticated user!");
        } else {
            Log.d(LOG_TAG, "Unauthenticated user!");
            finish();
        }
/*
        listBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(this, ListActivity.class));
                finish();
            }
        });*/
    }

    public void list(View view) {
        Intent intent = new Intent(this, ListActivity.class);

        startActivity(intent);
    }

    public void add(View view) {
        Intent intent = new Intent(this, AddActivity.class);

        startActivity(intent);
    }

    public void update(View view) {
        Intent intent = new Intent(this, UpdateActivity.class);

        startActivity(intent);
    }

    public void delete(View view) {
        Intent intent = new Intent(this, DeleteActivity.class);

        startActivity(intent);
    }

    public void logout(View view) {
        Intent intent = new Intent(this, MainActivity.class);

        startActivity(intent);
    }
}