package com.example.barbergo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.view.View;
import android.widget.Button;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputLayout;

public class FeedbackActivity extends AppCompatActivity {
    private TextView tview3;
    private TextView tview2;
    private ImageView imageView5;
    private Button botonLocal1;
    private Button botonLocal2;
    private TextInputLayout ratingBar_changes;
    private TextInputLayout ratingbar_changes2;
    private RatingBar ratingBar;
    private RatingBar ratingBar2;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);

        botonLocal1=(Button)findViewById(R.id.botonLocal1);
        botonLocal2=(Button)findViewById(R.id.botonLocal2);

        botonLocal1.setOnClickListener(new View.OnClickListener(){
            @Override

            public void onClick (View view){
                Intent intent = new Intent(FeedbackActivity.this, PersonalActivity.class);
                intent.putExtra("centroId", 11l);
                startActivity(intent);

            }
        });

        botonLocal2.setOnClickListener(new View.OnClickListener(){
            @Override

            public void onClick (View view){
                Intent intent = new Intent(FeedbackActivity.this, PersonalActivity.class);
                intent.putExtra("centroId", 22l);
                startActivity(intent);

            }
        });

    }
}