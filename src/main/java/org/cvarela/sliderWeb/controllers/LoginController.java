package org.cvarela.sliderWeb.controllers;

import java.io.IOException;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;

import org.cvarela.sliderWeb.models.Cliente;
import org.cvarela.sliderWeb.repositories.ClienteDao;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/login")
public class LoginController extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
			
		
		boolean showWrongAccess = false;
		boolean showLimitAccess = false;

		if (req.getMethod().equals("POST") && req.getParameter("password") != null) {

			String email = req.getParameter("email");
			String password = req.getParameter("password");
			
			Cliente cliente = new Cliente();
			ClienteDao clienteDao = new ClienteDao();
			cliente = clienteDao.getClientByEmail(email);
					
			LocalTime lastAccessTime = cliente.getAccessTime();
			long minutesDifference;
			
			if(lastAccessTime != null){
				minutesDifference = lastAccessTime.until(LocalTime.now(), ChronoUnit.SECONDS);
			} else {
				minutesDifference = 0;
			}

			int accessCounter = 0;
			// Si han pasado más de 5 minutos desde el último acceso fallido,
			// el contador de entradas fallidas se setea a cero siempre.
			if(minutesDifference > 120){		
				accessCounter = 0;
				cliente.setAccessCounter(0);
			}
		
			if (cliente.getAccessCounter() >= 5 && minutesDifference < 120) {
				showLimitAccess = true;
				accessCounter = 5;
			} else if (password.equals(cliente.getPassword())) {			
						
				HttpSession session = req.getSession();
				
				session.setAttribute("login", "true");
				session.setMaxInactiveInterval(120);
				
				Cookie cookie = new Cookie("userEmail", cliente.getEmail()); 
				cookie.setMaxAge(120);
				resp.addCookie(cookie);
				resp.sendRedirect("index.jsp");			
				// Siempre que entremos correctamente a la web, el contador de accesos se setea a cero
				accessCounter = 0;
			} else {
				showWrongAccess = true;
				accessCounter = cliente.getAccessCounter() + 1;
				ClienteDao clienteDao2 = new ClienteDao();
				clienteDao2.updateClientAccessTime(LocalTime.now(),  cliente.getNif());
			}
			ClienteDao clienteDao3 = new ClienteDao();
			clienteDao3.updateAccessCounter(accessCounter, cliente.getNif());
		}
		
		
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		
		
	}
	
	
	

}
