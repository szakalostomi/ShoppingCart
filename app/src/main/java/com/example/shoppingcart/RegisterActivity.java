package com.example.shoppingcart;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class RegisterActivity extends AppCompatActivity {
    private static final String LOG_TAG = RegisterActivity.class.getName();
    private static final String PREF_KEY = RegisterActivity.class.getPackage().toString();
    private static final int SECRET_KEY = 99;

    EditText emailET;
    EditText passwordET;
    EditText passwordconfET;

    private SharedPreferences preferences;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        //int secret_key = getIntent().getIntExtra("SECRET_KEY", 0);

        //if (secret_key != 99){
        //    finish();
        //}

        mAuth = FirebaseAuth.getInstance();

        emailET = findViewById(R.id.Email);
        passwordET = findViewById(R.id.Password);
        passwordconfET = findViewById(R.id.Passwordagain);

        preferences = getSharedPreferences(PREF_KEY, MODE_PRIVATE);
        String email = preferences.getString("email", "");
        String password = preferences.getString("password", "");

        emailET.setText(email);
        passwordET.setText(password);
        passwordconfET.setText(password);

        Log.i(LOG_TAG, "onCreate");
    }

    public void register(View view){
        String email = emailET.getText().toString();
        String password = passwordET.getText().toString();
        String passwordConfirm = passwordconfET.getText().toString();

        if (!password.equals(passwordConfirm)) {
            Log.e(LOG_TAG, "Nem egyenlő a jelszó és a megerősítése.");
            return;
        }

        Log.i(LOG_TAG, "Regisztrált: " + email);

        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()) {
                    Log.d(LOG_TAG, "User created successfully");
                    go_to_actions();
                } else {
                    Log.d(LOG_TAG, "User was't created successfully:", task.getException());
                    Toast.makeText(RegisterActivity.this, "User was't created successfully:", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    public void go_to_actions() {
        Intent intent = new Intent(this, ActionsActivity.class);
        //intent.putExtra("SECRET_KEY", SECRET_KEY);
        startActivity(intent);
    }

    public void back(View view) {
        Intent intent = new Intent(this, MainActivity.class);

        startActivity(intent);
    }
}