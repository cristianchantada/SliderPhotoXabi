<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="org.cvarela.sliderWeb.models.Imagen, org.cvarela.sliderWeb.repositories.ImagenDao" %>
 
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="styles/style.css">
    <title>Foto slider</title>
    <script>
        // Declarar una variable JavaScript para almacenar las URL de las imágenes
        const imgUrls = [
            <% List<Imagen> listaImagenes = (List<Imagen>) request.getAttribute("listaImagenes");
               for(Imagen img : listaImagenes){
            %>
                "<%= img.getSrc() %>",
            <% } %>
        ];        

        let indiceImagenActual = 0;

        function anterior() {
        	
        	console.log("imgUrls =" + imgUrls);
        	
        	
            indiceImagenActual--;
            if (indiceImagenActual < 0) {
                indiceImagenActual = imgUrls.length - 1;
            }
            document.getElementById("image").src = "misImg/" + imgUrls[indiceImagenActual];
        }

        function siguiente() {
            indiceImagenActual++;
            if (indiceImagenActual >= imgUrls.length) {
                indiceImagenActual = 0;
            }
            document.getElementById("image").src = "misImg/" + imgUrls[indiceImagenActual];
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
            }, 1000);
        }

        function parar(){
            stop = 1;
        }
    </script>
</head>
<body>

    <div>
        <button onclick="anterior()">Anterior</button>
        <img width="400px" height="400px" id="image" alt="">
        <button onclick="siguiente()">Siguiente</button>
        <button onclick="automatico()">Automático</button>
        <button onclick="parar()">Parar</button>
    </div>