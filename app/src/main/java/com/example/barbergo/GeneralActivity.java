package com.example.barbergo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class GeneralActivity extends AppCompatActivity {

    public Button botonCita1;
    public Button botonOfertas;
    public Button botonMeet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_general);

        botonCita1 = (Button) findViewById(R.id.botonCita1);
        botonOfertas = (Button) findViewById(R.id.botonOfertas);
        botonMeet = (Button) findViewById(R.id.botonMeet);

    botonCita1.setOnClickListener(new View.OnClickListener(){
        @Override

        public void onClick (View view){
        Intent intent = new Intent(GeneralActivity.this, FeedbackActivity.class);
        startActivity(intent);

    }
    });

    botonOfertas.setOnClickListener(new View.OnClickListener(){
        @Override

        public void onClick (View view){
        Intent intent = new Intent(GeneralActivity.this, PromsActivity.class);
        startActivity(intent);

    }
    });

    botonMeet.setOnClickListener(new View.OnClickListener(){
        @Override

        public void onClick (View view){
        Intent intent = new Intent(GeneralActivity.this, PersonalActivity.class);
            intent.putExtra("centroId", 0l);
        startActivity(intent);

    }
    });
    }
}