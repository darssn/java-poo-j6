package fr.diginamic.recensement.entites;

import java.util.ArrayList;
import java.util.List;

/** Représente un département
 * @author DIGINAMIC
 *
 */
public class Departement implements EnsemblePop {

	/** code : String*/
	private String code;
	/** population : int*/
	private int population;
	
	private int codeReg = 0;
	
	/** villes : List<Ville>*/
	private List<Ville> villes = new ArrayList<>();
	
	/** Constructeur
	 * @param code code
	 */
	public Departement(String code) {
		super();
		this.code = code;
	}
	
	public Departement(String code,int codeR) {
		super();
		this.code = code;
		this.codeReg = codeR;
	}
	
	
	
	/** Ajoute une ville
	 * @param ville ville
	 */
	public void addVille(Ville ville){
		villes.add(ville);
		this.population=this.population+ville.getPopulation();
	}
	
	/** Getter
	 * @return the code
	 */
	public String getCode() {
		return code;
	}
	/** Setter
	 * @param code the code to set
	 */
	public void setCode(String code) {
		this.code = code;
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
	
	public static boolean codeExists(Recensement rec,String a){
		
		boolean e = false;
		
		List<Ville> villes = rec.getVilles();
		
		for(Ville v : villes){
			
			if(v.getCodeDepartement().equals(a)){
				
				return true;
			}
			
		}
		
		return e;
		
		
	}

	/**Getter
	 * @return the codeReg
	 */
	public int getCodeReg() {
		return codeReg;
	}

	/**Setter
	 * @param codeReg the codeReg to set
	 */
	public void setCodeReg(int codeReg) {
		this.codeReg = codeReg;
	}

	
}
