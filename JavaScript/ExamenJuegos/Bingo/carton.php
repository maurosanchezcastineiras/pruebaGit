<?php
//función para generar el cartón 
function generarCartonBingo($CARTON, $Simulacion = false) {
    $numeros = [
        [1, 9],    
        [10, 19],  
        [20, 29],  
        [30, 39],  
        [40, 49],  
        [50, 59],  
        [60, 69],  
        [70, 79],  
        [80, 89]   
    ];
    $carton = [];

    for ($fila = 0; $fila < 3; $fila++) {
        // se crea una fila con 9 columnas vacías 
        $filaCarton = array_fill(0, 9, null);
        // Array para almacenar las posiciones de las casillas vacías
        $vacios = [];
        // se seleccionan 4 casillas vacías aleatoriamente
        while (count($vacios) < 4) {
            $vacio = rand(0, 8); 
            if (!in_array($vacio, $vacios)) {
                $vacios[] = $vacio; 
            }
        }

        // se recoorree cada columna de la fila 
        foreach ($filaCarton as $columna => &$numero) {
            if (in_array($columna, $vacios)) {
                continue;
            }
            $numero = rand($numeros[$columna][0], $numeros[$columna][1]);
        }

        // se añade la fila al cartón
        $carton[] = $filaCarton;
    }

    ///////////////777
    ///se crea la tabladel cartón
    /////////////////

    echo "<table border='1' class='table' data-carton='$CARTON'>";
    // se recorre cada fila del cartón
    foreach ($carton as $FILA => $fila) {
        echo "<tr>";
        // se recorr cada columna de la fila 
        foreach ($fila as $COLUMNA => $numero) {
            // se genera un ID único para cada casilla
            $botoncasilla = "carton{$CARTON}-{$FILA}-{$COLUMNA}";
            $datos = "data-carton='$CARTON' data-fila='$FILA' data-columna='$COLUMNA' data-numero='$numero'";
            echo "<td>";
            // Si la casilla no está vacía
            if ($numero !== null) {
                // Si es del cartón de la simulacion
                if ($Simulacion) {
                    //  se muestra el número sin el clic
                    echo "<button id='$botoncasilla' class='btn btn-light' $datos>$numero</button>";
                } else {
                    // Se añade la funcion del clic
                    echo "<button id='$botoncasilla' class='btn btn-light' $datos onclick='cambiarColorJugador(this.id)'>$numero</button>";
                }
            }
            echo "</td>";
        }
        echo "</tr>";
    }
    echo "</table>";
}
?>