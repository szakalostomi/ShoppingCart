package com.example.shoppingcart;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class AddActivity extends AppCompatActivity {
    EditText mName;
    EditText mPrice;
    EditText mDescription;
    Button addButton;

    ProgressDialog pd;

    FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        mName = findViewById(R.id.addName);
        mPrice = findViewById(R.id.addPrice);
        mDescription = findViewById(R.id.addDesc);
        addButton = findViewById(R.id.addBTN);

        pd = new ProgressDialog(this);
        db = FirebaseFirestore.getInstance();

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = mName.getText().toString().trim();
                String price = mPrice.getText().toString().trim();
                String desc = mDescription.getText().toString().trim();

                add(name, price, desc);
            }
        });
    }

    public void add(String name, String price, String desc) {
        pd.setTitle("Firebase-hez adas");
        pd.show();
        String id = UUID.randomUUID().toString();

        Map<String, Object> doc = new HashMap<>();
        doc.put("Id", id);
        doc.put("Name", name);
        doc.put("Price", price);
        doc.put("Description", desc);

        db.collection("ShoppingCart")
                .document(id)
                .set(doc)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                pd.dismiss();
                Toast.makeText(AddActivity.this, "Feltoltve", Toast.LENGTH_SHORT).show();
            }
        })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        pd.dismiss();
                        Toast.makeText(AddActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    public void back(View view) {
        Intent intent = new Intent(this, ActionsActivity.class);

        startActivity(intent);
    }
}