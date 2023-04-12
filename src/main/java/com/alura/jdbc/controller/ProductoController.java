package com.alura.jdbc.controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alura.jdbc.factory.ConnectionFactory;


public class ProductoController {

	public int modificar(String nombre, String descripcion, Integer id, Integer cantidad) throws SQLException {
		// TODO
		Connection con = new ConnectionFactory().recuperaConexion();
		/*
		Statement statement = con.createStatement();
		statement.execute("UPDATE PRODUCTO SET" 
							+ " NOMBRE = '" + nombre + "' "
							+ ", DESCRIPCION = '" + descripcion + "' "
							+ ", CANTIDAD = " + cantidad
							+ " WHERE ID = " + id + ";");
		*/
		PreparedStatement statement = con.prepareStatement("UPDATE PRODUCTO SET "
				+ "NOMBRE = ? "
				+ ", DESCRIPCION = ?"
				+ ", CANTIDAD = ?"
				+ " WHERE ID = ?"
				);
		statement.setString(1, nombre);
		statement.setString(2, descripcion);
		statement.setInt(3, cantidad);
		statement.setInt(4, id);
		
		statement.execute();
		
		int updateCount = statement.getUpdateCount();
		statement.close();
	    con.close();
	    return updateCount;
	}

	public int eliminar(Integer id) throws SQLException {
		// TODO
		Connection con = new ConnectionFactory().recuperaConexion();
		/*
		Statement statement = con.createStatement();
		statement.execute("DELETE FROM PRODUCTO WHERE id = " + id);
		*/
		PreparedStatement statement = con.prepareStatement("DELETE FROM PRODUCTO WHERE id = ?");
		statement.setInt(1, id);
		statement.execute();
		statement.close();
		con.close();
		return statement.getUpdateCount();			
	}

	public List<Map<String, String>> listar() throws SQLException {
		Connection con = new ConnectionFactory().recuperaConexion();
		/*
		Statement statement = con.createStatement();
		statement.execute("SELECT ID, NOMBRE, DESCRIPCION, CANTIDAD FROM PRODUCTO");
		*/
		PreparedStatement statement = con.prepareStatement("SELECT ID, NOMBRE, DESCRIPCION, CANTIDAD FROM PRODUCTO");
		statement.execute();
		
		ResultSet resultSet = statement.getResultSet();
		
		List<Map<String, String>> resultado = new ArrayList<>();
		
		while (resultSet.next()) {
			Map<String, String> fila = new HashMap<>();
			fila.put("ID", String.valueOf(resultSet.getInt(1)));
			fila.put("NOMBRE", resultSet.getNString(2));
			fila.put("DESCRIPCION", resultSet.getNString(3));
			fila.put("CANTIDAD", String.valueOf(resultSet.getInt(4)));
			
			resultado.add(fila);
		}
		
		resultSet.close();
		con.close();
		return resultado;
	}

    public void guardar(Map<String, String> producto) throws SQLException {
		// TODO
    	String nombre = producto.get("NOMBRE");
    	String descripcion = producto.get("DESCRIPCION");
    	Integer cantidad = Integer.valueOf(producto.get("CANTIDAD"));
    	Integer maximoCantidad = 50;
    	
    	Connection con = new ConnectionFactory().recuperaConexion();
    	con.setAutoCommit(false);
    	/*
    	Statement statement = con.createStatement();
    	statement.execute("INSERT INTO PRODUCTO(nombre, descripcion, cantidad) " + 
    					  "VALUES('" + producto.get("NOMBRE") + "', '" 
    					  + producto.get("DESCRIPCION") + "', "
    					  + producto.get("CANTIDAD") + ")", Statement.RETURN_GENERATED_KEYS);
    	 */
    	
    	PreparedStatement statement = con.prepareStatement("INSERT INTO PRODUCTO "
    				+ "(nombre, descripcion, cantidad) "
    				+ "VALUES(?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
    	try {
    		do {
    			Integer cantidadParaGuardar = Math.min(cantidad, maximoCantidad);
    			ejecutarRegistro(nombre, descripcion, cantidadParaGuardar, statement);
    			cantidad -= maximoCantidad;
    		} while (cantidad > 0);    		
    		con.commit();
			
		} catch (Exception e) {
			con.rollback();
		}
    	statement.close();
		con.close();
	}

	private void ejecutarRegistro(String nombre, String descripcion, Integer cantidad, PreparedStatement statement)
			throws SQLException {
		statement.setString(1, nombre);
		statement.setString(2, descripcion);
		statement.setInt(3, cantidad);
    	
    	statement.execute();
    	
        final ResultSet resultSet = statement.getGeneratedKeys();
        
        try(resultSet){      	
        	while (resultSet.next()) {
        		System.out.println(
        				String.format("Fue insertado el productode ID %d",
        						resultSet.getInt(1))
        				);
        	}
        }
	}

}
