package com.example.oasis;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class InicioDeSesion extends AppCompatActivity {

    EditText correo;
    EditText contrasenaU;
    Button iniciaSesion;
    Button registrate;


    FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio_de_sesion);

        correo = findViewById(R.id.correo);
        contrasenaU = findViewById(R.id.contrasena);
        iniciaSesion = findViewById(R.id.iniciaSesion);
        registrate = findViewById(R.id.registrate);

        db = FirebaseFirestore.getInstance();

        registrate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                irARegistro();
            }
        });

        iniciaSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                verificarCredenciales();
            }
        });
    }

    public void irARegistro() {
        Intent intent = new Intent(InicioDeSesion.this, Registro.class);
        startActivity(intent);
    }

    private void verificarCredenciales() {
        String usuario = correo.getText().toString().trim();
        String contrasena = contrasenaU.getText().toString().trim();

        if (usuario.isEmpty() || contrasena.isEmpty()) {
            Toast.makeText(this, "Completa todos los campos!!!!!!!!!!!", Toast.LENGTH_SHORT).show();
            return;
        }

        // coleccion clientes
        db.collection("clientes").whereEqualTo("usuario", usuario).whereEqualTo("contrasena", contrasena).get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            if (!task.getResult().isEmpty()) {
                                Intent intent = new Intent(InicioDeSesion.this, Principal.class);
                                startActivity(intent);
                                Toast.makeText(InicioDeSesion.this, "Has iniciado sesión sabiamente", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(InicioDeSesion.this, "Has iniciado sesión mal", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(InicioDeSesion.this, "Error al conectar con Firestore", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}