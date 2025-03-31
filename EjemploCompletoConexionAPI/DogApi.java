/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.dogapi;

import org.json.JSONObject;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author aleja
 */

public class DogApi {
    public static ArrayList<Perro> obtenerPerros(String url) {
        // Se crea un ArrayList llamado listaPerros
        ArrayList<Perro> listaPerros = new ArrayList<>();
        try {
            // Llama al método leerJsonDeUrl() al que se le pasa el valor de url y se almacena en json
            JSONObject json = leerJsonDeUrl(url);
            // Llama a getJSONObject() al que se le pasa el elemento "message" y se almacena en razas
            JSONObject razas = json.getJSONObject("message");

            // Utiliza un bucle for para iterar sobre cada clave en el mapa 'razas'
            for (String nombre : razas.keySet()) {
                // Obtiene una imagen aleatoria de la raza usando la URL de la API 
                String imagenUrl = "https://dog.ceo/api/breed/" + nombre + "/images/random";

                // Con el BufferedImage instanciamos la variable imagen en null
                BufferedImage imagen = null;
                try {
                    // Usamos leerJsonDeUrl para obtener la URL de la imagen aleatoria
                    String imagenJson = leerJsonDeUrl(imagenUrl).getString("message");
                    // Con ImageIO.read() se lee la URL de la imagen desde la API y se almacena en imagen
                    imagen = ImageIO.read(new URL(imagenJson));
                } catch (IOException e) {
                    // Imprime un mensaje de error
                    System.out.println("Error al obtener la imagen para la raza: " + nombre);
                    e.printStackTrace();
                }

                // Se crea un ArrayList llamado listaSubrazas
                List<String> listaSubrazas = new ArrayList<>();
                // Se verifica con un bucle if si la lista de subrazas mediante la clave "nombre" no es vacía
                if (razas.getJSONArray(nombre).length() > 0) {
                    // Con un bucle for se recorre la lista de subrazas mediante la clave "nombre"
                    for (int i = 0; i < razas.getJSONArray(nombre).length(); i++) {
                        // Con add() se añade cada subraza a listaSubrazas
                        listaSubrazas.add(razas.getJSONArray(nombre).getString(i));
                    }
                }

                // Crea un objeto Perro con nombre, imagen y listaSubrazas
                Perro perro = new Perro(nombre, imagen, listaSubrazas);
                // Añade con add() el valor de perro a listaPerros
                listaPerros.add(perro);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        // Devuelve el valor de listaPerros
        return listaPerros;
    }

    // Método para leer la respuesta JSON de una URL
    public static JSONObject leerJsonDeUrl(String url) throws IOException {
        // Abre una conexión a la URL proporcionada y obtiene un flujo de entrada (InputStream) desde esa URL.
        InputStream is = new URL(url).openStream();
        // Crea un BufferedReader para leer el contenido del InputStream y usa un InputStreamReader para 
        // convertir el flujo de bytes en un flujo de caracteres
        try (BufferedReader rd = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8))) {
            // Crea un StringBuilder que se utilizará para ir construyendo la cadena de texto a medida que se lee el contenido
            StringBuilder sb = new StringBuilder();
            // Declara la variable cp
            int cp;
            // Utiliza el método read() que devuelve 
            // el valor Unicode del carácter leído o -1 
            // cuando ya no hay más datos y lo almacena 
            // en la variable cp almacena el valor 
            // Unicode de cada carácter leído.
            while ((cp = rd.read()) != -1) {
                // Convierte el valor Unicode en un carácter y lo añade al StringBuilder mediante append()
                sb.append((char) cp);
            }
            // Convierte la cadena en un objeto JSON y devuelve este JSONObject
            return new JSONObject(sb.toString());
        }
    }
}
