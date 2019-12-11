package fr.diginamic.exemple.daos;

import java.util.ArrayList;
import java.util.List;

import fr.diginamic.exemple.entites.Ville;
import fr.diginamic.exemple.exceptions.CodeDeptNullException;

public class RechercherPopulationVillesDao {

	public List<Ville> extraireVilles(String codeDept) throws CodeDeptNullException {
		
		if (codeDept==null){
			throw new CodeDeptNullException("Veuillez renseigner un code département.");
		}
		
		List<Ville> villes = new ArrayList<Ville>();
		
		//TODO Accès BDD
		
		return villes;
	}
}
