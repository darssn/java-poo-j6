package fr.diginamic.recensement.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ResourceBundle;

/**Classe permettant d'ouvrir une connection a une base de données
 * 
 * */
public class Connectionbdd {

	static {
		try {
			Class.forName("org.mariadb.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	
	/**Permet de creer une instance de Connection avec les parametre de connection a la base de données
	 * 
	 *@return Connection
	 **/
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

			throw new RuntimeException("Impossible de se connecter a la base de données");
		}

		return connect;

	}

}
