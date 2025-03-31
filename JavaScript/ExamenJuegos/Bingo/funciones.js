let numeros = [];
let simulacion;
let LINEA = false;
let BINGO = false;
/////////////////////////////////////////////////
  // funcion  que almacena el numero de botones que se marcan en tres arrays para diferenciar las tres filas
////////////////////////////////////////
const numerosEnCarton = {
    0: [],
    1: [], 
    2: [], 
}
/////////////////////////////////////////////////////////////////////
 // función que almacena el número de botnes marcados en la simulación
//////////////////////////////////////////////////////////////////////////
const numerosCartonSimulacion = {
    0: [], 
    1: [],
    2: [],
}
///////////////////////////////////////////////
   // funcion para cambiar el color del botón cuando se pulsa
///////////////////////////////////////////////
function cambiarColorJugador(id) {
    if (BINGO) {
        return;
    }
    const boton = document.getElementById(id);
    const fila = parseInt(boton.dataset.fila, 10);
    const numero = parseInt(boton.dataset.numero, 10);
    const colorActual = window.getComputedStyle(boton).backgroundColor;
    boton.style.backgroundColor = 'red'; 
     // añade el numero al array de numerosEnCarton
     numerosEnCarton[fila].push(numero);
    verificaCartonJugador();
}

//////////////////////////////////////////////////////////////7
 //Función que verifica el cartón para ver si hay linea o bingo
////////////////////////////////////////////////////////////
function verificaCartonJugador() {
    const carton = document.querySelector(`table[data-carton='1']`);
    const filas = carton.querySelectorAll('tr');
    const mensaje = document.getElementById('mensaje');
    let bingo = true; 
    let hayLINEA = false; 

    filas.forEach((fila, index) => {
        const botones = fila.querySelectorAll('button');
        const numerosMarcadosFila = numerosEnCarton[index].length; 
        if (numerosMarcadosFila < 5) {
            bingo = false;
        }
        if (numerosMarcadosFila === 5) {
            hayLINEA = true;
        }
    });

    if (bingo) {
        mensaje.innerText = '¡Bingooooooooooooooooooooooooooooooooooooooo!';
        BINGO = true; 

         //se detiene la simulación
        clearInterval(simulacion);
        botonesJugador();
    } else if (hayLINEA && !LINEA) {
        mensaje.innerText = '¡Líneaaaaaaaooooo!';
        LINEA = true; 
    } else {
        mensaje.innerText = ''; 
    }
}

///////////////////////////////////////////////
    // función que se llama cuando el jugador hace bingo
////////////////////////////////////////////
function botonesJugador() {
    const botones = document.querySelectorAll(`table[data-carton='1'] button`);
    for (let i = 0; i < botones.length; i++) {
        botones[i].disabled = true;
    }
}

//////////////////////////////////////////////////////
  // función que inicia la simulación del enemigo
///////////////////////////////////////////////////////////
function iniciarSimulacion() {
    const mensaje = document.getElementById('mensaje');
    mensaje.innerText = '';
    numeros = [];
      // detiene la simulacion
    clearInterval(simulacion);
    LINEA = false; 
    BINGO = false; 

    const botones = document.querySelectorAll(`table[data-carton='1'] button`);
    for (let i = 0; i < botones.length; i++) {
        botones[i].disabled = false;
    }
    simulacion = setInterval(() => {
        if (BINGO) {
            clearInterval(simulacion);
            return;
        }
        let nuevoNumero;
        do {
            nuevoNumero = Math.floor(Math.random() * 89) + 1;
        } while (numeros.includes(nuevoNumero));
        numeros.push(nuevoNumero);
        mostrarNumeroSimulado(nuevoNumero);
        marcarNumeroSimulado(2, nuevoNumero);

        if (numeros.length >= 100) {
            clearInterval(simulacion);
        }
    }, 5000);
}

////////////////////////////////////
   // Función para mostrar los numeros
///////////////////////////////////////
function mostrarNumeroSimulado(numero) {
    const mensaje = document.getElementById('mensaje');
    mensaje.innerText = `Número: ${numero}`;
}

///////////////////////////////////////////////
  // Función para marcar el número en el cartón 
//////////////////////////////////////////
function marcarNumeroSimulado(idCarton, numero) {
    const botones = document.querySelectorAll(`table[data-carton='${idCarton}'] button`);
    for (let i = 0; i < botones.length; i++) {
        const boton = botones[i];
    // si el numero coincide con el genrado
        if (parseInt(boton.dataset.numero, 10) === numero) {
            boton.style.backgroundColor = 'red'; 
            const fila = parseInt(boton.dataset.fila, 10);
            // se añade el número al array
            numerosCartonSimulacion[fila].push(numero);
        }
    }
    verificaCartonSimulacion(idCarton);
}

////////////////////////////////////////////////////7///////
//función para verificar el carton del enemigo si hay bingo o linea
////////////////////////////////////////////////////////////////////
function verificaCartonSimulacion(idCarton) {
    const carton = document.querySelector(`table[data-carton='${idCarton}']`);
    const mensaje = document.getElementById('mensaje');
    let bingo = true;
    let hayLINEA = false; 
    for (const fila in numerosCartonSimulacion) {
        const numerosMarcadosFila = numerosCartonSimulacion[fila].length;
        if (numerosMarcadosFila < 5) {
            bingo = false;
        }
        if (numerosMarcadosFila === 5) {
            hayLINEA = true;
        }
    }

    if (bingo) {
        mensaje.innerText = '¡Bingoooooooooooooooooooooooooo!';
        BINGO = true;
        //  sed etiene la simulación
        clearInterval(simulacion);
        botonesJugador();
    } else if (hayLINEA && !LINEA) {
        mensaje.innerText = '¡Líneaaaaaaaooooo!';
        LINEA = true; 
    }
}

window.cambiarColorJugador = cambiarColorJugador;
window.iniciarSimulacion = iniciarSimulacion;