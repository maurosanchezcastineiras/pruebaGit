package com.example.oasis;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class DetallesProducto extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalles_producto);
        TextView productoNombre = findViewById(R.id.productoNombre);
        TextView productoPrecio = findViewById(R.id.productoPrecio);

        Producto producto = (Producto) getIntent().getSerializableExtra("producto");

        if (producto != null) {
            productoNombre.setText(producto.getNombre());
            productoPrecio.setText(producto.getPrecio()+ "€");
        }
    }
}
