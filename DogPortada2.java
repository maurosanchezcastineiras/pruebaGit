/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.dogapi;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Image;
import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

/**
 *
 * @author aleja
 */
public class DogPortada2 extends JFrame {

    // Declara un JList llamada listaRazas
    private JList<String> listaRazas;
    // Declara un JLabel Imagen 
    private JLabel Imagen;
    // Declara un JLabel Nombre
    private JLabel Nombre;
    // Declara un JTextArea Subrazas
    private JTextArea Subrazas;
    // Declara un ArrayList listaPerros
    private ArrayList<Perro> listaPerros;

    public DogPortada2(ArrayList<Perro> listaPerros) {
        this.listaPerros = listaPerros;
        initComponents();
        cargarListaRazas();
    }

    private void initComponents() {
        listaRazas = new JList<>();
        Imagen = new JLabel();
        Nombre = new JLabel("", SwingConstants.CENTER); 
        Subrazas = new JTextArea(3, 20);

        JScrollPane scrollLista = new JScrollPane(listaRazas);
        JScrollPane scrollSubrazas = new JScrollPane(Subrazas);
        
        setLayout(new BorderLayout(15, 15)); 

        JPanel panelIzquierdo = new JPanel(new BorderLayout());
        panelIzquierdo.add(new JLabel("Lista de Razas", SwingConstants.CENTER), BorderLayout.NORTH);
        panelIzquierdo.add(scrollLista, BorderLayout.CENTER); 
        add(panelIzquierdo, BorderLayout.WEST);

        JPanel panelDerecho = new JPanel();
        panelDerecho.setLayout(new BoxLayout(panelDerecho, BoxLayout.Y_AXIS)); 

        Imagen.setAlignmentX(Component.CENTER_ALIGNMENT);
        panelDerecho.add(Imagen);
        panelDerecho.add(Box.createRigidArea(new Dimension(0, 10))); 

        Nombre.setAlignmentX(Component.CENTER_ALIGNMENT);
        panelDerecho.add(Nombre);
        panelDerecho.add(Box.createRigidArea(new Dimension(0, 10)));

        scrollSubrazas.setMaximumSize(new Dimension(200, 100)); 
        panelDerecho.add(scrollSubrazas);
        
        add(panelDerecho, BorderLayout.CENTER);

        setSize(600, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        listaRazas.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 1) {
                    mostrarDetallesPerro(listaRazas.getSelectedIndex());
                }
            }
        });
    }

    private void cargarListaRazas() {
        // Crea un array de Strings para almacenar los nombres de las razas
        String[] nombres = new String[listaPerros.size()];
        // Con un bucle for itera sobre la lista de perros
        for (int i = 0; i < listaPerros.size(); i++) {
            // Asigna cada nombre de raza al array nombres
            nombres[i] = listaPerros.get(i).getNombre();
        }
        // Muestra la lista de nombres en el JList
        listaRazas.setListData(nombres);
    }

    private void mostrarDetallesPerro(int index) {
        // Obtiene el perro seleccionado de la lista mediante get(index)
        Perro perroSeleccionado = listaPerros.get(index);
        // Con un bucle if se verifica si el perro tiene una imagen
        if (perroSeleccionado.getImagen() != null) {
            // Redimensiona la imagen antes de mostrarla mediante getScaledInstance()
            Image imagenModificada = perroSeleccionado.getImagen().getScaledInstance(200, 200, Image.SCALE_SMOOTH);
            // Asigna la imagen modificada al JLabel mediante setIcon()
            Imagen.setIcon(new ImageIcon(imagenModificada));
        } else {
            // Muestra un mensaje indicando que no hay imagen disponible
            Imagen.setText("Imagen no disponible");
        }

        // Asigna el nombre del perro al JLabel mediante setText()
        Nombre.setText(perroSeleccionado.getNombre());

        // Con un bucle if verifica si el perro tiene subrazas mediante isEmpty()
        if (perroSeleccionado.getSubrazas().isEmpty()) {
            // Muestra un mensaje mediante setText() indicando que no tiene subrazas
            Subrazas.setText("No tiene subrazas");
        } else {
            // Si tiene subraza, las muestra mediante getSubrazas() y la almacena en subrazasTexto
            String subrazasTexto = String.join("\n", perroSeleccionado.getSubrazas());
            // Muestra la raza mediante setText()
            Subrazas.setText(subrazasTexto);
        }
    }
}
