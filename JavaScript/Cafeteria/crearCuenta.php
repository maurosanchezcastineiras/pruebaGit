<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="../css/crearCuenta.css">
    <title>Crear Cuenta</title>
</head>
<body>
    <form action="crearCuenta.php" method="post">
        <h1>Crear Cuenta</h1>
        <p>Nombre<input type="text" name="nombre" id="nom"/></p>
        <p>Apellido<input type="text" name="apellido" id="ape"/></p>
        <p>Correo Electrónico<input type="text" name="correo" id="correo"/></p>
        <p>DNI<input type="text" name="dni" id="dni"/></p>
        <p>Teléfono<input type="text" name="tlf" id= "tlf"/></p>
        <p>Clave<input type="password" name="clave" id="clave"/></p>

        <button type="submit" name="iniciar" id="iniciar">CREAR CUENTA</button>
        <a href="iniciarSesion.php">Ya tienes una cuenta creada? Inicia sesión ya!!</a>
    </form>
</body>
</html>
<?php
include 'conecta.php';

if ($_SERVER["REQUEST_METHOD"] == "POST") {
    $nombre = $_POST["nombre"];
    $apellido = $_POST["apellido"];
    $correo = $_POST["correo"];
    $dni = $_POST["dni"];
    $tlf = $_POST["tlf"];
    $clave = $_POST["clave"];

    // Verifica que $clave no esté vacío antes de hashear
    if (!empty($clave)) {
        $hash = password_hash($clave, PASSWORD_DEFAULT);
    } else {
        die("La contraseña está vacía.");
    }

    // Verificar si el hash se generó correctamente
    if (!$hash) {
        die("Error al generar el hash de la contraseña.");
    }

    // Usando una consulta preparada para mayor seguridad
    $stmt = $mysqli->prepare("INSERT INTO 2024_25_clientescafeteria (nombre, apellidos, correo, DNI, telefono, clave) VALUES (?, ?, ?, ?, ?, ?)");
    
    if (!$stmt) {
        die("Error en la preparación de la consulta: " . $mysqli->error);
    }

    $stmt->bind_param("ssssss", $nombre, $apellido, $correo, $dni, $tlf, $hash);

    // Ejecutar la consulta e informar del resultado
    if ($stmt->execute()) {
        echo "Registro insertado correctamente";
    } else {
        echo "Error al insertar el registro: " . $stmt->error;
    }

    // Cerrar la declaración y la conexión
    $stmt->close();
    $mysqli->close();
}
?>

