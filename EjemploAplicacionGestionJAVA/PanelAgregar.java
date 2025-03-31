/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.tienda_videojuegos;

import java.awt.Color;
import java.awt.Dimension;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 *
 * @author Federico
 */
public class PanelAgregar extends JPanel{
    
    private JTextField txtNombre, txtFecha, txtPrecio;
    private JButton btnInsertar, btnEliminar, btnActualizar;
    
    public PanelAgregar(){
        initComponents();
    }

    private void initComponents() {
        
//        this.setSize(600, 100);
        this.setBounds(0,300, 500, 100);
        this.setBackground(Color.GREEN);
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        
        
        this.txtNombre = new JTextField();
        this.txtNombre.setPreferredSize(new Dimension(100,15));
        
        this.txtFecha = new JTextField();
        this.txtFecha.setPreferredSize(new Dimension(100,15));
       
        this.txtPrecio = new JTextField();
        this.txtPrecio.setPreferredSize(new Dimension(100,15));
        
        this.btnInsertar = new JButton("INSERTAR");
        this.btnInsertar.setPreferredSize(new Dimension(600,25));
        
        this.btnEliminar = new JButton("ELIMINAR");
        this.btnEliminar.setPreferredSize(new Dimension(600,25));
        
        this.btnActualizar = new JButton("ACTUALIZAR");
        this.btnInsertar.setPreferredSize(new Dimension(600,25));
        
        this.add(this.txtNombre);
        this.add(this.txtFecha);
        this.add(this.txtPrecio);
        this.add(new PanelBotones());
        
        
        this.setVisible(true);
        
    }
    
}
