/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.calcularquiniela;

import java.io.File;
import java.io.IOException;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 *
 * @author aleja
 */
public class CalcularQuiniela {

    public static void main(String[] args) {
         try {
            // Crear una instancia de SAXParserFactory
            SAXParserFactory factory = SAXParserFactory.newInstance();

            // Crear una nueva instancia de SAXParser
            SAXParser saxParser = factory.newSAXParser();

            // Crear un nuevo manejador de eventos (handler)
            DefaultHandler handler = new QuinielaHandler();

            // Parsear el archivo XML
            saxParser.parse(new File("Quiniela.xml"), handler);

        } catch (ParserConfigurationException | SAXException | IOException e) {
            e.printStackTrace();
        }
    }
}
