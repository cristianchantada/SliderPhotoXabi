package org.cvarela.sliderWeb.repositories;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import org.cvarela.sliderWeb.models.Imagen;

public class ImagenDao implements DaoInterface<Imagen> {

	private List<Imagen> listaImagenes = new ArrayList<>();

	private String user;
	private String password;
	private String url;
	private Connection conn;
	private String sql;
	private Statement stmt;
	private PreparedStatement preparedStatement;
	private ResultSet rs;

	public ImagenDao() {
		// initialize driver class
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (Exception e) {
			System.out.println("Fail to initialize Oracle JDBC driver in ClienteDao: " + e.toString());
		}

		user = "root";
		password = "0000";
		url = "jdbc:mysql://localhost:3306/partes";
		
		/*user = "cristian";
		password = "crstn-00";
		url = "jdbc:mysql://10.53.124.177:3306/partes";*/

		// connect
		conn = null;
		try {
			this.conn = DriverManager.getConnection(url, user, password);
			System.out.println(" Connection status: " + conn);
		} catch (Exception e) {
			System.out.println("Connection failed in ClienteDao: " + e.toString());
		}

		try {
			stmt = conn.createStatement();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public List<Imagen> getAll() {
		listaImagenes = new ArrayList<>();
		sql = "SELECT * FROM imagenes";
		try {
			rs = stmt.executeQuery(sql);
			while (rs.next()) {
				Imagen newImagen = new Imagen();
				newImagen.setSrc(rs.getString("src"));
				newImagen.setVisible(rs.getBoolean("visible"));
				listaImagenes.add(newImagen);
			}
		} catch (SQLException e1) {
			System.out.println("SQL exception en ClienteDao");
			e1.printStackTrace();
		} finally {
			closeConnection();
		}
		return listaImagenes;
	}
	

	@Override
	public void save(Imagen imagen) {
		
		System.out.println("Aquí Entraaaaaa");
		
		String sql = "INSERT INTO imagenes (src, visible) VALUES (?, ?)";
		try {
			preparedStatement = conn.prepareStatement(sql);
			preparedStatement.setString(1, imagen.getSrc());
			preparedStatement.setBoolean(2, imagen.isVisible());
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			System.out.println("Error al insertar el cliente en ClienteDao: " + e.getMessage());
		} finally {
			closeConnection();
		}
	}

	public void closeConnection() {
		try {
			if(rs != null) {
				rs.close();
			}
			if(stmt != null) {
				stmt.close();
			}
			if(preparedStatement != null) {
				preparedStatement.close();
			}
			if(conn != null) {
				System.out.println("La conexión en ClienteDao se ha cerrado con éxito");				
			}
			// commit only when updating the DB
			// conn.commit();
			// disconnect
			conn.close();
		} catch (SQLException e) {
			System.out.println("Close connection error in ClienteDao");
			e.printStackTrace();
		}
	}

	@Override
	public Imagen get(Imagen t) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void update(Imagen t, String[] params) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(Imagen t) {
		// TODO Auto-generated method stub
		
	}

}
