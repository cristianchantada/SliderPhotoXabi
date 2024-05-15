const img = ["1", "2", "3", "4", "5", "6", "7", "8", "9", "10"];
let indiceImagenActual = 0;

function anterior() {
    indiceImagenActual--;
    if (indiceImagenActual < 0) {
        indiceImagenActual = img.length - 1;
    }
    document.getElementById("image").src = img[indiceImagenActual] + ".jpg";
}

function siguiente() {
    indiceImagenActual++;
    if (indiceImagenActual > 9) {
        indiceImagenActual = 0;
    }
    document.getElementById("image").src = img[indiceImagenActual] + ".jpg";
}

let stop = 0;

function automatico(){
    let contador = 0;
    const intervalo = setInterval(function() {
        contador++;
        siguiente();
        if (stop === 1) {
            clearInterval(intervalo);
            stop = 0;
        }
    }, 100);
}

function parar(){
    stop = 1;
}


