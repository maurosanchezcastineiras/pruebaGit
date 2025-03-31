/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.biblioteca3;

/**
 *
 * @author aleja
 */
class Libro {

    private int id;
    private String titulo;
    private int numEjemplares;
    private String editorial;
    private int numPaginas;
    private int anhoEdicion;

    public Libro(String titulo, int numEjemplares, String editorial, int numPaginas, int anhoEdicion) {
        this.titulo = titulo;
        this.numEjemplares = numEjemplares;
        this.editorial = editorial;
        this.numPaginas = numPaginas;
        this.anhoEdicion = anhoEdicion;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public int getNumEjemplares() {
        return numEjemplares;
    }

    public void setNumEjemplares(int numEjemplares) {
        this.numEjemplares = numEjemplares;
    }

    public String getEditorial() {
        return editorial;
    }

    public void setEditorial(String editorial) {
        this.editorial = editorial;
    }

    public int getNumPaginas() {
        return numPaginas;
    }

    public void setNumPaginas(int numPaginas) {
        this.numPaginas = numPaginas;
    }

    public int getAnhoEdicion() {
        return anhoEdicion;
    }

    public void setAnhoEdicion(int anhoEdicion) {
        this.anhoEdicion = anhoEdicion;
    }

    
}
