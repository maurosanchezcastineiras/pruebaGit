<?PHP
    include("conecta.php");
    $sql="select CATEGORIA,FOTO from 2024_25_cateCafeteria";
    $resultado=$mysqli->query($sql);
    $datos=$resultado->fetch_all();
    $num_rows=count($datos);
    for($i=0;$i<$num_rows;$i++){
        $categoria=$datos[$i][0];
        $cateFoto=$datos[$i][1];
        ?>
        <button type="button" name="<?=$categoria?>" id="<?=$categoria?>"
        onclick="muestraPro(this.id)">
            <img src="img/<?=$cateFoto?>"> </button>
        <?PHP
    }

?>