package fr.diginamic.exemple.services;

import java.util.List;

import fr.diginamic.exemple.daos.RechercherPopulationVillesDao;
import fr.diginamic.exemple.entites.Ville;
import fr.diginamic.exemple.exceptions.CodeDeptNullException;

public class RechercherPopulationDeptServices {

	
	public int rechercher(String codeDept){
		
		RechercherPopulationVillesDao dao = new RechercherPopulationVillesDao();
		try {
			int populationDept = 0;
			List<Ville> villes = dao.extraireVilles(codeDept);
			
			return populationDept;
		}
		catch (CodeDeptNullException e){
			System.out.println("Le code département n'est pas correct");
			return 0;
		}
		
		

		
	}
}
