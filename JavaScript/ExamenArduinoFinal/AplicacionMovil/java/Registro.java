package com.example.oasis;

import static java.sql.DriverManager.getConnection;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.sql.Connection;
import java.sql.DriverManager;

import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.util.HashMap;
import java.util.Map;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class Registro extends AppCompatActivity {

    EditText correo;
    EditText contrasenaU;
    Button Registrate;
    Button vuelveAInicioSesion;
    FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_registro);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        correo = findViewById(R.id.correo);
        contrasenaU = findViewById(R.id.contrasena);
        Registrate = findViewById(R.id.registrate);
        vuelveAInicioSesion = findViewById(R.id.iniciaSesion);
        db = FirebaseFirestore.getInstance();

        Registrate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registrarUsuario();
            }
        });

        vuelveAInicioSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                irAInicioSesion();
            }
        });
    }

    public void irAInicioSesion() {
        Intent intent = new Intent(Registro.this, InicioDeSesion.class);
        startActivity(intent);
    }

    private void registrarUsuario() {
        String email = correo.getText().toString().trim();
        String password = contrasenaU.getText().toString().trim();

        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Completa todos los campos!!!!", Toast.LENGTH_SHORT).show();
            return;
        }
        Map<String, Object> usuario = new HashMap<>();
        usuario.put("usuario", email);
        usuario.put("contrasena", password);
        usuario.put("jardin_asociado", null);
        db.collection("clientes").add(usuario)
                .addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentReference> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(Registro.this, "Te has registrado sabiamente", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(Registro.this, InicioDeSesion.class);
                            startActivity(intent);
                        } else {
                            Toast.makeText(Registro.this, "Te has registrado mal", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}