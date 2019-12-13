package fr.diginamic.recensement.dao;

import java.util.List;
import fr.diginamic.recensement.entites.Ville;



/**
 * @author Darss
 *
 */
public interface VilleDao {

	/**Extrait la liste de ville de la base de données
	 * @return List<Ville>
	 */
	List<Ville> extraire();

	/**Ajoute une ville dans la base de données
	 * @param Ville
	 */
	void insert(Ville ville);

	/**
	 * Modifie le nom d'une ville de la base de données
	 *
	 * @param ancienNom Nom de la ville a changer
	 * @param nouveauNom Nouveau nom de la ville
	 * @return int nombre de update reussi
	 */
	int update(String ancienNom, String nouveauNom);

	/**
	 * Supprime une ville de la base de données
	 * @param Ville prend une Ville en param
	 * @return boolean si la ville a bien ete supprimer de la base de données
	 */
	boolean delete(Ville ville);
	
	/**
	 * Recherche une ville a partir de son nom dans la base de données
	 * @param nomVille nom de la ville
	 * @return Ville
	 */
	Ville rechercheVille(String nomVille);
	
	
	/** Extrait une liste de ville en fonction du departement recherche ayant une population comprise entre 2 parametre
	 * @param a int 1ere borne 
	 * @param b int 2e borne
	 * @param iDep int ID_DEP id du departement
	 * @return List<Ville>
	 */
	List<Ville> villeBorne(int a,int b,int iDep);
	
	/**Fermeture de la connection de la base de données et des PreparedStatement
	 * 
	 */
	void close();

}
