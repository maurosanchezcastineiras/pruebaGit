<?php
$c = $_POST["cate"];
include("conecta.php");
$sql = "SELECT PRODUCTO, PRECIO, FOTO FROM 2024_25_prodCafeteria WHERE CATEGORIA='$c'";
$resultado = $mysqli->query($sql);
$datos = $resultado->fetch_all();
$num_rows = count($datos);
for ($i = 0; $i < $num_rows; $i++) {
    $producto = $datos[$i][0];
    $precio = $datos[$i][1]; 
    $foto = $datos[$i][2];
    ?>
    <button type="button" name="<?=$producto?>" id="<?=$producto?>" onclick="pro(this.id, <?=$precio?>)">
        <img src="img/<?=$foto?>">
    </button>
    <?php
}
?>
