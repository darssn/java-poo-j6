package fr.diginamic.recensement.entites;

import java.util.ArrayList;
import java.util.List;

/** Représente une région
 * @author DIGINAMIC
 *
 */
public class Region implements EnsemblePop {

	/** code : String*/
	private int code;
	/** nom : String*/
	private String nom;
	/** population : int*/
	private int population;
	
	private int idReg;
	
	/** villes : List<Ville>*/
	private List<Ville> villes = new ArrayList<>();
	
	/** Constructeur
	 * @param code code
	 * @param nom nom
	 */
	public Region(int idReg,int code, String nom) {
		super();
		this.code = code;
		this.nom = nom;
		this.idReg = idReg;
	}
	
	public Region(int code, String nom){
		
		super();
		this.code = code;
		this.nom = nom;
	}
	
	/** Ajoute une ville
	 * @param ville ville
	 */
	public void addVille(Ville ville){
		villes.add(ville);
		this.population=this.population+ville.getPopulation();
	}
	

	/** Getter
	 * @return the nom
	 */
	public String getNom() {
		return nom;
	}
	/** Setter
	 * @param nom the nom to set
	 */
	public void setNom(String nom) {
		this.nom = nom;
	}
	/** Getter
	 * @return the population
	 */
	public int getPopulation() {
		return population;
	}
	/** Setter
	 * @param population the population to set
	 */
	public void setPopulation(int population) {
		this.population = population;
	}
	
public static boolean regionExists(Recensement rec,String a){
		
		boolean e = false;
		
		List<Ville> villes = rec.getVilles();
		
		for(Ville v : villes){
			
			if(v.getNomRegion().equals(a)){
				
				return true;
			}
			
		}
		
		return e;
		
		
	}

/**Getter
 * @return the code
 */
public int getCode() {
	return code;
}

/**Setter
 * @param code the code to set
 */
public void setCode(int code) {
	this.code = code;
}

/**Getter
 * @return the idReg
 */
public int getIdReg() {
	return idReg;
}

/**Setter
 * @param idReg the idReg to set
 */
public void setIdReg(int idReg) {
	this.idReg = idReg;
}

	
}
