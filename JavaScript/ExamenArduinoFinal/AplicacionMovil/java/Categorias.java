package com.example.oasis;


import android.content.Intent;
import android.os.Bundle;
import android.widget.ExpandableListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

public class Categorias extends AppCompatActivity {

    ExpandableListView expandableListView;
    List<String> categorias;
    HashMap<String, List<Producto>> productos;
    ProductosAdaptador adaptador;
    FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categorias);
        expandableListView = findViewById(R.id.expandableListView);

        db = FirebaseFirestore.getInstance();

        // se llama a cargaDatos
        cargarDatos();

        expandableListView.setOnChildClickListener((parent, v, groupPosition, childPosition, id) -> {
            Producto product = productos.get(categorias.get(groupPosition)).get(childPosition);

            Intent intent = new Intent(Categorias.this, DetallesProducto.class);
            intent.putExtra("producto", product);
            startActivity(intent);

            return true;
        });
    }

    private void cargarDatos() {
        categorias = new ArrayList<>();
        productos = new HashMap<>();
        db.collection("claseArticulos").get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            Long claseId = document.getLong("clase_id");
                            String categoria = document.getString("clase");
                            if (claseId != null && categoria != null) {
                                categorias.add(categoria);
                                // llamo a obtenerProductos pasandole claseId y categoria
                                obtenerProductos(claseId, categoria);
                            }
                        }
                    } else {
                        Toast.makeText(this, "Error al cargar categorías", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void obtenerProductos(Long claseId, String categoria) {
        db.collection("articulos").whereEqualTo("clase_asociada", claseId).get().addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        List<Producto> listaProductos = new ArrayList<>();
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            String nombre = document.getString("articulo");
                            Double precio = document.getDouble("precio");
                            if (nombre != null && precio != null) {
                                listaProductos.add(new Producto(nombre, precio));
                            }
                        }
                          // se asocia la litas con su categoria
                        productos.put(categoria, listaProductos);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                // se crea la nueva instancia de ProductosAdaptador
                                adaptador = new ProductosAdaptador(Categorias.this, categorias, productos);
                                expandableListView.setAdapter(adaptador);
                            }
                        });
                    } else {
                        Toast.makeText(this, "Error al cargar productos", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}