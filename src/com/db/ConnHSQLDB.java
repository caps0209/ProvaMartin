package com.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnHSQLDB {

	private String usuario = "SA";
	private String senha = "";
	private String PathBase = "C:\\Users\\PICHAU\\eclipse-workspace\\ProvaMartin\\Dados\\dbProva";
	private String URL = "jdbc:hsqldb:file:" + PathBase + ";";

	public Connection conectar() {
		try {
			return DriverManager.getConnection(URL, usuario, senha);
		} catch (SQLException e) {
			System.out.println("Nao conectou com o Banco:\n");
			e.printStackTrace();
			throw new Error("SQL Exception");

		}
	}
}
