package fr.diginamic.recensement.dao;

import java.util.List;

import fr.diginamic.recensement.entites.Region;




public interface RegionDao {

	/** Extrait une liste de region de la base de données
	 * @return List<Region>
	 */
	List<Region> extraire();
	
	/**Ajoute une Region dans la base de données
	 * @param Region
	 */
	void insert(Region region);
	
	/** Modifie le nom d'une region dans la base de données avec 2 paramètre
	 * @param ancienNom nom a modifier dans la base de données
	 * @param nouveauNom nom modifié
	 * @return int nombre de update 
	 */
	int update(String ancienNom , String nouveauNom);
	
	/** Supprime une Region dans la base de données avec 1 paramètre
	 * @param Region  
	 * @return booleen si la suppression a été réussi
	 */
	boolean delete(Region region);
	
	/**Recherche une region en fonction de son code region
	 * @param codeR int code de la region
	 * @return int une Region si existe
	 */
	Region rechercheReg(int codeR);
	
	/**Calcul la population d'une region a partir d'un code region
	 * @param codeR int ciode region
	 * @return int total de la population de la region
	 */
	int calcPopRegion(int codeR);
	
	
	/**Fermeture de la connection et des PreparedStatement
	 * 
	 */
	void close();
	
}
