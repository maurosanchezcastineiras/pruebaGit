/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */
package com.mycompany.mongodbtiempo;

import com.mongodb.Block;
import com.mongodb.client.AggregateIterable;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Aggregates;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Projections;
import com.mongodb.client.model.Sorts;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.bson.Document;

/**
 *
 * @author aleja
 */
public class MongoDBTiempo {

    public static void main(String[] args) {
        try {
            MongoConexion conexion = new MongoConexion("galicia");
            MongoCollection<Document> provinciasColeccion = conexion.getBd().getCollection("provincias");
            MongoCollection<Document> municipiosColeccion = conexion.getBd().getCollection("municipios");

            ArrayList<Municipio> municipiosCoruna = ObtencionDatos.obtenerMunicipios("https://www.el-tiempo.net/api/json/v2/provincias/15/municipios", "Coruna");
            ArrayList<Municipio> municipiosLugo = ObtencionDatos.obtenerMunicipios("https://www.el-tiempo.net/api/json/v2/provincias/27/municipios", "Lugo");
            ArrayList<Municipio> municipiosOurense = ObtencionDatos.obtenerMunicipios("https://www.el-tiempo.net/api/json/v2/provincias/32/municipios", "Ourense");
            ArrayList<Municipio> municipiosPontevedra = ObtencionDatos.obtenerMunicipios("https://www.el-tiempo.net/api/json/v2/provincias/36/municipios", "Pontevedra");

            insertarMunicipios(municipiosColeccion, municipiosCoruna);
            insertarMunicipios(municipiosColeccion, municipiosLugo);
            insertarMunicipios(municipiosColeccion, municipiosOurense);
            insertarMunicipios(municipiosColeccion, municipiosPontevedra);

            insertarProvincias(provinciasColeccion, municipiosColeccion);

            Consultas(municipiosColeccion, provinciasColeccion);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Este método se encarga de insertar los municipios con sus datos en una colección en MongoDB
    public static void insertarMunicipios(MongoCollection<Document> coleccion, ArrayList<Municipio> municipios) {
        for (Municipio municipio : municipios) {
            // Crea un documento
            Document doc = new Document()
                    .append("nombre", municipio.getNombre())
                    .append("poblacion", municipio.getPoblacion())
                    .append("provincia", municipio.getProvincia())
                    .append("capital", municipio.getCapital())
                    .append("altitud", municipio.getAltitud());

            // Ourense necesita también la superficie asi que se añade al documento
            if (municipio instanceof Ourense) {
                doc.append("superficie", ((Ourense) municipio).getSuperficie());
            }

            // Pontevedra necesita también la superficie asi que se añade al documento
            if (municipio instanceof Pontevedra) {
                doc.append("superficie", ((Pontevedra) municipio).getSuperficie());
            }

            // Inserta el documento en la colección
            coleccion.insertOne(doc);
        }
    }

    // Este método se encarga de insertar las provincias con sus datos en una colección en MongoDB
    public static void insertarProvincias(MongoCollection<Document> provinciasColeccion, MongoCollection<Document> municipiosColeccion) {
        List<String> nombresCoruna = obtenerNombresMunicipios(municipiosColeccion, "Coruna");
        List<String> nombresLugo = obtenerNombresMunicipios(municipiosColeccion, "Lugo");
        List<String> nombresOurense = obtenerNombresMunicipios(municipiosColeccion, "Ourense");
        List<String> nombresPontevedra = obtenerNombresMunicipios(municipiosColeccion, "Pontevedra");

        String capitalCoruna = obtenerCapitalMunicipio(municipiosColeccion, "Coruna");
        String capitalLugo = obtenerCapitalMunicipio(municipiosColeccion, "Lugo");
        String capitalOurense = obtenerCapitalMunicipio(municipiosColeccion, "Ourense");
        String capitalPontevedra = obtenerCapitalMunicipio(municipiosColeccion, "Pontevedra");

        provinciasColeccion.insertOne(new Document("nombre", "Coruna")
                .append("municipios", nombresCoruna)
                .append("capital", capitalCoruna));
        provinciasColeccion.insertOne(new Document("nombre", "Lugo")
                .append("municipios", nombresLugo)
                .append("capital", capitalLugo));
        provinciasColeccion.insertOne(new Document("nombre", "Ourense")
                .append("municipios", nombresOurense)
                .append("capital", capitalOurense));
        provinciasColeccion.insertOne(new Document("nombre", "Pontevedra")
                .append("municipios", nombresPontevedra)
                .append("capital", capitalPontevedra));
    }

    // Este método obtiene los nombres de las provincias. Es llamado en insertarProvincias()
    public static List<String> obtenerNombresMunicipios(MongoCollection<Document> Coleccion, String provincia) {
        List<String> nombresMunicipios = new ArrayList<>();
        List<Document> municipios = Coleccion.find(Filters.eq("provincia", provincia)).into(new ArrayList<>());
        for (Document municipio : municipios) {
            nombresMunicipios.add(municipio.getString("nombre"));
        }
        return nombresMunicipios;
    }

    // Este método obtiene las capitales de las provincias. Es llamado en insertarProvincias()
    public static String obtenerCapitalMunicipio(MongoCollection<Document> Coleccion, String provincia) {
        return Coleccion.find(Filters.eq("provincia", provincia)).first().getString("capital");
    }

    // Este método contiene las consultas a las colecciones de MongoDB. Es llamado en el main
    public static void Consultas(MongoCollection<Document> municipiosColeccion, MongoCollection<Document> provinciasColeccion) {
        // 1. Indica los municipios correspondientes a Coruña
        System.out.println("------------------------------Municipios de Coruña:");
        // Se obtienen los documentos en los que el campo provincia sea Coruna
        FindIterable<Document> consulta1 = municipiosColeccion.find(Filters.eq("provincia", "Coruna"));

        // Itera sobre los datos
        for (Document doc : consulta1) {
            // Imprime el nombre de cada municipio
            System.out.println(doc.getString("nombre"));
        }

        // 2. Indica los municipios correspondientes a Lugo con altitud menor que 300m
        System.out.println("-------------------------------Municipios en Lugo con altitud menor que 300m:");
        // Se obtienen los documentos en los que el campo provincia sea Lugo y la altitud sea menor de 300
        FindIterable<Document> consulta2 = municipiosColeccion.find(Filters.and(
                Filters.eq("provincia", "Lugo"), Filters.lt("altitud", 300)));

        // Itera sobre los datos
        for (Document doc : consulta2) {
            // Imprime el nombre de cada municipio
            System.out.println(doc.getString("nombre"));
        }

        // 3. Indica el municipio más despoblado
        System.out.println("------------------------------------El municipio más despoblado:");
        // Se obtienen todos los documentos ordenados por población de menor a mayor y selecciona el consulta1 al primer documento 
        FindIterable<Document> consulta3 = municipiosColeccion.find().sort(Sorts.ascending("poblacion")).limit(1);

        // Itera sobre los datos
        for (Document doc : consulta3) {
            // Imprime el nombre del municipio
            System.out.println(doc.getString("nombre"));
        }

        // 4. Indica los municipios con una superficie mayor a 150km2 de la provincia de Orense y Pontevedra
        System.out.println("------------------------------------Municipios con una superficie mayor a 150km2 de Ourense o Pontevedra:");

        int superficieMinima = 15000;

        // Se obtienen los documentos en los que el campo provincia sea Ourense con superficie mayor que superficieMinima 
        // o el campo provincia sea Pontevedra con superficie mayor que superficieMinima
        FindIterable<Document> consulta4 = municipiosColeccion.find(Filters.or(Filters.and(
                Filters.eq("provincia", "Ourense"), Filters.gt("superficie", superficieMinima)),
                Filters.and(Filters.eq("provincia", "Pontevedra"), Filters.gt("superficie", superficieMinima))
        ));

        // Itera sobre los datos
        for (Document doc : consulta4) {
            // Imprime el nombre de los municipios
            System.out.println(doc.getString("nombre"));
        }

        // 5. Indica el número de municipios de cada provincia gallega, incluyendo su capital
        System.out.println("--------------------------------------Número de municipios de cada provincia gallega con su capital:");

        // Se obtienen losnombres de las provincias 
        FindIterable<Document> consulta5 = provinciasColeccion.find(Filters.in(
                "nombre", Arrays.asList("Coruna", "Lugo", "Ourense", "Pontevedra")));

        // Itera sobre los datos
        for (Document provincia : consulta5) {
            // Se obtiene el nombre de la provincia
            String nombreProvincia = provincia.getString("nombre");
            // Se obtiene la capital
            String capital = provincia.getString("capital");
            // Se obtienen los municipios
            List<String> municipios = (List<String>) provincia.get("municipios");
            // Se obtienen el numero de municipios
            int numeroDeMunicipios = municipios.size();

            System.out.println("Provincia: " + nombreProvincia);
            System.out.println("Capital: " + capital);
            System.out.println("Número de municipios: " + numeroDeMunicipios);
        }

        // 6. Indica en un campo calculado el "nombre - capital" de los 5 mayores municipios de Galicia
        System.out.println("---------------------------------------Los 5 mayores municipios de Galicia (nombre - capital):");

        // Hace una agregación en la colección de municipios
        AggregateIterable<Document> consulta6 = municipiosColeccion.aggregate(Arrays.asList(
                // Ordena los municipios por población de manera descendente
                Aggregates.sort(Sorts.descending("poblacion")),
                // Selecciona los 5 mayores municipios
                Aggregates.limit(5),
                // Hace un project concatenando los campos "nombre" y "capital" de cada municipio
                Aggregates.project(Projections.fields(
                        Projections.include("nombre", "capital"),
                        Projections.computed("nombre - capital", new Document(
                                "$concat", Arrays.asList("$nombre", " - ", "$capital")))
                ))
        ));

        // Itera sobre los datos
        for (Document doc : consulta6) {
            // Imprime el nombre de los municipios y las capitales
            System.out.println(doc.getString("nombre - capital"));
        }

        // 7. Indica los 5 mayores municipios de cada provincia gallega (nombre - capital)
        System.out.println("-------------------------------------------Los 5 mayores municipios de cada provincia gallega (nombre - capital):");

        // Crea una lista con las cuatro provincias
        List<String> provincias = Arrays.asList("Coruna", "Lugo", "Ourense", "Pontevedra");

        // Itera sobre los datos
        for (String provincia : provincias) {
            // Obtiene las provincias, las ordena de mayor a menor y selecciona las cinco primeras
            FindIterable<Document> consulta7 = municipiosColeccion.find(Filters.eq(
                    "provincia", provincia)).sort(Sorts.descending("poblacion")).limit(5);
            // Imprime las provincias
            System.out.println("Provincia: " + provincia);

            // Itera sobre los datos 
            for (Document municipio : consulta7) {
                // Obtiene el nombre 
                String nombre = municipio.getString("nombre");
                // Obtiene la capital
                String capital = municipio.getString("capital");
                // Separa los datos con un guión
                String nombreCapital = nombre + " - " + capital;

                System.out.println(nombreCapital);
            }
        }

        // 8. Indica el municipio con menor y mayor altitud de Galicia
        System.out.println("-------------------------------------Municipio con menor y mayor altitud de Galicia:");

        // Obtiene los municipios ordenandolos por la altitud de menor a mayor y selecciona el primero
        FindIterable<Document> menorAltitud = municipiosColeccion.find().sort(Sorts.ascending("altitud")).limit(1);

        System.out.println("Municipio con menor altitud:");
        // Itera sobre el dato
        for (Document municipio : menorAltitud) {
            // Obtiene el nombre
            String nombre = municipio.getString("nombre");
            // Obtiene la altitud
            int altitud = municipio.getInteger("altitud");

            System.out.println(nombre + ", Altitud: " + altitud);
        }

        // Obtiene los municipios ordenandolos por la altitud de mayor a menor y selecciona el primero
        FindIterable<Document> mayorAltitud = municipiosColeccion.find().sort(Sorts.descending("altitud")).limit(1);

        System.out.println("Municipio con mayor altitud:");
        // Itera sobre el dato
        for (Document municipio : mayorAltitud) {
            // Obtiene el nombre
            String nombre = municipio.getString("nombre");
            // Obtiene la altitud
            int altitud = municipio.getInteger("altitud");

            System.out.println(nombre + ", Altitud: " + altitud);
        }
    }
}
