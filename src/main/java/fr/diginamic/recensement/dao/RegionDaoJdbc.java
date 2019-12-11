package fr.diginamic.recensement.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import fr.diginamic.recensement.utils.Connectionbdd;

import fr.diginamic.recensement.entites.Region;

public class RegionDaoJdbc implements RegionDao {

	@Override
	public List<Region> extraire() {

		List<Region> liste = new ArrayList<>();

		Connection connect = Connectionbdd.newConnect();

		try {
			Statement st = connect.createStatement();

			ResultSet rs = st.executeQuery("select * from region");

			while (rs.next()) {

				String codeReg = Integer.toString(rs.getInt("CODE_REG"));
				String nomReg = rs.getString("NOM_REG");

				liste.add(new Region(codeReg, nomReg));
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
	public void insert(Region region) {

		Connection connect = Connectionbdd.newConnect();
		Statement st = null;
		ResultSet rs = null;

		try {

			st = connect.createStatement();

			rs = st.executeQuery(

					"SELECT * from region where CODE_REG =" + Integer.parseInt(region.getCode()) + " AND NOM_REG='"
							+ region.getNom() + "'");

			if (!rs.next()) {

				st.executeUpdate("Insert into region (CODE_REG,NOM_REG)values(" + Integer.parseInt(region.getCode())
						+ ",'" + region.getNom() + "')");

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

			up = st.executeUpdate("update region set NOM_REG='"+nouveauNom+"' where NOM_REG='"+ancienNom+"'");

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
	public boolean delete(Region region) {
		
		Connection connect = Connectionbdd.newConnect();
		Statement st = null;


		try {

			st = connect.createStatement();

		

				st.executeUpdate("delete from region where NOM_REG ='"+region.getNom()+"'");
			
			
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
	public int rechercheIdReg(String codeR) {
		
		Connection connect = Connectionbdd.newConnect();
		int idReg = 0;

		try {
			Statement st = connect.createStatement();

			ResultSet rs = st.executeQuery("select ID_REG from region where CODE_REG ='" + codeR + "'");

			if (rs.next()) {

				
				idReg = rs.getInt("ID_REG");

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
	
		
		
		return idReg;
	}

	@Override
	public int calcPopRegion(int idR) {
		
				Connection connect = Connectionbdd.newConnect();
		Statement st = null;
		ResultSet rs = null;
		int totalPop = 0 ;
	
		try {

			st = connect.createStatement();
			rs = st.executeQuery("SELECT SUM(POP) FROM ville,departement WHERE departement.ID_REG ="+idR+" AND departement.ID_DEP = ville.ID_DEP");
			
			if(rs.next()){
				
				totalPop = rs.getInt("SUM(POP)");
			}
			
			
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
				//rs.close();
				st.close();
				connect.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		
		return totalPop;
	}

}
