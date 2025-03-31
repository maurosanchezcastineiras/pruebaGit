<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="../css/inicioSesion.css">
    <title>Inicio de sesion</title>
</head>

<body>
    <form action="iniciarSesion.php" method="post">
        <h1>Inicio Sesión</h1>
        <p>Correo Electrónico<input type="text" name="correo_electronico" id="correo" /></p>
        <p>DNI<input type="text" name="dni" id="dni" /></p>
        <p>Contraseña<input type="password" name="clave" id="clave" /></p>

        <button type="submit" name="iniciar" id="iniciar">INICIAR SESIÓN</button>
        <a href="crearCuenta.php">Todavía no tienes una cuenta creada? Créate una ya!!</a>
    </form>
<?php

include 'conecta.php';

if ($_SERVER["REQUEST_METHOD"] == "POST") {
    $correo = $_POST["correo_electronico"];
    $correo = $_POST["dni"];
    $clave = $_POST["clave"];


    $sql = "SELECT clave, DNI FROM 2024_25_clientescafeteria WHERE correo = ?";
    $stmt = $mysqli->prepare($sql);
    $stmt->bind_param("s", $correo);
    $stmt->execute();
    $result = $stmt->get_result();

    if ($result->num_rows > 0) {
        $inicioSesion = false;
        while ($row = $result->fetch_assoc()) {
            if (password_verify($dni, $row['DNI']) && password_verify($clave, $row['clave'])) {
                $inicioSesion = true;
                header("Location: index2.php");
                exit; 
            }
        }
        
        if (!$inicioSesion) {
            echo "Los datos son incorrectos";
        }
    } else {
        echo "Error al comprobar el registro. " . $mysqli->error;
    }    
}
?>
</body>

</html>