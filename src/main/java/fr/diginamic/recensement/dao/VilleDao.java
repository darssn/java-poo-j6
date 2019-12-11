package fr.diginamic.recensement.dao;

import java.util.List;
import fr.diginamic.recensement.entites.Ville;



public interface VilleDao {

	List<Ville> extraire();

	void insert(Ville ville);

	int update(String ancienNom, String nouveauNom);

	boolean delete(Ville ville);
	
	Ville rechercheVille(String nomVille);
	
	
	List<Ville> villeBorne(int a,int b,int iDep);

}
