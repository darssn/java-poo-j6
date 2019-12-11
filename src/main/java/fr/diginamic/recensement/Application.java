package fr.diginamic.recensement;


import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;



import fr.diginamic.recensement.dao.DepDaoJdbc;
import fr.diginamic.recensement.dao.RegionDaoJdbc;
import fr.diginamic.recensement.dao.VilleDaoJdbc;
import fr.diginamic.recensement.entites.Recensement;
import fr.diginamic.recensement.entites.Ville;
import fr.diginamic.recensement.utils.RecensementUtils;

/**
 * Application de traitement des données de recensement de population
 * 
 * @param args
 */
public class Application {

	/**
	 * Point d'entrée
	 * 
	 * @param args
	 *            arguments (non utilisés ici)
	 */
	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
	
		
		String path = ClassLoader.getSystemClassLoader().getResource("recensement.csv").getFile();
		
		Recensement recensement = RecensementUtils.lire(path);
		

		if (recensement == null) {
			System.out.println("L'application doit s'arrétée en raison d'une erreur d'exécution.");
			System.exit(0);
		}

		// Menu
		int choix = 0;
		VilleDaoJdbc vDao = new VilleDaoJdbc();
		RegionDaoJdbc rDao = new RegionDaoJdbc();
		DepDaoJdbc dDao = new DepDaoJdbc();
		String recherche = null;
		long total = 0;
		List <Ville> lV = new ArrayList<>();
		do {

			// Affichage du menu
			afficherMenu();

			// Poser une question à l'utilisateur
			String choixMenu = scanner.nextLine();

			// Conversion du choix utilisateur en int
			choix = Integer.parseInt(choixMenu);

			// On exécute l'option correspondant au choix de l'utilisateur
			switch (choix) {
			case 1:
				
					
				System.out.println("Quel est le nom de la ville recherchée ? ");
				
			 recherche = scanner.nextLine();
				
				Ville v = vDao.rechercheVille(recherche);
				
				System.out.println(v);
								
				
				break;
			case 2:
				
				System.out.println("Quel est le code du département recherché ? ");
				recherche = scanner.nextLine();
				total  =  dDao.calcPop(dDao.rechercheIdDep(recherche));
				System.out.println(total);
				
				break;
			case 3:
							
				System.out.println("Quel est le code de la région recherchée ? ");
				recherche = scanner.nextLine();
				total  =  rDao.calcPopRegion(rDao.rechercheIdReg(recherche));
				System.out.println(total);
				
				break;
			case 4:
				
				System.out.println("Quel est le code du département recherché ? ");		
				recherche = scanner.nextLine();
				System.out.println("Choississez une population minimum (en milliers d'habitants): ");
				String saisieMin = scanner.nextLine();			
				System.out.println("Choississez une population maximum (en milliers d'habitants): ");
				String saisieMax = scanner.nextLine();
				
				lV = vDao.villeBorne(Integer.parseInt(saisieMin), Integer.parseInt(saisieMax),dDao.rechercheIdDep(recherche));
				
				for(Ville vi : lV){
					System.out.println(vi);
				}
				
	
				break;
			}
		} while (choix != 99);

		scanner.close();

	}

	/**
	 * Affichage du menu
	 */
	private static void afficherMenu() {
		System.out.println("***** Recensement population *****");
		System.out.println("1. Rechercher la population d'une ville");
		System.out.println("2. Rechercher la population d'un département");
		System.out.println("3. Rechercher la population d'une région");
		System.out.println("4. Rechercher la population des villes d'un dept entre min et max");
		System.out.println("99. Sortir");
	}
}
