/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.calcularquiniela;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 *
 * @author aleja
 */
public class QuinielaHandler extends DefaultHandler {

    boolean equipoLocal = false;
    boolean equipoVisitante = false;
    boolean golesLocal = false;
    boolean golesVisitante = false;

    int contadorPartidos = 0;
    String Local = "";
    String Visitante = "";
    String GolesLocal = "";
    String GolesVisitante = "";

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        if (qName.equalsIgnoreCase("partido")) {
            contadorPartidos++;
        } else if (qName.equalsIgnoreCase("equipoLocal")) {
            equipoLocal = true;
        } else if (qName.equalsIgnoreCase("golesLocal")) {
            golesLocal = true;
        } else if (qName.equalsIgnoreCase("equipoVisitante")) {
            equipoVisitante = true;
        } else if (qName.equalsIgnoreCase("golesVisitante")) {
            golesVisitante = true;
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        if (qName.equalsIgnoreCase("partido")) {
            System.out.println("\nPartido #" + contadorPartidos + ":");
            System.out.println("\nEquipo Local: " + Local);
            System.out.println("\nEquipo Local: " + GolesLocal);
            System.out.println("\nEquipo Visitante: " + Visitante);
            System.out.println("\nEquipo Local: " + GolesVisitante);

            Local = "";
            GolesLocal = "";
            Visitante = "";
            GolesVisitante = "";
            
        }
    }

    @Override
    public void characters(char ch[], int start, int length) throws SAXException {
        if (equipoLocal) {
            Local = new String(ch, start, length);
            equipoLocal = false;
        } else if (golesLocal) {
            GolesLocal = new String(ch, start, length);
            golesLocal = false;
        } else if (equipoVisitante) {
            Visitante = new String(ch, start, length);
            equipoVisitante = false;
        } else if (golesVisitante) {
            GolesVisitante = new String(ch, start, length);
            golesVisitante = false;
        }
    }

    @Override
    public void endDocument() throws SAXException {
        System.out.println("\nCantidad total de partidos: " + contadorPartidos);
    }
}

