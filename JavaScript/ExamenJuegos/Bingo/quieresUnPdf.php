<?php
require('../pdf/fpdf.php');

if ($_SERVER['REQUEST_METHOD'] == 'POST') {
    // función para crear el cartón
    function generarCartonBingo($pdf) {
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

        // el cartoon al principio no tiene numeros 
        $carton = [
            [null, null, null, null, null, null, null, null, null],
            [null, null, null, null, null, null, null, null, null],
            [null, null, null, null, null, null, null, null, null]
        ];

        /////////////////////////////////////////////////////////////
            // selecciona las casillas vacías
        ////////////////////////////////////////////////////////
        for ($fila = 0; $fila < 3; $fila++) {
            // lista para los vacios
            $vacios = [];
            while (count($vacios) < 4) {
                  // casilla aleatoria
                $casilla = rand(0, 8);
                // si la casilla no está en la lista
                if (!in_array($casilla, $vacios)) {
                    // se añade a la lista de vacias
                    $vacios[] = $casilla;
                }
            }
            for ($casilla = 0; $casilla < 9; $casilla++) {
                 // Si la casilla no está en la lista
                if (!in_array($casilla, $vacios)) {
                     // se genera un número aleatorio
                    $numero = rand($numeros[$casilla][0], $numeros[$casilla][1]);
                    //  el número para la casilla
                    $carton[$fila][$casilla] = $numero;
                }
            }
        }

        $destino = "../img/bingooo.jpg";
        $pdf->Image($destino, 80, 20, 50, 50);
        $pdf->Ln(60); 

        $pdf->SetFont('Arial', '', 10);
//////////////////////////////////////////////////////////////////////////////////
                 // se dibuja el cartón
///////////////////////////////////////////////////////////////////////////////
        // recorren las filas
        for ($fila = 0; $fila < 3; $fila++) {
            // se recorren las casilla de la fila actual
            for ($casilla = 0; $casilla < 9; $casilla++) {
                // se dibuja el rectángulo para cada casilla
                $pdf->Rect(20 + ($casilla * 20), 80 + ($fila * 20), 20, 20);
                // Si la casilla no está vacía
                if ($carton[$fila][$casilla] !== null) {
                    // se escribe el numro
                    $pdf->Text(20 + ($casilla * 20) + 6, 80 + ($fila * 20) + 12, (string)$carton[$fila][$casilla]);
                }
            }
        }
    }
    ///////////////////////////
    // crecacion del pdf
    ////////////

    $pdf = new FPDF();
    $pdf->AddPage();
    // se llama a la función para generar el cartón
    generarCartonBingo($pdf);
    // lo descargamos 
    $pdf->Output('D', 'carton_bingo.pdf');
    exit();
}
?>