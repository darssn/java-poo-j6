package fr.diginamic.recensement.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class Connectionbdd {

	static {
		try {
			Class.forName("org.mariadb.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static Connection newConnect() {

		Connection connect = null;
		/**
		 * Recuperation des données du ficher .properties
		 */
		ResourceBundle data = ResourceBundle.getBundle("database");

		String dbPath = data.getString("dbCC.path");
		String dbUsername = data.getString("dbCC.username");
		String dbPwd = data.getString("dbCC.pwd");

		/**
		 * Ouverture connection a la base de données
		 */

		try {
			connect = DriverManager.getConnection(dbPath, dbUsername, dbPwd);

		} catch (SQLException e) {

			System.out.println(e);
		}

		return connect;

	}

}
