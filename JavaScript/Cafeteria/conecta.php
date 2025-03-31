<?php
            // Configuración de la base de datos
            $servidor="localhost";
            $usuario="root";
            $contra="";
            $bbdd="Cafeteria";

            // Crear una conexión
            $mysqli = new mysqli($servidor, $usuario, $contra, $bbdd);
            $mysqli ->set_charset("utf8");

            // Verificar la conexión
            if ($mysqli->connect_error) {
                die("Error de conexión: " . $mysqli->connect_error);
            }
?>