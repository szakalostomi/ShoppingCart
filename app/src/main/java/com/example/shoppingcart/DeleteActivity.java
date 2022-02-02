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

public class DeleteActivity extends AppCompatActivity {
    Button deleteBtn;
    EditText nameET;
    FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete);

        db = FirebaseFirestore.getInstance();
        deleteBtn = findViewById(R.id.deleteButton);
        nameET = findViewById(R.id.add_name);

        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = nameET.getText().toString();
                nameET.setText("");
                deleteData(name);
            }
        });
    }

    public void deleteData(String nameSTR) {
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
                            .delete()
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Toast.makeText(DeleteActivity.this, "Torolve...", Toast.LENGTH_SHORT).show();

                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(DeleteActivity.this, "HIBA...", Toast.LENGTH_SHORT).show();
                                }
                            });
                }
                else {
                    Toast.makeText(DeleteActivity.this, "Nem sikerult a torles...", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void back(View view) {
        Intent intent = new Intent(this, ActionsActivity.class);

        startActivity(intent);
    }
}