package fr.diginamic.recensement.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fr.diginamic.recensement.entites.Departement;
import fr.diginamic.recensement.utils.Connectionbdd;

public class DepDaoJdbc implements DepDao {

	private static final Logger LOG = LoggerFactory.getLogger(DepDaoJdbc.class);

	public Connection connect = null;
	public PreparedStatement stInsert;
	public PreparedStatement stUpdateNom;
	public PreparedStatement stDelete;
	public PreparedStatement stAffAll;
	public PreparedStatement stRechDep;
	public PreparedStatement stCalcPopDep;

	public DepDaoJdbc() {

		connect = Connectionbdd.newConnect();

		try {
			stInsert = connect.prepareStatement("Insert into departement (CODE_DEP,ID_REG)values(?,?)");
			stUpdateNom = connect.prepareStatement("update departement set CODE_DEP=? where CODE_DEP=?");
			stDelete = connect.prepareStatement("delete from departement where CODE_DEP =?");
			stAffAll = connect.prepareStatement("select * from departement");
			stRechDep = connect.prepareStatement("select * from departement where CODE_DEP =?");
			stCalcPopDep = connect.prepareStatement("select SUM(POP) from ville where ID_DEP = ?");

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			throw new RuntimeException(e);
		}
	}

	@Override
	public void close() {
		try {

			stInsert.close();
			stUpdateNom.close();
			stDelete.close();
			stAffAll.close();
			stRechDep.close();
			stCalcPopDep.close();

			if (connect != null) {
				connect.close();
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			throw new RuntimeException(e);
		}

	}

	@Override
	public List<Departement> extraire() {

		if (connect == null) {

			connect = Connectionbdd.newConnect();

		}

		ResultSet rs = null;
		List<Departement> liste = new ArrayList<>();

		try {

			rs = stAffAll.executeQuery();

			while (rs.next()) {

				String codeDep = rs.getString("CODE_DEP");
				int idReg = rs.getInt("ID_REG");
				int idDep = rs.getInt("ID_DEP");

				liste.add(new Departement(idDep, codeDep, idReg));
			}

			rs.close();

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

		return liste;
	}

	@Override
	public void insert(Departement dep) {

		if (connect == null) {
			connect = Connectionbdd.newConnect();
		}

		ResultSet rs = null;

		try {

			if (rechercheDep(dep.getCode()) == null) {

				stInsert.setString(1, dep.getCode());
				stInsert.setInt(2, dep.getIdReg());
				stInsert.executeUpdate();

			}

			connect.commit();

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
	public boolean delete(Departement dep) {
		if (connect == null) {
			connect = Connectionbdd.newConnect();

		}

		boolean del = false;

		try {

			stDelete.setString(1, dep.getCode());
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
	public Departement rechercheDep(String nomDep) {
		
		if (connect == null) {
			connect = Connectionbdd.newConnect();

		}

		Departement dep = null;
		ResultSet rs = null;

		try {

			stRechDep.setString(1, nomDep);
			rs = stRechDep.executeQuery();

			if (rs.next()) {

				dep = new Departement(rs.getInt("ID_DEP"), rs.getString("CODE_DEP"), rs.getInt("ID_REG"));

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

		return dep;
	}

	@Override
	public int calcPop(int id) {

		if (connect == null) {
			connect = Connectionbdd.newConnect();
		}

		ResultSet rs = null;
		int totalPop = 0;


			
			try {

				stCalcPopDep.setInt(1, id);
				rs = stCalcPopDep.executeQuery();

				if (rs.next()) {

					totalPop = rs.getInt("SUM(POP)");

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

		return totalPop;
	}

}
