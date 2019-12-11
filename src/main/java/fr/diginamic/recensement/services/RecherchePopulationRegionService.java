package fr.diginamic.recensement.services;

import java.util.List;
import java.util.Scanner;

import fr.diginamic.recensement.entites.Recensement;
import fr.diginamic.recensement.entites.Region;
import fr.diginamic.recensement.entites.Ville;

/** Recherche et affichage de la population d'une région
 * @author DIGINAMIC
 *
 */
public class RecherchePopulationRegionService extends MenuService {

	@Override
	public void traiter(Recensement rec, Scanner scanner) throws ExceptionR {
		
		System.out.println("Quel est le code de la région recherchée ? ");
		String choix = scanner.nextLine();
		
		if (!Region.regionExists(rec, choix)) {

			throw new ExceptionR("Veuillez entrez une region valable");

		}
		
		List<Ville> villes = rec.getVilles();
		int somme = 0;
		String nom = null;
		for (Ville ville: villes){
			if (ville.getCodeRegion().equalsIgnoreCase(choix)){
				somme+=ville.getPopulation();
				nom=ville.getNomRegion();
			}
		}
		if (somme>0){
			System.out.println("Population de la région "+nom+" : "+ somme);
		}
		else {
			System.out.println("Région "+choix+" non trouvée.");
		}
	}

}
