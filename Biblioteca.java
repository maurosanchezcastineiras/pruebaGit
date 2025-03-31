/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.biblioteca3;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 *
 * @author aleja
 */

public class Biblioteca extends JFrame {
    
    public Biblioteca() {
        setTitle("Biblioteca");
        setSize(600, 500);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        
        ImageIcon logo = new ImageIcon("img\\logo.png"); 
        setIconImage(logo.getImage());
        
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        
        ImageIcon imagen = new ImageIcon("img\\MSC.png"); 
        Image imagenModificada = imagen.getImage().getScaledInstance(200, 200, Image.SCALE_SMOOTH); 
        JLabel imagenFinal = new JLabel(new ImageIcon(imagenModificada));
        imagenFinal.setHorizontalAlignment(SwingConstants.CENTER); 
        panel.add(imagenFinal, BorderLayout.NORTH);
        
        
        JLabel bienvenida = new JLabel("¡Bienvenido a la Biblioteca MSC!", SwingConstants.CENTER);
        bienvenida.setFont(new Font("Serif", Font.ITALIC, 24)); 
        bienvenida.setForeground(new Color(34, 139, 34)); 
        bienvenida.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, new Color(0, 128, 255))); // Borde azul
        bienvenida.setBackground(new Color(255, 255, 255, 200));
        bienvenida.setOpaque(true); 
        panel.add(bienvenida, BorderLayout.CENTER);
        
        JPanel panelBotones = new JPanel();
        panelBotones.setLayout(new FlowLayout());
        
        JButton botonLibros = new JButton("Gestionar Libros");
        botonLibros.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new GestionLibros().setVisible(true);
            }
        });
        panelBotones.add(botonLibros);
        
        JButton botonSocios = new JButton("Gestionar Socios");
        botonSocios.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new GestionSocios().setVisible(true);
            }
        });
        panelBotones.add(botonSocios);
        
        JButton botonPrestamos = new JButton("Gestionar Préstamos");
        botonPrestamos.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new GestionPrestamos().setVisible(true);
            }
        });
        panelBotones.add(botonPrestamos);
        
        panel.add(panelBotones, BorderLayout.SOUTH);
        add(panel);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Biblioteca biblioteca = new Biblioteca();
            biblioteca.setVisible(true);
        });
    }
}
