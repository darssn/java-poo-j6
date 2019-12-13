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
	
	/**idDep : int 
	 * */
	private int idDep = 0;
	
	/**idReg : int
	 * */
	private int idReg = 0;
	
	/** villes : List<Ville>*/
	private List<Ville> villes = new ArrayList<>();
	
	/** Constructeur
	 * @param code codeR
	 */
	public Departement(String code,int codeR) {
		super();
		this.code = code;
		this.idReg = codeR;
	}
	/** Constructeur
	 * @param id code codeR
	 */
	
	public Departement(int id,String code,int codeR) {
		super();
		this.idDep = id;
		this.code = code;
		this.idReg = codeR;
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
	
	/**Permet de voir si un departement existe 
	 * @param rec Recensement
	 * @param a  String code du departement
 	 * @return booleen
	 * */
	
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
	public int getIdReg() {
		return idReg;
	}

	/**Setter
	 * @param codeReg the codeReg to set
	 */
	public void setIdReg(int codeReg) {
		this.idReg = codeReg;
	}

	/**Getter
	 * @return the idDep
	 */
	public int getIdDep() {
		return idDep;
	}

	/**Setter
	 * @param idDep the idDep to set
	 */
	public void setIdDep(int idDep) {
		this.idDep = idDep;
	}

	
}
