<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="https://bootswatch.com/5/vapor/bootstrap.min.css" />
    <link rel="stylesheet" href="../css/bingooo.css" />
    <title>BINGOOIVANATIMARTE</title>
    <script src="../js/funciones.js"></script>
</head>
<body>
    <h1 class="text-body-secondary">Bienvenido al BINGOOO. Gasta todo lo que quieras</h1>
    <div class="alert alert-dismissible alert-warning" id="cartonazos">
        <?php
            include 'carton.php';
            // Llamo a la función que crea un cartón de bingo pasándole su número y si es para que juegue el usuario (false) o que tenga simulación (true)
            generarCartonBingo(1, false); 
            generarCartonBingo(2, true); 
        ?>
    </div>
    <div class="alert alert-dismissible alert-warning" id="mensaje"></div>
    <button id="jugar" class="btn btn-primary" onclick="iniciarSimulacion()">Jugar</button>
</body>
</html>
