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
import static javax.swing.WindowConstants.EXIT_ON_CLOSE;

/**
 *
 * @author aleja
 */
public class App extends JFrame {

    public App() {
        setTitle("OASIS SL");
        setSize(800, 500);
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

        JLabel bienvenida = new JLabel("¡Bienvenido a OASIS SL! ", SwingConstants.CENTER);
        bienvenida.setFont(new Font("Serif", Font.ITALIC, 24));
        bienvenida.setForeground(new Color(34, 139, 34));
        bienvenida.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, new Color(0, 128, 255)));
        bienvenida.setBackground(new Color(255, 255, 255, 200));
        bienvenida.setOpaque(true);
        panel.add(bienvenida, BorderLayout.CENTER);

        JPanel panelBotones = new JPanel();
        panelBotones.setLayout(new FlowLayout());

        JButton botongestiones = new JButton("Gestiones");
        botongestiones.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new Gestions().setVisible(true);
            }
        });
        panelBotones.add(botongestiones);

        JButton firebase = new JButton("Datos de los jardines");
        firebase.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new FirebaseEnTiempoReal().setVisible(true);
            }
        });
        panelBotones.add(firebase);

        JButton historicos = new JButton("Datos históricos de los jardines");
        historicos.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new SeleccionHistoricos().setVisible(true);
            }
        });
        panelBotones.add(historicos);

        panel.add(panelBotones, BorderLayout.SOUTH);
        add(panel);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            App app = new App();
            app.setVisible(true);
        });
    }
}
