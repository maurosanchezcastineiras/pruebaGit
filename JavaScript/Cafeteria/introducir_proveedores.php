<html>
<head>
    <title>Cafetería</title>
    <link rel="stylesheet" type="text/css" href="css/introducir.css">
    <script type="text/javascript" src="js/funciones.js"></script>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js">
    </script>
</head>
</html>
<form method="post" action="php/introducir_proveedores.php" enctype="multipart/form-data">
        <p>Proveedor: <input type="text" name="proveedor" id="proveedor" required /></p>
        <p>Foto: <input type="file" name="foto_proveedor" id="foto_proveedor" /></p>
        <p>
            <button type="submit" name="introducir_proveedor" id="introducir_proveedor">Introducir Empleado</button>
            <button type="reset" name="borrar_proveedor" id="borrar_proveedor">Borrar</button>
        </p>
    </form>

<?php
include("conecta.php");

if (isset($_POST['proveedor'])) {
    $proveedor = $_POST['proveedor'];
    $destino = '';

    if (isset($_FILES['foto_proveedor']) && $_FILES['foto_proveedor']['error'] === UPLOAD_ERR_OK) {
        $origen = $_FILES['foto_proveedor']['tmp_name'];
        $destino = basename($_FILES['foto_proveedor']['name']);
        if (!move_uploaded_file($origen, "../img/" . $destino)) {
            echo "Error al mover la imagen del producto.";
            exit;
        }
    }

    $sql = "INSERT INTO 2024_25_proveedorescafeteria (nombre, foto) VALUES (?, ?)";
    $stmt = $mysqli->prepare($sql);
    $stmt->bind_param("si", $proveedor, $destino);

    if ($stmt->execute()) {
        echo "Producto introducido correctamente.";
    } else {
        echo "Error al introducir el producto: " . $stmt->error;
    }
    $stmt->close();
}
?>

