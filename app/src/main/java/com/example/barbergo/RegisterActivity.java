package com.example.barbergo;

import static kotlin.random.RandomKt.Random;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;



import android.os.Bundle;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Random;

public class RegisterActivity extends AppCompatActivity {

    private EditText userName;
    private EditText userSurnames;
    private EditText userPassword;
    private EditText userPassword2;
    private EditText userEmail;
    private EditText userPhone;
    private Button botonRegistro;
    private CheckBox checkBox1;

    private FirebaseDatabase databaseInstance;
    private DatabaseReference databaseRef;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        databaseInstance = FirebaseDatabase.getInstance();
        databaseRef = databaseInstance.getReference();

        userName = (EditText) findViewById(R.id.userName);
        userSurnames = (EditText) findViewById(R.id.userSurnames);
        userPassword = (EditText) findViewById(R.id.userPassword);
        userPassword2 = (EditText) findViewById(R.id.userPassword2);
        userEmail = (EditText) findViewById(R.id.userPhone);
        userPhone = (EditText) findViewById(R.id.userPhone);
        botonRegistro = (Button) findViewById(R.id.botonRegistro);
        checkBox1 = (CheckBox) findViewById(R.id.checkBox1);

        botonRegistro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if (userName.getText().toString().isEmpty()) {
                    Toast.makeText(RegisterActivity.this, "Todos los campos son obligatorios.",
                            Toast.LENGTH_SHORT).show();
                }
                if (userSurnames.getText().toString().isEmpty()) {
                    Toast.makeText(RegisterActivity.this, "Todos los campos son obligatorios.",
                            Toast.LENGTH_SHORT).show();
                }
                if (userEmail.getText().toString().isEmpty()) {
                    Toast.makeText(RegisterActivity.this, "Todos los campos son obligatorios.",
                            Toast.LENGTH_SHORT).show();
                }
                if (userPhone.getText().toString().isEmpty()) {
                    Toast.makeText(RegisterActivity.this, "Todos los campos son obligatorios.",
                            Toast.LENGTH_SHORT).show();
                }

                if (userPassword.getText().toString().isEmpty()) {
                    Toast.makeText(RegisterActivity.this, "Todos los campos son obligatorios.",
                            Toast.LENGTH_SHORT).show();
                }
                if (userPassword2.getText().toString().isEmpty()) {
                    Toast.makeText(RegisterActivity.this, "Todos los campos son obligatorios.",
                            Toast.LENGTH_SHORT).show();
                }
                if (!userPassword.getText().toString().equals(userPassword2.getText().toString())) {

                    Toast.makeText(RegisterActivity.this, "Las contraseñas no coinciden.",
                            Toast.LENGTH_SHORT).show();
                    return;
                }
                String userId = Long.toString(new Random().nextLong());
                User user = new User(
                        userName.getText().toString(),
                        userSurnames.getText().toString(),
                        userEmail.getText().toString(),
                        userPhone.getText().toString(),
                        userPassword.getText().toString(),
                        userId
                );

                databaseRef.child("Users").child(userId).setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(!task.isSuccessful()){
                            Toast.makeText(RegisterActivity.this, "Hubo un problema al registrar, por favor inténtelo de nuevo.",
                                    Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(RegisterActivity.this, "Usuario registrado con éxito",
                                    Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(RegisterActivity.this, GeneralActivity.class);
                            startActivity(intent);
                        }
                    }
                });
            }

        });
    }
}