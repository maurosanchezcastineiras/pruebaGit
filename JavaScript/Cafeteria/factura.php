<!DOCTYPE html>
<html>
<head>
    <title>Factura</title>
    <link rel="stylesheet" type="text/css" href="../css/factura.css">
    <script type="text/javascript" src="../js/funciones.js"></script>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>
</head>
<body>
    <img src="../img/img.png"><br>
    <label>C/ Ramón Cabanillas 3</label><br>
    <label>15011 A Coruña</label><br>
    <label>981 47 73 62</label><br>

    <form method="post" action="crear.php">
        <table>
            <tr>
                <th>Producto</th>
                <th>Precio</th>
                <th>Cantidad</th>
                <th>Importe</th>
            </tr>
            <?php
            $i = 0;
            $cont = 0;
            $total = 0;
            foreach ($_POST as $value) {
                if ($value != null) {
                    if ($i % 3 == 0) {
                        echo "<tr>";
                        $producto[$cont] = $value;
                        $prod = str_replace(" ", "_", $producto[$cont]); // Cambio espacios por "_"
                        echo "<td><input type='text' name='Pro_$prod' value='$producto[$cont]' readonly/></td>";
                    }
                    if ($i % 3 == 1) {
                        $precio[$cont] = $value;
                        echo "<td><input type='text' name='Precio_$prod' value='$precio[$cont]' readonly/></td>";
                    }
                    if ($i % 3 == 2) {
                        $cantidad[$cont] = $value;
                        $importe[$cont] = $precio[$cont] * $cantidad[$cont];
                        $total += $importe[$cont];
                        echo "<td><input type='text' name='Cantidad_$prod' value='$cantidad[$cont]' readonly/></td>";
                        echo "<td><input type='text' name='Importe_$prod' value='$importe[$cont]' readonly/></td>";
                        echo "</tr>";
                        $cont++;
                    }
                    $i++;
                }
            }
            ?>
        </table>

        <?php
        $fecha = date("Y-m-d");
        $cliente = "CLIENTE CONTADO";
        $base = $total / 1.10;
        $iva = $total - $base;
        $pago = "CONTADO";

        // Conexión a la base de datos
        include("conecta.php");

        // Inserción en la tabla `2024_25_facturas`
        $sql = "INSERT INTO 2024_25_facturas (FECHA, CLIENTE, BASE, IVA, TOTAL, FORMAPAGO) VALUES (?, ?, ?, ?, ?, ?)";
        $stmt = $mysqli->prepare($sql);
        if ($stmt === false) {
            die("Error al preparar la consulta: " . $mysqli->error);
        }
        $stmt->bind_param("ssddds", $fecha, $cliente, $base, $iva, $total, $pago);
        $stmt->execute();
        if ($stmt->error) {
            die("Error al insertar factura: " . $stmt->error);
        }

        // Obtener el número de factura generado
        $numeroFact = $mysqli->insert_id;

        // Inserción de las líneas de factura en `2024_25_LIN_FACT`
        for ($i = 0; $i < count($producto); $i++) {
            $sql = "INSERT INTO 2024_25_LIN_FACT (NFACT, PRODUCTO, PRECIO, CANTIDAD) VALUES (?, ?, ?, ?)";
            $stmt = $mysqli->prepare($sql);
            if ($stmt === false) {
                die("Error al preparar la consulta: " . $mysqli->error);
            }
            $stmt->bind_param("isdi", $numeroFact, $producto[$i], $precio[$i], $cantidad[$i]);
            $stmt->execute();
            if ($stmt->error) {
                die("Error al insertar línea de factura: " . $stmt->error);
            }
        }
        ?>

        <input type="hidden" name="numeroF" value="<?= $numeroFact ?>"/>
        <input type="hidden" name="fechaF" value="<?= $fecha ?>"/>
        <input type="hidden" name="clienteF" value="<?= $cliente ?>"/>
        <input type="hidden" name="pagoF" value="<?= $pago ?>"/>
        <input type="hidden" name="baseF" value="<?= number_format($base, 2) ?>"/>
        <input type="hidden" name="ivaF" value="<?= number_format($iva, 2) ?>"/>
        <input type="hidden" name="totalF" value="<?= number_format($total, 2) ?>"/>

        <button type="submit" name="enviar" id="enviar">Enviar</button>
        <button type="reset" name="reset">Borrar</button>
    </form>
</body>
</html>
