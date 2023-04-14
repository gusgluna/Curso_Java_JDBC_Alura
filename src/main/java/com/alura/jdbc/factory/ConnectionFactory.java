package com.alura.jdbc.factory;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import io.github.cdimascio.dotenv.Dotenv;

public class ConnectionFactory {
	private Dotenv dotenv = Dotenv.load();

	private DataSource dataSource;
	
	public ConnectionFactory() {
		var pooledDataSource = new ComboPooledDataSource();
		pooledDataSource.setJdbcUrl(dotenv.get("JDBC_URL"));
		pooledDataSource.setUser(dotenv.get("JDBC_USER"));
		pooledDataSource.setPassword(dotenv.get("JDBC_PASSWORD"));
		pooledDataSource.setMaxPoolSize(10);
		
		this.dataSource = pooledDataSource;
	}
	

	public Connection recuperaConexion(){
		try {
			return this.dataSource.getConnection();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

}
