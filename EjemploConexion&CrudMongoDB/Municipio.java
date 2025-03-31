/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.mongodbtiempo;

/**
 *
 * @author aleja
 */
public abstract class Municipio {
    private String nombre;
    private int poblacion;
    private String provincia;
    private String capital;
    private int altitud;

    public Municipio(String nombre, int poblacion, String provincia, String capital, int altitud) {
        this.nombre = nombre;
        this.poblacion = poblacion;
        this.provincia = provincia;
        this.capital = capital;
        this.altitud = altitud;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getPoblacion() {
        return poblacion;
    }

    public void setPoblacion(int poblacion) {
        this.poblacion = poblacion;
    }

    public String getProvincia() {
        return provincia;
    }

    public void setProvincia(String provincia) {
        this.provincia = provincia;
    }

    public String getCapital() {
        return capital;
    }

    public void setCapital(String capital) {
        this.capital = capital;
    }
    
    public int getAltitud() {
        return altitud;
    }

    public void setAltitud(int altitud) {
        this.altitud = altitud;
    }

    @Override
    public String toString() {
        return "Municipio: " + nombre + ", Población: " + poblacion + ", Provincia: " + provincia + ", Capital: " + capital + ", Altitud: " + altitud;
    }
}
