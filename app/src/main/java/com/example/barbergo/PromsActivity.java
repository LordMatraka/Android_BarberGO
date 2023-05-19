package com.example.barbergo;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;

public class PromsActivity extends AppCompatActivity{

    private RecyclerView recyclerView;
    private PromsAdapter adapter;
    private ArrayList<Promotion> promotions;

    private FirebaseDatabase databaseInstance;
    private DatabaseReference databaseRef;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_proms);

        databaseInstance = FirebaseDatabase.getInstance();
        databaseRef = databaseInstance.getReference();


        recyclerView = (RecyclerView)findViewById(R.id.promsRecyclerView);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(manager);
        recyclerView.setHasFixedSize(true);
        promotions = new ArrayList<Promotion>();

        databaseRef.child("Ofertas").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if(!task.isSuccessful()){
                    Toast.makeText(PromsActivity.this, "Error obteniendo las promociones, asegúrese de que está " +
                                    "conectado a internet e inténtelo de nuevo.",
                            Toast.LENGTH_SHORT).show();
                    Log.e("promos", "Error getting data", task.getException());
                }
                else{
                    HashMap<String, Object> promotionsData = (HashMap<String, Object>) task.getResult().getValue();

                    //Obtengo todos los usuarios de la base de datos
                    for(String key: promotionsData.keySet()) {
                        HashMap<String, Object> promotion = (HashMap<String, Object>) promotionsData.get(key);
                        promotions.add(new Promotion(
                                (String)promotion.get("Name"),
                                (String)promotion.get("Descripcion"),
                                (long)promotion.get("Imagen")
                        ));
                    }

                    adapter= new PromsAdapter(promotions);
                    recyclerView.setAdapter(adapter);
                }
            }
        });

    }
}