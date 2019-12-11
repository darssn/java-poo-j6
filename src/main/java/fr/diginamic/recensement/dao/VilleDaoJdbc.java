package fr.diginamic.recensement.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import fr.diginamic.recensement.entites.Ville;

import fr.diginamic.recensement.utils.Connectionbdd;

public class VilleDaoJdbc implements VilleDao {

	@Override
	public List<Ville> extraire() {
		List<Ville> liste = new ArrayList<>();

		Connection connect = Connectionbdd.newConnect();

		try {
			Statement st = connect.createStatement();

			ResultSet rs = st.executeQuery("select * from ville");

			while (rs.next()) {

				String nomVille = rs.getString("NOM_VILLE");
				int codeVille = rs.getInt("CODE_VILLE");
				int pop = rs.getInt("POP");
				int idDep = rs.getInt("ID_DEP");

				liste.add(new Ville(nomVille, codeVille, pop, idDep));
			}

			rs.close();
			st.close();
			connect.close();

		} catch (SQLException e) {
			try {
				connect.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			System.out.println(e);
		}

		return liste;
	}

	@Override
	public void insert(Ville ville) {

		Connection connect = Connectionbdd.newConnect();
		Statement st = null;
		ResultSet rs = null;

		String nomVille = ville.getNom();
		int codeVille = Integer.parseInt(ville.getCodeVille());
		int pop = ville.getPopulation();
		int idDep = ville.getIdDep();

		try {

			st = connect.createStatement();

			rs = st.executeQuery(

					"SELECT * from ville where NOM_VILLE ='" + nomVille + "' AND CODE_VILLE='" + codeVille
							+ "' AND POP=" + pop);

			if (!rs.next()) {

				st.executeUpdate("Insert into ville (NOM_VILLE,CODE_VILLE,POP,ID_DEP)values('" + nomVille + "',"
						+ codeVille + "," + pop + "," + idDep + ")");

			}

			connect.commit();

		} catch (SQLException e) {
			try {
				connect.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			System.out.println(e);
		} finally {
			try {
				rs.close();
				st.close();
				connect.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

	}

	@Override
	public int update(String ancienNom, String nouveauNom) {

		Connection connect = Connectionbdd.newConnect();
		int up = 0;

		try {
			Statement st = connect.createStatement();

			up = st.executeUpdate(
					"update ville set NOM_VILLE='" + nouveauNom + "' where NOM_VILLE='" + ancienNom + "'");

			connect.commit();
			st.close();
			connect.close();

		} catch (SQLException e) {
			try {
				connect.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			System.out.println(e);
		}

		return up;
	}

	@Override
	public boolean delete(Ville ville) {
		Connection connect = Connectionbdd.newConnect();
		Statement st = null;

		try {

			st = connect.createStatement();

			st.executeUpdate("delete from ville where NOM_VILLE ='" + ville.getNom() + "'");

			return true;

		} catch (SQLException e) {
			try {
				connect.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			System.out.println(e);
		} finally {
			try {

				st.close();
				connect.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

		return false;
	}

	@Override
	public Ville rechercheVille(String nomVille) {

		Connection connect = Connectionbdd.newConnect();
		Ville ville = null;

		try {
			Statement st = connect.createStatement();

			ResultSet rs = st.executeQuery("select * from ville where NOM_VILLE='" + nomVille + "'");

			if (rs.next()) {

				String nomV = rs.getString("NOM_VILLE");
				int codeVille = rs.getInt("CODE_VILLE");
				int pop = rs.getInt("POP");
				int idDep = rs.getInt("ID_DEP");

				ville = new Ville(nomV, codeVille, pop, idDep);
			}

			rs.close();
			st.close();
			connect.close();

		} catch (SQLException e) {
			try {
				connect.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			System.out.println(e);
		}
		return ville;
	}

	@Override
	public List<Ville> villeBorne(int a, int b, int iDep) {
		
		List<Ville> listeVille = new ArrayList<>();
		
		Connection connect = Connectionbdd.newConnect();


		try {
			Statement st = connect.createStatement();

			ResultSet rs = st.executeQuery("select * from ville where ID_DEP ="+ iDep+" AND POP between " + a + " AND "+b);

			while (rs.next()) {

				String nomV = rs.getString("NOM_VILLE");
				int codeVille = rs.getInt("CODE_VILLE");
				int pop = rs.getInt("POP");
				int idDep = rs.getInt("ID_DEP");

				listeVille.add(new Ville(nomV, codeVille, pop, idDep));
			}

			rs.close();
			st.close();
			connect.close();

		} catch (SQLException e) {
			try {
				connect.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			System.out.println(e);
		}

		return listeVille;
	}

}
