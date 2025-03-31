<html>
<head>
    <title>Cafetería</title>
    <link rel="stylesheet" type="text/css" href="../css/cafeteria3.css">
    <script type="text/javascript" src="js/funciones.js"></script>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js">
    </script>
</head>
<h1>La cafetería de la esquina</h1>
<body>
        <div id="superior" class="sup">
            <button class="inicio" id="categorias" onclick="carga('categorias')">Categorías</button>
            <button class="inicio" id="productos" onclick="carga('productos')">Introducir Productos</button>
            <button class="inicio" id="empleados" onclick="carga('empleados')">Introducir Empleados</button>
            <button class="inicio" id="proveedores" onclick="carga('proveedores')">Introducir Proveedores</button>
            <button class="inicio" id="fidelizacion" onclick="carga('fidelizacion')">Tarjeta de Fidelización</button>
            <a href="../index.php" class="cerrar">
                <img src="../img/cerrarSesion.png" class="cerrar">
            </a>
        </div>
        <div id="cuerpo" class="inferior"></div>
        <div id="ticket" class="derecha"></div>
        <div id="factura" class="dInf"></div>
</body>
</html>