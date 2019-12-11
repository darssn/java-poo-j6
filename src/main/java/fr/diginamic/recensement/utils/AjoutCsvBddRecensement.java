package fr.diginamic.recensement.utils;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import fr.diginamic.recensement.utils.Connectionbdd;
import fr.diginamic.recensement.entites.Recensement;
import fr.diginamic.recensement.entites.Ville;

public class AjoutCsvBddRecensement {

	public static void create() {

		String path = ClassLoader.getSystemClassLoader().getResource("recensement.csv").getFile();

		Recensement liste = RecensementUtils.lire(path);

		Connection connect = Connectionbdd.newConnect();
		Statement st = null;
		ResultSet rs = null;

		for (Ville v : liste.getVilles()) {

			int codeReg = Integer.parseInt(v.getCodeRegion());
			String nomReg = v.getNomRegion().replace("'", "''");
			String codeDep = v.getCodeDepartement();
			int codeVille = Integer.parseInt(v.getCodeVille());
			String nomVille = v.getNom().replace("'", "''");
			int pop = v.getPopulation();

			try {

				st = connect.createStatement();

				rs = st.executeQuery(
						"SELECT * from region where CODE_REG =" + codeReg + " AND NOM_REG='" + nomReg + "'");

				if (!rs.next()) {
					st.executeUpdate("Insert into region (CODE_REG,NOM_REG)values(" + codeReg + ",'" + nomReg + "')");
				}

				rs = st.executeQuery("SELECT distinct ID_REG from region where CODE_REG =" + codeReg + " AND NOM_REG='"
						+ nomReg + "'");

				int idReg = 0;
				if (rs.next()) {
					idReg = rs.getInt("ID_REG");
				}

				rs = st.executeQuery("SELECT * from departement where CODE_DEP ='" + codeDep + "'");

				if (!rs.next()) {
					st.executeUpdate("Insert into departement(CODE_DEP,ID_REG)values('" + codeDep + "'," + idReg + ")");
				}

				rs = st.executeQuery("SELECT distinct ID_DEP from departement where CODE_DEP ='" + codeDep + "'");

				int idDep = 0;
				if (rs.next()) {

					idDep = rs.getInt("ID_DEP");
				}
				
				rs = st.executeQuery("SELECT * from ville where NOM_VILLE ='" + nomVille + "'");

				if (!rs.next()) {
					st.executeUpdate("Insert into ville (NOM_VILLE,CODE_VILLE,POP,ID_DEP)values('" + nomVille + "',"
							+ codeVille + "," + pop + "," + idDep + ")");
				}

				

			} catch (SQLException e) {
				try {
					connect.rollback();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				System.out.println(e);
			}

		}
		
		try {
			rs.close();
			st.close();
			connect.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		System.out.println("Ajout Terminé");

	}

}
