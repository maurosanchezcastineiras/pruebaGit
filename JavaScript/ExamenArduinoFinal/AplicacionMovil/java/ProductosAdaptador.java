package com.example.oasis;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import java.util.HashMap;
import java.util.List;

public class ProductosAdaptador extends BaseExpandableListAdapter {

    private Context context;
    private List<String> categorias;
    private HashMap<String, List<Producto>> productos;

    public ProductosAdaptador(Context context, List<String> categorias, HashMap<String, List<Producto>> productos) {
        this.context = context;
        this.categorias = categorias;
        this.productos = productos;
    }

    @Override
    public int getGroupCount() {
        return categorias.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        String categoria = categorias.get(groupPosition);
        return productos.get(categoria).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return categorias.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        String categoria = categorias.get(groupPosition);
        return productos.get(categoria).get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.categoria_item, parent, false);
        }
        String categoria = (String) getGroup(groupPosition);
        TextView textView = convertView.findViewById(R.id.categoria);
        textView.setText(categoria);
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView,ViewGroup parent) {
        Producto producto = (Producto) getChild(groupPosition, childPosition);
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.producto_item, parent, false);
        }
        TextView nombre = convertView.findViewById(R.id.nombreProducto);
        TextView precio = convertView.findViewById(R.id.precioProducto);
        nombre.setText(producto.getNombre());
        precio.setText(producto.getPrecio()+"€");
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}