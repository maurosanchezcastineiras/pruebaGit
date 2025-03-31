let cartasDeVuelta = [];
let parejasEncontradas = 0;
let tiempoRestante = 60;
let totalParejas = 0;
let temporizador;

///////////////////////////////////////////////
// baraja las cartas
///////////////////////////////////////////////
function barajeo(starWars) {
    return starWars.sort(() => Math.random() - 0.5);
}

///////////////////////////////////////////////7
   //función para que inicie el juego
///////////////////////////////////////////////
function iniciaJuego() {
    clearInterval(temporizador);
    tiempoRestante = 60;

    const juego = document.getElementById('juego');
    if (juego) {
        juego.innerHTML = ''; 
    } else {
        console.error('MAL');
        return;
    }

    document.getElementById('barraProgreso').style.width = '0%';

    const starWars = [
        'img/lukeSkywalker.jpg', 'img/lukeSkywalker.jpg', 
        'img/darthVader.jpg', 'img/darthVader.jpg', 
        'img/leia.jpg', 'img/leia.jpg', 
        'img/lukeLeia.jpg', 'img/lukeLeia.jpg',
        'img/leia2.jpg', 'img/leia2.jpg', 
        'img/hanChewie.jpg', 'img/hanChewie.jpg',
        'img/r2.jpg', 'img/r2.jpg', 
        'img/lukeSkywalker2.jpg', 'img/lukeSkywalker2.jpg',
        'img/r2C3po.jpg', 'img/r2C3po.jpg',
        'img/hanSolo.jpg', 'img/hanSolo.jpg',
        'img/obi.jpg', 'img/obi.jpg',
    ];
    const barajeoResultado = barajeo(starWars); 
    totalParejas = barajeoResultado.length / 2; 
    creaCartas(barajeoResultado); 
    iniciaTemporizador(); 
}

////////////////////////////////
// Crea las cartas para jugar
////////////////////
function creaCartas(cartas) {
    const juego = document.getElementById('juego');
    cartas.forEach(function(carta, index) {
        const Carta = document.createElement('div'); 
        Carta.className = 'carta'; 
        Carta.innerHTML = `
            <div class="CARTA">
                <div class="frente">
                    <img src="${carta}" alt="Carta ${index + 1}">
                </div>
                <div class="deVuelta">?</div> 
            </div>
        `;
        Carta.addEventListener('click', function() {
            daVueltaCarta(Carta); 
        });
        juego.appendChild(Carta); 
    });
}

//////////////////////////////////////
// Voltea la carta
///////////////////////////////////
function daVueltaCarta(carta) {
    // si hay menos de 2 cartas volteadas 
    if (cartasDeVuelta.length < 2) {
        carta.classList.add('dadaVuelta'); 
        // Añade la carta a la lista de cartas dadas la vuelta
        cartasDeVuelta.push(carta); 
        if (cartasDeVuelta.length === 2) {
            verificaIguales(); 
        }
    }
}

////////////////////////////
// verifica cartas iguales
//////////////////////////////////
function verificaIguales() {
    const [carta1, carta2] = cartasDeVuelta; 
    // se obtiene la primera imagen
    const imagen1 = carta1.querySelector('.frente img').src; 
    // se obtiene la segunda imagen
    const imagen2 = carta2.querySelector('.frente img').src; 

    if (imagen1 === imagen2) {
        parejasEncontradas++; 
        actualizarBarraTiempo();
        // indica que la primera carta es igual
        carta1.classList.add('igual'); 
        // indica que la segunda carta es igual
        carta2.classList.add('igual'); 

        if (parejasEncontradas === totalParejas) {
            setTimeout(function() {
                alert('IMPOSIBLE! HAS GANADO!'); 
            }, 500);
            clearInterval(temporizador); 
        }
    } else {
        // Después de 1 segundo oculta las dos cartas si no son iguales
        setTimeout(function() {
            // Oculta la primera carta si no se coincide con la segunda
            carta1.classList.remove('dadaVuelta'); 
            // Oculta la segunda carta si no se coincide con la primera
            carta2.classList.remove('dadaVuelta'); 
        }, 1000);
    }

    cartasDeVuelta = []; 
}

/////////////////////
// El tiempo
/////////////////////
function iniciaTemporizador() {
    temporizador = setInterval(function() {
        tiempoRestante--; 
        const barraProgreso = document.getElementById('barraProgreso');
        // porcentaje de tiempo restante con el tiempo total 
        const progreso = (tiempoRestante / 60) * 100; 
        barraProgreso.style.width = progreso + '%'; 
        if (tiempoRestante <= 0) {
            // se detiene el temporizador
            clearInterval(temporizador); 
            if (tiempoRestante === 0) {
                alert('¡JAJAJA SE TE ACABO EL TIEMPO!'); 
                reiniciaJuego(); 
            }
            tiempoRestante = -1; 
        }
    }, 1200); 
}

//////////////////////////////
 // actualiza la barra de tiempo
///////////////////////////7
function actualizarBarraTiempo() {
    const progreso = (parejasEncontradas / totalParejas) * 100; 
    const barraProgreso = document.getElementById('barraProgreso');
    barraProgreso.style.width = progreso + '%'; 
}

////////////////////
// Reinicia el juego 
//////////////////////
function reiniciaJuego() {
     // se limpia el temporizzador
    clearInterval(temporizador); 
    iniciaJuego();
}
iniciaJuego();