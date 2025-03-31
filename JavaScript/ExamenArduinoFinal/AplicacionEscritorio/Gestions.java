/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.jardines;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

/**
 *
 * @author aleja
 */
public class Gestions extends JFrame {

    public Gestions() {
        setTitle("Gestiona aquí los datos");
        setSize(800, 500);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        ImageIcon logo = new ImageIcon("C:\\Users\\aleja\\OneDrive\\Documentos\\NetBeansProjects\\pruebaGit\\Jardines\\src\\main\\java\\com\\mycompany\\jardines\\img\\logo.png");
        setIconImage(logo.getImage());

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        ImageIcon imagen = new ImageIcon("C:\\Users\\aleja\\OneDrive\\Documentos\\NetBeansProjects\\pruebaGit\\Jardines\\src\\main\\java\\com\\mycompany\\jardines\\img\\logo.png");
        Image imagenModificada = imagen.getImage().getScaledInstance(200, 200, Image.SCALE_SMOOTH);
        JLabel imagenFinal = new JLabel(new ImageIcon(imagenModificada));
        imagenFinal.setHorizontalAlignment(SwingConstants.CENTER);
        panel.add(imagenFinal, BorderLayout.NORTH);

        JPanel panelBotones = new JPanel();
        panelBotones.setLayout(new FlowLayout());

        JButton botonClientes = new JButton("Gestionar Clientes");
        botonClientes.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new GestionClientes().setVisible(true);
            }
        });
        panelBotones.add(botonClientes);

        JButton botonArticulos = new JButton("Gestionar Artículos");
        botonArticulos.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new GestionArticulos().setVisible(true);
            }
        });
        panelBotones.add(botonArticulos);
        
        JButton botonPersonal = new JButton("Gestionar Personal");
        botonPersonal.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new GestionPersonal().setVisible(true);
            }
        });
        panelBotones.add(botonPersonal);
        
        JButton botonTempertura = new JButton("Gestionar los rangos ideales de la temperatura");
        botonTempertura.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new GestionPersonal().setVisible(true);
            }
        });
        panelBotones.add(botonTempertura);
        
        JButton botonHumedad = new JButton("Gestionar los rangos ideales de la humedad");
        botonHumedad.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new GestionPersonal().setVisible(true);
            }
        });
        panelBotones.add(botonHumedad);
        
        JButton botonHumedadSuelo = new JButton("Gestionar los rangos ideales de la humedad del suelo");
        botonHumedadSuelo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new GestionPersonal().setVisible(true);
            }
        });
        panelBotones.add(botonHumedadSuelo);
        
        JButton botonAgua = new JButton("Gestionar el nivel mínimo ideal del agua");
        botonAgua.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new GestionPersonal().setVisible(true);
            }
        });
        panelBotones.add(botonAgua);

        panel.add(panelBotones, BorderLayout.SOUTH);
        add(panel);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Gestions gestiones = new Gestions();
            gestiones.setVisible(true);
        });
    }
}

