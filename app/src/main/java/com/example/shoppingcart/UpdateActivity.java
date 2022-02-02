package com.example.shoppingcart;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

public class UpdateActivity extends AppCompatActivity {

    Button updateBtn;
    EditText oldnameET;
    EditText newnameET;
    FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        db = FirebaseFirestore.getInstance();
        updateBtn = findViewById(R.id.updateButton);
        oldnameET = findViewById(R.id.oldName);
        newnameET = findViewById(R.id.newName);

        updateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String oldName = oldnameET.getText().toString();
                oldnameET.setText("");

                String newName = newnameET.getText().toString();
                newnameET.setText("");
                updateData(oldName, newName);
            }
        });
    }

    public void updateData(String nameSTR, String newnameSTR) {
        db.collection("ShoppingCart")
                .whereEqualTo("Name", nameSTR)
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful() && !task.getResult().isEmpty()){
                    DocumentSnapshot documentSnapshot = task.getResult().getDocuments().get(0);
                    String documentID = documentSnapshot.getId();
                    db.collection("ShoppingCart")
                            .document(documentID)
                            .update("Name", newnameSTR)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Toast.makeText(UpdateActivity.this, "Frissitve...", Toast.LENGTH_SHORT).show();

                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(UpdateActivity.this, "HIBA...", Toast.LENGTH_SHORT).show();
                                }
                            });
                }
                else {
                    Toast.makeText(UpdateActivity.this, "Nem sikerult a frissites...", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void back(View view) {
        Intent intent = new Intent(this, ActionsActivity.class);

        startActivity(intent);
    }
}