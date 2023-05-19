package com.example.barbergo;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.LinearLayout;
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

public class PersonalActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private EmployeeAdapter adapter;
    private ArrayList<Employee> employees;

    private FirebaseDatabase databaseInstance;
    private DatabaseReference databaseRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal);

        databaseInstance = FirebaseDatabase.getInstance();
        databaseRef = databaseInstance.getReference();

        recyclerView = (RecyclerView)findViewById(R.id.employeesRecyclerView);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(manager);
        recyclerView.setHasFixedSize(true);
        Bundle bundle = getIntent().getExtras();
        long centro_id = bundle.getLong("centroId");
        employees = new ArrayList<Employee>();


        databaseRef.child("Users").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if(!task.isSuccessful()){
                    Toast.makeText(PersonalActivity.this, "Error obteniendo las promociones, asegúrese de que está " +
                                    "conectado a internet e inténtelo de nuevo.",
                            Toast.LENGTH_SHORT).show();
                    Log.e("promos", "Error getting data", task.getException());
                }
                else{
                    HashMap<String, Object> users = (HashMap<String, Object>) task.getResult().getValue();

                    //Obtengo todos los usuarios de la base de datos
                    for(String key: users.keySet()){
                        HashMap<String, Object> user = (HashMap<String, Object>)users.get(key);
                        String tipoUsuario = (String)user.get("TipoUsuario");

                        //Filtro los usuarios de tipo estilista

                        if (tipoUsuario != null && tipoUsuario.equals("Estilista")){
                            long centroEstilista = (long)user.get("centro_id");

                            //Filtro los estilistas cuyo centro es el que estoy mostrando
                            //El valor 0 implica mostrar todos los estilistas
                            if(centroEstilista == centro_id || centro_id == 0l) {
                                Log.d("promos", "esilistad" + user);
                                Log.d("promos", "esilista_id" + user.get("User_id"));
                                employees.add(new Employee(
                                        (String) user.get("Name"),
                                        (String) user.get("Descripcion"),
                                        (long) user.get("Imagen"),
                                        key
                                ));
                            }
                        }
                    }
                    adapter = new EmployeeAdapter(employees, new EmployeeAdapter.OnItemClickListener() {
                        @Override
                        public void onItemClick(Employee employee) {
                            if(centro_id == 0)
                                return;

                            Intent i = new Intent(PersonalActivity.this, CitaActivity.class);
                            i.putExtra("estilistaId", employee.getUserId());
                            startActivity(i);
                        }
                    });
                    recyclerView.setAdapter(adapter);
                }
            }
        });
    }
}
