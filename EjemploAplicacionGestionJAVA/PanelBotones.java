/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.tienda_videojuegos;

import java.awt.BorderLayout;
import java.awt.Dimension;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;

/**
 *
 * @author Federico
 */
public class PanelBotones extends JPanel{
    
    
    private JButton btnInsertar, btnEliminar, btnActualizar;
    
    public PanelBotones(){
        initComponents();
    }
    
    

    private void initComponents() {
        
        this.setSize(600, 100);
        this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
        
        this.btnInsertar = new JButton("INSERTAR");
        this.btnInsertar.setPreferredSize(new Dimension(600,25));

        this.btnEliminar = new JButton("ELIMINAR");
        this.btnEliminar.setPreferredSize(new Dimension(600,25));

        this.btnActualizar = new JButton("ACTUALIZAR");
        this.btnInsertar.setPreferredSize(new Dimension(600,25));
        
        this.add(this.btnInsertar);
        this.add(this.btnEliminar);
        this.add(this.btnActualizar);
        
        this.setVisible(true);
    }
    
    
    
    
}
