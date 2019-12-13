package fr.diginamic.recensement.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fr.diginamic.recensement.entites.Ville;

import fr.diginamic.recensement.utils.Connectionbdd;

public class VilleDaoJdbc implements VilleDao {

	private static final Logger LOG = LoggerFactory.getLogger(VilleDaoJdbc.class);

	public Connection connect = null;
	public PreparedStatement stInsert;
	public PreparedStatement stUpdateNom;
	public PreparedStatement stDelete;
	public PreparedStatement stAffAll;
	public PreparedStatement stRechVille;
	public PreparedStatement stBorneVille;

	public VilleDaoJdbc() {

		connect = Connectionbdd.newConnect();

		try {
			stInsert = connect.prepareStatement("Insert into ville (NOM_VILLE,CODE_VILLE,POP,ID_DEP)values(?,?,?,?)");
			stUpdateNom = connect.prepareStatement("update ville set NOM_VILLE=? where NOM_VILLE=?");
			stDelete = connect.prepareStatement("delete from ville where NOM_VILLE =?");
			stAffAll = connect.prepareStatement("select * from ville");
			stRechVille = connect.prepareStatement("select * from ville where NOM_VILLE=?");
			stBorneVille = connect.prepareStatement("select * from ville where ID_DEP = ? AND POP between ? AND ?");

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			throw new RuntimeException(e);
		}

	}

	public void close() {

		try {

			stInsert.close();
			stUpdateNom.close();
			stDelete.close();
			stAffAll.close();
			stRechVille.close();
			stBorneVille.close();

			if (connect != null) {
				connect.close();
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			throw new RuntimeException(e);
		}
	}

	@Override
	public List<Ville> extraire() {

		if (connect == null) {
			connect = Connectionbdd.newConnect();
		}

		List<Ville> liste = new ArrayList<>();
		ResultSet rs = null;

		try {

			rs = stAffAll.executeQuery();

			while (rs.next()) {

				String nomVille = rs.getString("NOM_VILLE");
				int codeVille = rs.getInt("CODE_VILLE");
				int pop = rs.getInt("POP");
				int idDep = rs.getInt("ID_DEP");

				liste.add(new Ville(nomVille, codeVille, pop, idDep));
			}

		} catch (SQLException e) {
			try {
				connect.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				LOG.error(e1.getMessage());
			}
			LOG.error(e.getMessage());
		} finally {

			try {
				if (rs != null) {
				rs.close();
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();

			}
		}

		return liste;
	}

	@Override
	public void insert(Ville ville) {

		if (connect == null) {
			connect = Connectionbdd.newConnect();
		}

		String nomVille = ville.getNom();
		int codeVille = Integer.parseInt(ville.getCodeVille());
		int pop = ville.getPopulation();
		int idDep = ville.getIdDep();

		try {

			if (rechercheVille(nomVille) == null) {

				stInsert.setString(1, nomVille);
				stInsert.setInt(2, codeVille);
				stInsert.setInt(3, pop);
				stInsert.setInt(4, idDep);

				stInsert.executeUpdate();

				connect.commit();

			}

		} catch (SQLException e) {
			try {
				connect.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				LOG.error(e1.getMessage());
			}
			LOG.error(e.getMessage());
		}

	}

	@Override
	public int update(String ancienNom, String nouveauNom) {

		if (connect == null) {
			connect = Connectionbdd.newConnect();
		}

		int up = 0;

		try {

			stUpdateNom.setString(1, nouveauNom);
			stUpdateNom.setString(2, ancienNom);

			up = stUpdateNom.executeUpdate();

			connect.commit();

		} catch (SQLException e) {
			try {
				connect.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				LOG.error(e1.getMessage());
			}
			LOG.error(e.getMessage());
		}

		return up;
	}

	@Override
	public boolean delete(Ville ville) {

		if (connect == null) {
			connect = Connectionbdd.newConnect();
		}

		boolean del = false;

		try {

			stDelete.setString(1, ville.getNom());
			stDelete.executeUpdate();

			del = true;

		} catch (SQLException e) {
			try {
				connect.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				LOG.error(e1.getMessage());
			}
			LOG.error(e.getMessage());
		}

		return del;
	}

	@Override
	public Ville rechercheVille(String nomVille) {

		if (connect == null) {
			connect = Connectionbdd.newConnect();
		}

		ResultSet rs = null;
		Ville ville = null;

		try {

			stRechVille.setString(1, nomVille);
			rs = stRechVille.executeQuery();

			if (rs.next()) {

				String nomV = rs.getString("NOM_VILLE");
				int codeVille = rs.getInt("CODE_VILLE");
				int pop = rs.getInt("POP");
				int idDep = rs.getInt("ID_DEP");

				ville = new Ville(nomV, codeVille, pop, idDep);
			}

			

		} catch (SQLException e) {
			try {
				connect.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				LOG.error(e1.getMessage());
			}
			LOG.error(e.getMessage());
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				LOG.error(e.getMessage());
			}
		}
		return ville;
	}

	@Override
	public List<Ville> villeBorne(int a, int b, int iDep) {

		if (connect == null) {
			connect = Connectionbdd.newConnect();
		}

		List<Ville> listeVille = new ArrayList<>();
		ResultSet rs = null;
		
	

		try {
			stBorneVille.setInt(1, iDep);

			stBorneVille.setInt(2, a);
			stBorneVille.setInt(3, b);
			
			rs = stBorneVille.executeQuery();

			while (rs.next()) {

				String nomV = rs.getString("NOM_VILLE");
				int codeVille = rs.getInt("CODE_VILLE");
				int pop = rs.getInt("POP");
				int idDep = rs.getInt("ID_DEP");

				listeVille.add(new Ville(nomV, codeVille, pop, idDep));
			}

		} catch (SQLException e) {
			try {
				connect.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				LOG.error(e1.getMessage());
			}
			LOG.error(e.getMessage());
		} finally {
			try {
				if (rs != null) {
				rs.close();
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				LOG.error(e.getMessage());
			}
		}

		return listeVille;
	}

}
