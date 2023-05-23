package com.example.barbergo;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class CitaActivity extends AppCompatActivity {

    private Calendar selectedDate;
    private TimePicker timePicker;
    private Button botonCita;
    private Button botonEstilista;
    private String estilistaId;

    private List<Cita> citasEstilista;

    private FirebaseDatabase databaseInstance;
    private DatabaseReference databaseRef;
    private FirebaseUser firebaseUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_citas);

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        databaseInstance = FirebaseDatabase.getInstance();
        databaseRef = databaseInstance.getReference();

        Bundle bundle = getIntent().getExtras();
        estilistaId = bundle.getString("estilistaId");

        CalendarView calendarView = findViewById(R.id.calendar_view1);
        timePicker = findViewById(R.id.time_picker);
        botonCita = findViewById(R.id.BotonCita);
        botonEstilista = findViewById(R.id.botonEstilista);
        selectedDate = new GregorianCalendar();

        // Configuramos el cambio de fecha del calendario
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
                selectedDate.set(year, month, dayOfMonth);
            }
        });

        // Configuramos el botón de envío
        botonCita.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int hour = timePicker.getHour();
                int minute = timePicker.getMinute();

                selectedDate.set(Calendar.HOUR_OF_DAY, hour);
                selectedDate.set(Calendar.MINUTE, minute);

                // Comprobamos la disponibilidad del estilista seleccionado
                getStylist(selectedDate.getTime().getTime());
            }
        });

    }

    private void getStylist(long selectedDate){
        if (citasEstilista != null){
            checkStylistAvailability(selectedDate);
        }
        databaseRef.child("Users").child(estilistaId).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if(!task.isSuccessful()){
                    Toast.makeText(CitaActivity.this, "Error obteniendo el estilista, asegúrese de que está " +
                                    "conectado a internet e inténtelo de nuevo.",
                            Toast.LENGTH_SHORT).show();
                    Log.e("promos", "Error getting data", task.getException());
                }
                else {
                    citasEstilista = new ArrayList<Cita>();

                    HashMap<String, Object> estilista = (HashMap<String, Object>) task.getResult().getValue();
                    if (estilista.containsKey("Citas")){
                        HashMap<String, Object> citas = (HashMap<String, Object>)estilista.get("Citas");
                        for(String key: citas.keySet()) {
                            HashMap<String, Object> cita = (HashMap<String, Object>) citas.get(key);
                            citasEstilista.add(new Cita(
                                    (String)cita.get("citaId"),
                                    (String)cita.get("clienteId"),
                                    (long)cita.get("date"),
                                    (String)cita.get("estilistaId"))
                            );
                        }
                    }
                    checkStylistAvailability(selectedDate);
                }
            }
        });
    }


    private void checkStylistAvailability(long selectedDate){
        for(Cita c: citasEstilista){
            if(c.date == selectedDate){
                Toast.makeText(CitaActivity.this, "La cita seleccionada no está disponible, por favor" +
                                "intente con otra cita",
                        Toast.LENGTH_SHORT).show();
                return;
            }
        }

        //Upload "cita"
        String cita_id = Long.toString(new Random().nextLong());
        Cita cita = new Cita(
                cita_id,
                firebaseUser.getUid(),
                selectedDate,
                estilistaId
        );
        Map<String, Object> postValues = cita.toMap();

        Map<String, Object> childUpdates = new HashMap<>();
        childUpdates.put("/Citas/" + cita_id, postValues);
        childUpdates.put("/Users/" + firebaseUser.getUid() + "/Citas/" + cita_id, postValues);
        childUpdates.put("/Users/" + estilistaId + "/Citas/" + cita_id, postValues);
        databaseRef.updateChildren(childUpdates).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Toast.makeText(CitaActivity.this, "La cita fue seleccionada con éxito",
                            Toast.LENGTH_SHORT).show();

                    Intent i = new Intent(CitaActivity.this, GeneralActivity.class);
                    startActivity(i);
                }else{
                    Toast.makeText(CitaActivity.this, "Error al coger la cita, por favor inténtelo de nuevo",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
