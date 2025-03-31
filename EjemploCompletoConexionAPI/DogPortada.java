/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.dogapi;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Random;

/**
 *
 * @author aleja
 */

public class DogPortada extends JFrame {
    private JLabel Imagen;
    private JLabel Nombre;
    private JButton btnAcceder;
    private ArrayList<Perro> listaPerro;

    public DogPortada() {
        initComponents();
        // Llama a obtenerPerros para obtener la lista de razas y las imágenes de la API
        listaPerro = DogApi.obtenerPerros("https://dog.ceo/api/breeds/list/all");
        // Llama al método mostrarPerroAleatorio()
        mostrarPerroAleatorio();
    }

    private void initComponents() {
        Imagen = new JLabel();
        Nombre = new JLabel();
        btnAcceder = new JButton("Acceder a lista de razas");

        setLayout(null);
        Imagen.setBounds(50, 50, 300, 300);
        Nombre.setBounds(50, 360, 300, 30);
        btnAcceder.setBounds(100, 420, 200, 30);

        add(Imagen);
        add(Nombre);
        add(btnAcceder);

        setSize(400, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Centra la ventana en la pantalla

        btnAcceder.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DogPortada2 pantallaSecundaria = new DogPortada2(listaPerro);
                pantallaSecundaria.setVisible(true);
                dispose(); 
            }
        });
    }

    private void mostrarPerroAleatorio() {
        // Creo una nueva instancia de random
        Random random = new Random();
        // Genera un índice aleatorio entre 0 y el tamaño de 
        // la lista de perros mediante nextInt() y lo almacena en la variable index
        int index = random.nextInt(listaPerro.size());
        // Obtiene el perro seleccionado desde perroAleatorio mediante get(index)
        Perro perroAleatorio = listaPerro.get(index);
        // Establece el nombre del perro aleatorio en un campo de texto mediante setText()
        Nombre.setText(perroAleatorio.getNombre());
        // Con un bucle if se verifica si el perro tiene una imagen
        if (perroAleatorio.getImagen() != null) {
            // Redimensiona la imagen mediante getScaledInstance() antes de mostrarla
            Image imagenModificada = perroAleatorio.getImagen().getScaledInstance(200, 200, Image.SCALE_SMOOTH);
            // Establece la imagen modificada en el JLabel mediante setIcon()
            Imagen.setIcon(new ImageIcon(imagenModificada));
        } else {
            // Muestra un mensaje indicando que no hay imagen disponible mediante setText()
            Imagen.setText("Imagen no disponible");
        }
    }

    public static void main(String[] args) {
        java.awt.EventQueue.invokeLater(() -> new DogPortada().setVisible(true));
    }
}
