package com.example.shoppingcart;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class ListActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    FirebaseFirestore db;
    MyAdapter adapter;
    ArrayList<CartItem> cartItemArrayList;
    ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        pd = new ProgressDialog(this);
        pd.setCancelable(false);
        pd.setMessage("Betoltes...");
        pd.show();

        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        db = FirebaseFirestore.getInstance();
        cartItemArrayList = new ArrayList<CartItem>();
        adapter = new MyAdapter(this, cartItemArrayList);

        recyclerView.setAdapter(adapter);
        showData();
    }

    private void showData(){
        db.collection("ShoppingCart").orderBy("Name", Query.Direction.ASCENDING)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        if (error != null){
                            if (pd.isShowing()){
                                pd.dismiss();
                            }
                            Log.e("Firestore hiba.", error.getMessage());
                        }

                        for (DocumentChange dc: value.getDocumentChanges()){
                            if (dc.getType() == DocumentChange.Type.ADDED){
                                cartItemArrayList.add(dc.getDocument().toObject(CartItem.class));
                            }
                        }
                            adapter.notifyDataSetChanged();
                            if (pd.isShowing()){
                                pd.dismiss();
                            }
                    }
                });
    }

    public void back(View view) {
        Intent intent = new Intent(this, ActionsActivity.class);

        startActivity(intent);
    }
}