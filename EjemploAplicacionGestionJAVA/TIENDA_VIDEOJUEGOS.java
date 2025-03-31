/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.tienda_videojuegos;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;

/**
 *
 * @author Federico
 */
public class TIENDA_VIDEOJUEGOS extends JFrame{
    
    public TIENDA_VIDEOJUEGOS(){
        this.setTitle("ALL OF GAMES");
        this.setLayout(null);
        this.setSize(600, 600);
        this.add(new Menu_Conexion_BBDD());
        this.add(new PanelAgregar());
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable(){
            @Override
            public void run() {
               new TIENDA_VIDEOJUEGOS().setVisible(true);
            }
        });
    }
}
