<html>
<head>
    <title>Cafetería</title>
    <link rel="stylesheet" type="text/css" href="css/introducir.css">
    <script type="text/javascript" src="js/funciones.js"></script>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js">
    </script>
</head>
</html>
<form method="post" action="php/introducir_tarjeta.php" enctype="multipart/form-data">
        <p>Nombre: <input type="text" name="nombre" id="nombre" required /></p>
        <p>Apellidos: <input type="text" name="apellidos" id="apellidos" required /></p>
        <p>DNI: <input type="text" name="dni" id="dni" required /></p>
        <p>Tlf: <input type="number" name="tlf" id="tlf" required /></p>
        <p>
            <button type="submit" name="introducir_tarjeta" id="introducir_tarjeta">Introducir Tarjeta</button>
            <button type="reset" name="borrar_tarjeta" id="borrar_tarjeta">Borrar</button>
        </p>
    </form>

<?php
include("conecta.php");

if (isset($_POST['nombre']) && isset($_POST['apellidos']) && isset($_POST['dni']) && isset($_POST['tlf'])) {
    $nombre = $_POST['nombre'];
    $apellidos = $_POST['apellidos'];
    $dni = $_POST['dni'];
    $tlf = $_POST['tlf'];

    $sql = "INSERT INTO 2024_25_tarjetacafeteria (nombre, apellidos, dni, telefono) VALUES (?, ?, ?, ?)";
    $stmt = $mysqli->prepare($sql);
    $stmt->bind_param("ssss", $nombre, $apellidos, $dni, $tlf);

    if ($stmt->execute()) {
        echo "Producto introducido correctamente.";
    } else {
        echo "Error al introducir el producto: " . $stmt->error;
    }
    $stmt->close();
}
?>
