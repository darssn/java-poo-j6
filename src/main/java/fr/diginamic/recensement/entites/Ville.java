package fr.diginamic.recensement.entites;

import java.util.List;

/** Représente une ville
 * @author DIGINAMIC
 *
 */
public class Ville implements EnsemblePop {
	
	/** codeRegion : code de la région */
	private String codeRegion =null;
	/** nomRegion : nom de la région */
	private String nomRegion = null;
	/** codeDepartement : code du département */
	private String codeDepartement =null;
	/** code INSEE de la ville */
	private String codeVille =null;
	/** nom de la ville */
	private String nom =null;
	/** population totale */
	private int population = 0;
	
	private int idDep = 0;	
	
	
	/** Constructeur
	 * @param codeRegion code de la région
	 * @param nomRegion nom de la région
	 * @param codeDepartement code du département
	 * @param codeVille code INSEE de la ville
	 * @param nom nom de la ville
	 * @param population population totale
	 */
	
	public Ville(String nomVille,int codeVille,int pop,int idDep){
		super();
		this.nom = nomVille;
		this.codeVille = Integer.toString(codeVille);
		this.population = pop;
		this.idDep = idDep;
		
		
	}
	public Ville(String codeRegion, String nomRegion, String codeDepartement, String codeVille, String nom, int population) {
		super();
		this.codeRegion=codeRegion;
		this.nomRegion=nomRegion;
		this.codeDepartement=codeDepartement;
		this.codeVille = codeVille;
		this.nom = nom;
		this.population = population;
	}
	

	
	@Override
	public String toString(){
		return nom + " " + population + " hab.";
	}

	/** Getter
	 * @return the codeRegion
	 */
	public String getCodeRegion() {
		return codeRegion;
	}

	/** Setter
	 * @param codeRegion the codeRegion to set
	 */
	public void setCodeRegion(String codeRegion) {
		this.codeRegion = codeRegion;
	}

	/** Getter
	 * @return the nomRegion
	 */
	public String getNomRegion() {
		return nomRegion;
	}

	/** Setter
	 * @param nomRegion the nomRegion to set
	 */
	public void setNomRegion(String nomRegion) {
		this.nomRegion = nomRegion;
	}

	/** Getter
	 * @return the codeDepartement
	 */
	public String getCodeDepartement() {
		return codeDepartement;
	}

	/** Setter
	 * @param codeDepartement the codeDepartement to set
	 */
	public void setCodeDepartement(String codeDepartement) {
		this.codeDepartement = codeDepartement;
	}

	/** Getter
	 * @return the codeVille
	 */
	public String getCodeVille() {
		return codeVille;
	}

	/** Setter
	 * @param codeVille the codeVille to set
	 */
	public void setCodeVille(String codeVille) {
		this.codeVille = codeVille;
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
	
	public static boolean villeExists(Recensement rec ,String a){
		
	boolean e = false;
		
		List<Ville> villes = rec.getVilles();
		
		for(Ville v : villes){
			
			if(v.getNom().equals(a)){
				
				return true;
			}
			
		}
		
		return e;
		
		
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
