/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.jardines;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 *
 * @author aleja
 */
public class SeleccionHistoricos extends JFrame {

    public SeleccionHistoricos() {
        setTitle("Jardín");
        setSize(400, 200);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(2, 1, 10, 10));

        JButton botonJardin1 = new JButton("Los datos del Jardín 1");
        botonJardin1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new FirebaseHistoricos(1).setVisible(true); 
                dispose(); 
            }
        });

        JButton botonJardin2 = new JButton("Los datos del Jardín 2");
        botonJardin2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new FirebaseHistoricos(2).setVisible(true); 
                dispose(); 
            }
        });
        panel.add(botonJardin1);
        panel.add(botonJardin2);

        add(panel);
    }
}
