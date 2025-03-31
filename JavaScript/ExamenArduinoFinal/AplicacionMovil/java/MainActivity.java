package com.example.oasis;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.activity.ComponentActivity;
import androidx.activity.EdgeToEdge;

public class MainActivity extends ComponentActivity {

    ImageButton InicioDeSesion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        InicioDeSesion = findViewById(R.id.inicioDeSesion);

        InicioDeSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        IrAInicioDeSesion();
                    }
                }).start();
            }
        });
    }

    public void IrAInicioDeSesion(){
        Intent intent = new Intent(MainActivity.this, InicioDeSesion.class);
        startActivity(intent);
    }

}

