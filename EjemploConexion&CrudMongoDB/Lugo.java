/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.mongodbtiempo;

/**
 *
 * @author aleja
 */
public class Lugo extends Municipio {
    public Lugo(String nombre, int poblacion, int altitud, String capital) {
        super(nombre, poblacion, "Lugo", capital, altitud);
    }

    @Override
    public String toString() {
        return super.toString() + " (Provincia: Lugo)";
    }
}
