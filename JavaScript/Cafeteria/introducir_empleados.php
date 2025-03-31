<html>
<head>
    <title>Cafetería</title>
    <link rel="stylesheet" type="text/css" href="css/introducir.css">
    <script type="text/javascript" src="js/funciones.js"></script>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js">
    </script>
</head>
</html>
<form name="introPro" id="introPro" method="post" action="php/introducir_empleados.php" enctype="multipart/form-data">
        <p>Empleado: <input type="text" name="empleado" id="empleado" required /></p>
        <p>DNI: <input type="number" name="dni" id="dni" required /></p>
        <p>Foto: <input type="file" name="foto_empleado" id="foto_empleado" /></p>
        <p>
            <button type="submit" name="introducir_empleado" id="introducir_empleado">Introducir Empleado</button>
            <button type="reset" name="borrar_empleado" id="borrar_empleado">Borrar</button>
        </p>
    </form>

<?php
include("conecta.php");

if (isset($_POST['empleado']) && isset($_POST['dni'])) {
    $empleado = $_POST['empleado'];
    $dni = $_POST['dni'];
    $destino = '';

    if (isset($_FILES['foto_empleado']) && $_FILES['foto_empleado']['error'] === UPLOAD_ERR_OK) {
        $origen = $_FILES['foto_empleado']['tmp_name'];
        $destino = basename($_FILES['foto_empleado']['name']);
        if (!move_uploaded_file($origen, "../img/" . $destino)) {
            echo "Error al mover la imagen del producto.";
            exit;
        }
    }

    $sql = "INSERT INTO 2024_25_empleadoscafeteria (empleado, dni, foto) VALUES (?, ?, ?)";
    $stmt = $mysqli->prepare($sql);
    $stmt->bind_param("sid", $empleado, $dni, $destino);

    if ($stmt->execute()) {
        echo "Empleado introducido correctamente.";
    } else {
        echo "Error al introducir el empleado: " . $stmt->error;
    }
    $stmt->close();
}
?>

