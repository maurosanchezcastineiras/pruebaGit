/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.tienda_videojuegos;

import java.awt.FlowLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Federico
 */
public class Fichajes extends JFrame{
    
    private JTable tablaFichar;
    private JPanel panelFichaje;
    private JScrollPane barra;
    
    public Fichajes(){
        initComponents();
    }

    private void initComponents() {
        this.setSize(877, 655);
        this.panelFichaje = new JPanel();
        this.panelFichaje.setSize(877, 655);
        this.setLayout(new FlowLayout());
        
        
        
        
        DefaultTableModel modelo = new DefaultTableModel();
        
        
        modelo.addColumn("empleado_id");
        modelo.addColumn("fecha_entrada");
        modelo.addColumn("fecha_salida");
        modelo.addColumn("comentarios");
        
        this.tablaFichar = new JTable();
        this.tablaFichar = new JTable(modelo);
        
        this.barra = new JScrollPane();
        this.barra = new JScrollPane(tablaFichar);
        
        //metodo para mostrar los fichejes de los empleados
        new ConsultasTablaStock().obtenerDatosTablaFichaje(modelo);
        
        this.panelFichaje.add(this.barra);
        this.add(this.panelFichaje);
        this.setVisible(true);
//        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    
    
    
    
    
}
