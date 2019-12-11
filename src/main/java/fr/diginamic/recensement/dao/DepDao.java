package fr.diginamic.recensement.dao;

import java.util.List;


import fr.diginamic.recensement.entites.Departement;


public interface DepDao {
	
	List<Departement> extraire();
	
	void insert(Departement dep);
	
	int update(String ancienNom , String nouveauNom);
	
	boolean delete(Departement dep);
	
	int rechercheIdDep(String nomDep);
	
	int calcPop(int id);
	


}
