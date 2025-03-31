package com.example.oasis;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.activity.ComponentActivity;
import androidx.activity.EdgeToEdge;

public class Principal extends ComponentActivity {

    ImageButton IrATienda;
    ImageButton IrADatos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_principal);

        IrATienda = findViewById(R.id.IrATienda);
        IrADatos = findViewById(R.id.IrADatos);

        IrATienda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        IrATienda();
                    }
                }).start();
            }
        });

        IrADatos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        IrADatosATiempoRealOHistoricos();
                    }
                }).start();
            }
        });
    }

    public void IrATienda(){
        Intent intent = new Intent(Principal.this, Categorias.class);
        startActivity(intent);
    }

    public void IrADatosATiempoRealOHistoricos(){
        Intent intent = new Intent(Principal.this, DatosATiempoRealOHistoricos.class);
        startActivity(intent);
    }
}

