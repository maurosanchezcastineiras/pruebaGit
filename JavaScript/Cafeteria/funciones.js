var cantidad = 0;
var importe = 0;

function carga(tipo){
    switch(tipo){
        case 'categorias':
           // alert("Has escogido categorias");
            $('#cuerpo').load('php/categorias.php');
        break;

        case 'productos':
            //alert("Has escogido productos");
            $('#cuerpo').load('php/introducir_productos.php');
        break;

        case 'empleados':
            //alert("Has escogido empleado");
            $('#cuerpo').load('php/introducir_empleados.php');
        break;

        case 'proveedores':
            //alert("Has escogido proveedores");
            $('#cuerpo').load('php/introducir_proveedores.php');
        break;

        case 'fidelizacion':
            //alert("Has escogido tarjeta");
            $('#cuerpo').load('php/introducir_tarjeta.php');
        break;
    }
}


function muestraPro(categoriaEscogida){
    $('#cuerpo').load('php/productos.php',{cate:categoriaEscogida});
}

function pro(producto, precio){
    var prod=producto.replace(/\s/g,"_");

    //alert(producto+"........."+precio+"____________"+prod);

    cantidad = $('#Cant_'+prod).val();

    if($('#ticketForm').length==0){
        $('#ticket').append("<form id='ticketForm' name='ticketForm' method='POST' action='php/factura.php'>");
    }

    if(cantidad == null){
        $('#ticketForm').append("<div id='Div_"+prod+"'>");
        $('#Div_'+prod).append("<input type='text' name='"+prod+"' id='"+prod+"' value='"+producto+"' readonly/>");
        $('#Div_'+prod).append("<input type='text' name='pre_"+prod+"' id='pre_"+prod+"' value='"+precio+"' class='peke' readonly/>");
        $('#Div_'+prod).append("<input type='number' name='Cant_"+prod+"' id='Cant_"+prod+"' value='1' class='peke' readonly/>");
        $('#Div_'+prod).append("<button type='button' id='M_"+prod+"' onclick='resta(\""+prod+"\")' class='peke' >-</button>");
        $('#Div_'+prod).append("<button type='button' id='B_"+prod+"' onclick='borrar(\""+prod+"\")' class='peke' >x</button>");
    } else {
        cantidad++;
        $('#Cant_'+prod).val(cantidad);
    }
    let prize = $('#pre_'+prod).val();
    prize = parseFloat(prize);
    totalizar(prize,1);
}

function resta(prod){
    cantidad=$('#Cant_'+prod).val();
    cantidad--;
    if(cantidad>0){
        let prize = $('#pre_'+prod).val();
        prize = parseFloat(prize);
        totalizar(prize,-1);
        //alert(cantidad+"..........."+prod);
        $('#Cant_'+prod).val(cantidad);
    } else {
        borrar(prod);
    }
}

function borrar(prod){
    let prize = $('#pre_'+prod).val();
    prize = parseFloat(prize);
    cantidad = $('#Cant_'+prod).val();
    cantidad = (-1)*parseFloat(cantidad);
    totalizar(precio,cantidad);
    $('#Div_'+prod).remove();
}

function totalizar(pre, cant){
    importe += parseFloat(pre)*parseFloat(cant);
    //alert("Importe total: "+importe.toFixed(2));
    $('#enviarFact').remove();
    if($('#importe').val()==null){
        $('#factura').append("<input type='text' name='importe' id='importe' value='"+importe.toFixed(2)+"' readonly />");
    } else {
        $('#importe').val(importe.toFixed(2));
    }

    $('#ticketForm').append("<button type='submit' name='enviarFact' id='enviarFact'>Facturar</button>");

    if($('#importe').val()==0.0){
        $('#importe').remove();
        $('#enviarFact').remove();
    }
}