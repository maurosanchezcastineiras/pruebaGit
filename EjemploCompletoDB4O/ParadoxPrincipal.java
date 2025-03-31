/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package db4ojorge;

import java.io.IOException;
import java.sql.SQLException;

/**
 *
 * @author aleja
 */
public class ParadoxPrincipal {

    public ParadoxPrincipal() throws SQLException, IOException {
        ParadoxMetodos paradox = new ParadoxMetodos();
        //paradox.crearTabla("cliente", "jdbc:paradox:///db/");
        //paradox.borrarTabla("prueba1");
        String rutaAppData = System.getenv("AppData");
        String rutaArchivoPX = rutaAppData + "\\softgenius\\px";
        //paradox.insertarEnTabla(rutaArchivoPX, "configuracion", "ConfiguracionID, color, idioma, formas", "1, 'Profesor4', 'hola', 'que tal'");
        //System.out.println(rutaArchivoPX);
        //paradox.leerTabla("paises", rutaArchivoPX);
        //paradox.modificarTabla("prueba1", "nueva_columna", "VARCHAR(255)");
        //paradox.crearTablaCopia("CREATE TABLE prueba1COPIA AS SELECT * FROM prueba1");
        //paradox.actualizarTabla("prueba1COPIA", "profesor", "'Juan'");
        //paradox.DB4OtoPARADOX("cliente");
        //paradox.mostrarDatosDB4O("paises");
        //paradox.crearBaseDeDatos("paises");
        paradox.SQLtoPARADOX("bbdd_config_softgenius");
        
        
        //paradox.SQLtoPARADOX("bbdd_config_softgenius");
    }

    public static void main(String[] args) throws SQLException, IOException {
        ParadoxPrincipal jb4o = new ParadoxPrincipal();
    }
}
