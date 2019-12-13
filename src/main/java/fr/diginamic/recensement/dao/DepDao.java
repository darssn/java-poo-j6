package fr.diginamic.recensement.dao;

import java.util.List;

import fr.diginamic.recensement.entites.Departement;

public interface DepDao {

	/**
	 * Extrait une liste de departements de la base de donnees
	 * 
	 * @return List<Departement>
	 */
	List<Departement> extraire();

	/**
	 * Ajoute un Departement dans la base de données avec 1 param
	 * 
	 * @param dep  Departement
	 */
	void insert(Departement dep);

	/**
	 * Modifie le nom d'un departement dans la base de données avec 2 param
	 * 
	 * @param ancienNom String nom a modifié
	 * @param nouveauNom String nouveau nom a ajouté
	 * @return int nombre d'update
	 */
	int update(String ancienNom, String nouveauNom);

	/**
	 * Supprime un Departement avec 1 param
	 * 
	 * @param dep Departement
	 * @return booleen si la suppression a ete effectué
	 */
	boolean delete(Departement dep);

	/**
	 * Recherche un departement avec 1 variable
	 * 
	 * @param nomDep nom du departement
	 * @return un Departement si existant
 	 */
	Departement rechercheDep(String nomDep);

	/**
	 * Calcul de la population d'un departement avec 1 param
	 * 
	 * @param id int id d'un departement
	 * @return int la population totale d'un deparement
	 */
	int calcPop(int id);

	

	/**
	 * Fermeture de la connection et des PreparedStatement
	 */

	void close();

}
