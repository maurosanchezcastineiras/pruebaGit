/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.mongodbtiempo;

/**
 *
 * @author aleja
 */
public class Pontevedra extends Municipio {
    private final int superficie;

    public Pontevedra(String nombre, int poblacion, String capital, int superficie) {
        super(nombre, poblacion, "Pontevedra", capital, 0); 
        this.superficie = superficie;
    }

    public int getSuperficie() {
        return superficie;
    }

    @Override
    public String toString() {
        return super.toString() + ", Superficie: " + superficie + " km² (Provincia: Pontevedra)";
    }
}
