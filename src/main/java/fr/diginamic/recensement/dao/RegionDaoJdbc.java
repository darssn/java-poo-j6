package fr.diginamic.recensement.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fr.diginamic.recensement.utils.Connectionbdd;

import fr.diginamic.recensement.entites.Region;

public class RegionDaoJdbc implements RegionDao {

	private static final Logger LOG = LoggerFactory.getLogger(RegionDaoJdbc.class);

	public Connection connect = null;
	public PreparedStatement stInsert;
	public PreparedStatement stUpdateNom;
	public PreparedStatement stDelete;
	public PreparedStatement stAffAll;
	public PreparedStatement stRechReg;
	public PreparedStatement stCalcPopReg;

	public RegionDaoJdbc() {

		connect = Connectionbdd.newConnect();

		try {
			stInsert = connect.prepareStatement("Insert into region (CODE_REG,NOM_REG)values(?,?)");
			stUpdateNom = connect.prepareStatement("update region set NOM_REG=? where NOM_REG=?");
			stDelete = connect.prepareStatement("delete from region where NOM_REG =?");
			stAffAll = connect.prepareStatement("select * from region");
			stRechReg = connect.prepareStatement("select * from region where CODE_REG =?");
			stCalcPopReg = connect.prepareStatement("SELECT SUM(POP) FROM ville,departement WHERE departement.ID_REG = ? AND departement.ID_DEP = ville.ID_DEP");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			throw new RuntimeException(e);
		}

	}

	@Override
	public void close() {
		// TODO Auto-generated method stub

		try {
			
			stInsert.close();
			stUpdateNom.close();
			stDelete.close();
			stAffAll.close();
			stRechReg.close();
			stCalcPopReg.close();
			
			if (connect != null) {
				connect.close();
			}
			
		} catch (

		SQLException e) {
			// TODO Auto-generated catch block
			throw new RuntimeException(e);
		}
	}

	@Override
	public List<Region> extraire() {

		List<Region> liste = new ArrayList<>();
		ResultSet rs = null;

		if (connect == null) {
			connect = Connectionbdd.newConnect();
		}

		try {

			rs = stAffAll.executeQuery();

			while (rs.next()) {

				int idReg = rs.getInt("ID_REG");
				int codeReg = rs.getInt("CODE_REG");
				String nomReg = rs.getString("NOM_REG");
				

				liste.add(new Region(idReg,codeReg, nomReg));
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

		return liste;
	}

	@Override
	public void insert(Region region) {

		if (connect == null) {
			connect = Connectionbdd.newConnect();
		}

		ResultSet rs = null;

		try {
			
			if (rechercheReg(region.getCode()) == null) {

				stInsert.setInt(1, region.getCode());
				stInsert.setString(2, region.getNom());
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
	public boolean delete(Region region) {

		if (connect == null) {
			connect = Connectionbdd.newConnect();
		}
		boolean del = false;

		try {

			stDelete.setString(1, region.getNom());
			stDelete.executeUpdate();

			del = true;

		} catch (SQLException e) {
			try {
				connect.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			System.out.println(e);
		}

		return del;
	}

	@Override
	public Region rechercheReg(int codeR) {

		if (connect == null) {
			connect = Connectionbdd.newConnect();
		}
		ResultSet rs = null;
		Region reg = null;

		try {

			stRechReg.setInt(1, codeR);
			rs = stRechReg.executeQuery();

			if (rs.next()) {

				reg = new Region(rs.getInt("ID_REG"), rs.getInt("CODE_REG"), rs.getString("NOM_REG"));

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

		return reg;
	}

	@Override
	public int calcPopRegion(int idR) {

		if (connect == null) {
			connect = Connectionbdd.newConnect();
		}

		ResultSet rs = null;
		int totalPop = 0;

		try {

			stCalcPopReg.setInt(1, idR);
			
			rs = stCalcPopReg.executeQuery();

			if (rs.next()) {

				totalPop = rs.getInt("SUM(POP)");
			}
			

		} catch (SQLException e) {
			try {
				connect.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				LOG.error(e.getMessage());
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
