package org.cvarela.sliderWeb.controllers;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import org.cvarela.sliderWeb.models.Imagen;
import org.cvarela.sliderWeb.repositories.ImagenDao;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;

import java.io.*;

@WebServlet({"/index.html", "/"})
@MultipartConfig(
		fileSizeThreshold = 1024 * 1024 * 2,
	    maxFileSize = 1024 * 1024 * 10,
	    maxRequestSize = 1024 * 1024 * 50
)
public class UploadServlet extends HttpServlet{

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		HttpSession session = req.getSession();
		if (session.getAttribute("login") != "true") {
			resp.sendRedirect(req.getContextPath() + "/login.jsp");
		} else {
			resp.sendRedirect(req.getContextPath() + "/upload.jsp");
		}
		
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		
		String checkboxValue = req.getParameter("check");
	    boolean isVisible = (checkboxValue != null && checkboxValue.equals("on"));
		
        Part filePart = req.getPart("file");
        String fileName = getFileName(filePart);        
        String uploadPath = getServletContext().getRealPath("") + File.separator + "misImg";       
        
        ImagenDao imagenDao = new ImagenDao();
        Imagen imagen = new Imagen(fileName, isVisible);
        imagenDao.save(imagen);
        
        File uploadDir = new File(uploadPath);
        if (!uploadDir.exists()) {
            uploadDir.mkdir();
        }
        
        System.out.println("uploadDir = " + uploadDir);
        
        
        File file = new File(uploadDir, fileName);
        try (InputStream input = filePart.getInputStream(); 
        		OutputStream output = new FileOutputStream(file)) {
            byte[] buffer = new byte[1024];
            int length;
            while ((length = input.read(buffer)) > 0) {
                output.write(buffer, 0, length);
            }
        }
        
		resp.sendRedirect(req.getContextPath()+ "/slider-servlet");
        
	} 
	
    private String getFileName(final Part part) {
        final String partHeader = part.getHeader("content-disposition");
        for (String content : partHeader.split(";")) {
            if (content.trim().startsWith("filename")) {
                return content.substring(content.indexOf('=') + 1).trim().replace("\"", "");
            }
        }
        return null;
    }
    

}
