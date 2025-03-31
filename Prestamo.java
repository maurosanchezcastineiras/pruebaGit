/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.biblioteca3;

import java.time.LocalDate;

/**
 *
 * @author aleja
 */
public class Prestamo {
    int id;
    int LibroID;
    int SocioID;
    LocalDate fechaInicio;
    LocalDate fechaFin;

    public Prestamo(int id, int LibroID, LocalDate fechaInicio, LocalDate fechaFin) {
        this.id = id;
        this.LibroID = LibroID;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getLibroID() {
        return LibroID;
    }

    public void setLibroID(int LibroID) {
        this.LibroID = LibroID;
    }

    public int getSocioID() {
        return SocioID;
    }

    public void setSocioID(int SocioID) {
        this.SocioID = SocioID;
    }

    public LocalDate getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(LocalDate fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public LocalDate getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(LocalDate fechaFin) {
        this.fechaFin = fechaFin;
    }

   
}