package com.example.barbergo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {

    private Button botonLogin, botonRegistro2;
    private EditText email1;
    private EditText password1;
    private TextView errorEmail;
    private TextView errorContrasena;

    private static final String TAG = "EmailPassword";
    // [START declare_auth]
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FirebaseApp.initializeApp(this);
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);

        mAuth = FirebaseAuth.getInstance();
        botonLogin=(Button)findViewById(R.id.botonLogin);
        botonRegistro2=(Button)findViewById(R.id.botonRegistro2);
        errorEmail=(TextView)findViewById(R.id.errorEmail);
        errorContrasena=(TextView)findViewById(R.id.errorContrasena);
        email1=(EditText)findViewById(R.id.email1);
        password1=(EditText)findViewById(R.id.password1);

        botonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(email1.getText().toString().isEmpty()){
                    Toast.makeText(MainActivity.this, "Email obligatorio.",
                            Toast.LENGTH_SHORT).show();
                    return;
                }
                if(password1.getText().toString().isEmpty()){
                    Toast.makeText(MainActivity.this, "Contraseña obligatoria.",
                            Toast.LENGTH_SHORT).show();
                    return;
                }

                iniciarSesion();

            }
        });

        botonRegistro2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, RegisterActivity.class);
                startActivity(intent);

            }
        });

    }

    private void iniciarSesion(){

        mAuth.signInWithEmailAndPassword(email1.getText().toString(), password1.getText().toString())
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            Intent intent = new Intent(MainActivity.this, GeneralActivity.class);
                            startActivity(intent);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithEmail:failure", task.getException());
                            Toast.makeText(MainActivity.this, "Email o contrseña incorrectos.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });

    }
}