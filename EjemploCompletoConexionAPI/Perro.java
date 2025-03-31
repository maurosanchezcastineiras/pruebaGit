/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.dogapi;

import java.awt.image.BufferedImage;
import java.util.List;

/**
 *
 * @author aleja
 */
public class Perro {
    private final String nombre;
    private final BufferedImage imagen;
    private final List<String> subrazas;

    public Perro(String nombre, BufferedImage imagen, List<String> subrazas) {
        this.nombre = nombre;
        this.imagen = imagen;
        this.subrazas = subrazas; 
    }

    public String getNombre() {
        return nombre;
    }

    public BufferedImage getImagen() {
        return imagen;
    }

    public List<String> getSubrazas() {
        return subrazas;
    }
}