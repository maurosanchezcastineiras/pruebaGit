/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.mongodbtiempo;

/**
 *
 * @author aleja
 */
public class Coruna extends Municipio {
    public Coruna(String nombre, int poblacion, String capital, int altitud) {
        super(nombre, poblacion, "Coruna", capital, altitud);
    }

    @Override
    public String toString() {
        return super.toString() + " (Provincia: Coruna)";
    }
}
