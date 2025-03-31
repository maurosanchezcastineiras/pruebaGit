<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Cafetería - Factura</title>
    <link rel="stylesheet" type="text/css" href="../pdf/fpdf186/fpdf.css">
    <script type="text/javascript" src="js/funciones.js"></script>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>
</head>
<body>
    <?php
    require('../pdf/fpdf186/fpdf.php');

    // Validación si es POST
    if ($_SERVER['REQUEST_METHOD'] == 'POST') {
        // Inicializamos la clase FPDF
        $pdf = new FPDF();
        $pdf->AddPage();
        $pdf->SetFont('Arial','B',12);

        // Imagen de cabecera
        $destino = "../img/cocktails.jpg";
        $pdf->Image($destino, 80, 20, 50, 50);
        $pdf->Ln(60); // Espacio debajo de la imagen

        // Información de la cafetería
        $pdf->Cell(0,10,mb_convert_encoding("Cafetería Cocktails", 'ISO-8859-1' , 'UTF-8'), 0, 1, 'C');
        $pdf->Cell(0,10,mb_convert_encoding("C/ Ramón Cabanillas 3, 15011 A Coruña", 'ISO-8859-1' , 'UTF-8'), 0, 1, 'C');
        $pdf->Cell(0,10,"981 47 73 62", 0, 1, 'C');
        $pdf->Ln(10);

        // Datos de la factura recibidos desde el formulario
        $nF = $_POST["numeroF"];
        $fF = $_POST["fechaF"];
        $cF = $_POST["clienteF"];
        $pF = $_POST["pagoF"];
        $bF = $_POST["baseF"];
        $iF = $_POST["ivaF"];
        $tF = $_POST["totalF"];

        // Mostrar datos principales de la factura
        $pdf->Cell(50,10,"Factura: ".$nF);
        $pdf->Ln();
        $pdf->Cell(50,10,"Fecha: ".$fF);
        $pdf->Ln();
        $pdf->Cell(50,10,"Cliente: ".$cF);
        $pdf->Ln();
        $pdf->Cell(50,10,"Metodo de Pago: ".$pF);
        $pdf->Ln();
        $pdf->Cell(50,10,"Base Imponible: ".$bF, 'UTF-8');
        $pdf->Ln();
        $pdf->Cell(50,10,"IVA: ".$iF , 'UTF-8');
        $pdf->Ln();
        $pdf->Cell(50,10,"Total: ".$tF, 'UTF-8');
        $pdf->Ln(20); // Espacio antes de la tabla de productos

        // Procesar y mostrar productos
        $values = array_values($_POST);
        $pdf->SetFont('Arial','',10);
        $pdf->Cell(50,10,'Producto', 1);
        $pdf->Cell(50,10,'Precio', 1);
        $pdf->Cell(50,10,'Cantidad', 1);
        $pdf->Cell(50,10,'Importe', 1);
        $pdf->Ln();

        // Imprimir productos y cantidades
        for($i = 0; $i < count($values)-8; $i+=4) {
            $pdf->Cell(50,10,$values[$i], 1);
            $pdf->Cell(50,10,$values[$i+1], 1);
            $pdf->Cell(50,10,$values[$i+2], 1);
            $pdf->Cell(50,10,($values[$i+1] * $values[$i+2]), 1);
            $pdf->Ln();
        }

        // Generar el archivo PDF
        $pdf->Output("../facturaS/factura".$nF.".pdf","F");
    } else {
        echo "No se han enviado datos para la factura.";
    }
    ?>

    <script>location.href="../index.php"</script>
</body>
</html>
