package org.cvarela.sliderWeb.controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.cvarela.sliderWeb.models.Imagen;
import org.cvarela.sliderWeb.repositories.ImagenDao;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/slider-servlet")
public class SliderServlet extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		List<Imagen> listaImagenes = new ArrayList<>();
		ImagenDao imagenDao = new ImagenDao();
		
		listaImagenes = imagenDao.getAll();
		
		req.setAttribute("listaImagenes", listaImagenes);
		getServletContext().getRequestDispatcher("/slider.jsp").forward(req, resp);
		
	}

}
