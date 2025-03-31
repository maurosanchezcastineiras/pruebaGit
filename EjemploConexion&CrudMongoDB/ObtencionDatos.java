/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.mongodbtiempo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author aleja
 */
public class ObtencionDatos {

    // Este método obtiene la lista de muncipios de la API y crea un objeto municipio para cada uno y lo añade a la lista
    public static ArrayList<Municipio> obtenerMunicipios(String url, String provincia) throws IOException {
        ArrayList<Municipio> municipios = new ArrayList<>();
        JSONObject json = leerJsonDeUrl(url);
        JSONArray municipiosArray = json.getJSONArray("municipios");

        for (int i = 0; i < municipiosArray.length(); i++) {
            JSONObject municipioJson = municipiosArray.getJSONObject(i);
            String nombre = municipioJson.getString("NOMBRE");
            int poblacion = municipioJson.optInt("POBLACION_MUNI");
            int superficie = municipioJson.optInt("SUPERFICIE");
            int altitud = municipioJson.optInt("ALTITUD");
            String capital = municipioJson.optString("NOMBRE_CAPITAL");

            Municipio municipio = crearMunicipio(provincia, nombre, poblacion, superficie, capital, altitud);
            municipios.add(municipio);
        }
        return municipios;
    }
    
    // Este método crea un objeto municipio. Es llamado en obtenerMunicipios()
    private static Municipio crearMunicipio(String provincia, String nombre, int poblacion, int superficie, String capital, int altitud) {
        switch (provincia) {
            case "Coruna":
                return new Coruna(nombre, poblacion, capital, altitud);
            case "Lugo":
                return new Lugo(nombre, poblacion, altitud, capital);
            case "Ourense":
                return new Ourense(nombre, poblacion, capital, superficie);
            case "Pontevedra":
                return new Pontevedra(nombre, poblacion, capital, superficie);
        }
        return null;
    }

    // Este método se encarga de leer la url y devuelve un objeto JSONObject. Es llamado en obtenerMunicipio()
    public static JSONObject leerJsonDeUrl(String url) throws IOException {
        try (BufferedReader rd = new BufferedReader(new InputStreamReader(new URL(url).openStream(), StandardCharsets.UTF_8))) {
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = rd.readLine()) != null) {
                sb.append(line);
            }
            return new JSONObject(sb.toString());
        }

    }
}
