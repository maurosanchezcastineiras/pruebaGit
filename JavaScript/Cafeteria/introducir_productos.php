<html>
<head>
    <title>Cafetería</title>
    <link rel="stylesheet" type="text/css" href="css/introducir.css">
    <script type="text/javascript" src="js/funciones.js"></script>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js">
    </script>
</head>
</html>
<form method="post" action="php/introducir_productos.php" enctype="multipart/form-data">
        <p>Categoria: <input type="text" name="categoria" id="categoria" required /></p>
        <p>Producto: <input type="text" name="producto" id="producto" required /></p>
        <p>Precio: <input type="text" name="precio" id="precio" required /></p>
        <p>Foto: <input type="file" name="foto_producto" id="foto_producto" /></p>
        <p>
            <button type="submit" name="introducir_producto" id="introducir_producto">Introducir Producto</button>
            <button type="reset" name="borrar_producto" id="borrar_producto">Borrar</button>
        </p>
    </form>

<?php
include("conecta.php");

if (isset($_POST['categoria']) && isset($_POST['producto']) && isset($_POST['precio'])) {
    $categoria = $_POST['categoria'];
    $producto = $_POST['producto'];
    $precio = (float) $_POST['precio'];
    $destino = '';

    if (isset($_FILES['foto_producto']) && $_FILES['foto_producto']['error'] === UPLOAD_ERR_OK) {
        $origen = $_FILES['foto_producto']['tmp_name'];
        $destino = basename($_FILES['foto_producto']['name']);
        if (!move_uploaded_file($origen, "../img/" . $destino)) {
            echo "Error al mover la imagen del producto.";
            exit;
        }
    }

    $sql = "INSERT INTO 2024_25_prodcafeteria (categoria, producto, precio, foto) VALUES (?, ?, ?, ?)";
    $stmt = $mysqli->prepare($sql);
    $stmt->bind_param("sids", $categoria, $producto, $precio, $destino);

    if ($stmt->execute()) {
        echo "Producto introducido correctamente.";
    } else {
        echo "Error al introducir el producto: " . $stmt->error;
    }
    $stmt->close();
}
?>



