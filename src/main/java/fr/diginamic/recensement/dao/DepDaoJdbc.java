package fr.diginamic.recensement.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import fr.diginamic.recensement.entites.Departement;

import fr.diginamic.recensement.utils.Connectionbdd;

public class DepDaoJdbc implements DepDao{

	@Override
	public List<Departement> extraire() {
		
		List<Departement> liste = new ArrayList<>();

		Connection connect = Connectionbdd.newConnect();

		try {
			Statement st = connect.createStatement();

			ResultSet rs = st.executeQuery("select * from departement");

			while (rs.next()) {

				
				String codeDep = rs.getString("CODE_DEP");
				int idReg = rs.getInt("ID_REG");

				liste.add(new Departement(codeDep,idReg));
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
	public void insert(Departement dep) {

		Connection connect = Connectionbdd.newConnect();
		Statement st = null;
		ResultSet rs = null;

		try {

			st = connect.createStatement();

			rs = st.executeQuery(

					"SELECT * from departement where CODE_DEP ='"+dep.getCode() +"'" );

			if (!rs.next()) {

				st.executeUpdate("Insert into departement (CODE_DEP,ID_REG)values('" + dep.getCode()+ "'," + dep.getCodeReg()+")");

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

			up = st.executeUpdate("update departement set CODE_DEP='"+nouveauNom+"' where CODE_DEP='"+ancienNom+"'");

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
	public boolean delete(Departement dep) {
		Connection connect = Connectionbdd.newConnect();
		Statement st = null;
	

		try {

			st = connect.createStatement();

		

				st.executeUpdate("delete from departement where CODE_DEP ='"+dep.getCode()+"'");
			
			
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
	public int rechercheIdDep(String nomDep) {
		
		Connection connect = Connectionbdd.newConnect();
		int idDep = 0;

		try {
			Statement st = connect.createStatement();

			ResultSet rs = st.executeQuery("select ID_DEP from departement where CODE_DEP ='" + nomDep + "'");

			if (rs.next()) {

				
				idDep = rs.getInt("ID_DEP");

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
	
		
		return idDep;
	}

	@Override
	public int calcPop(int id) {
		
		Connection connect = Connectionbdd.newConnect();
		Statement st = null;
		ResultSet rs = null;
		int totalPop = 0 ;
	
		try {

			st = connect.createStatement();
			rs = st.executeQuery("select SUM(POP) from ville where ID_DEP =" + id );
			
		
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
			e.printStackTrace();
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
