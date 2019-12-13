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
		List<Ville> lV = new ArrayList<>();
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

				if (v != null) {
					System.out.println(v);
				} else {
					System.out.println("Veuillez renseigner un nom de ville valide");
				}

				break;
			case 2:

				System.out.println("Quel est le code du département recherché ? ");
				recherche = scanner.nextLine();

				if (dDao.rechercheDep(recherche) != null) {
					total = dDao.calcPop(dDao.rechercheDep(recherche).getIdDep());
					System.out.println(total);

				} else {
					System.out.println("Veuillez renseigner un departement valide");
				}

				break;
			case 3:

				System.out.println("Quel est le code de la région recherchée ? ");
				recherche = scanner.nextLine();

				if (rDao.rechercheReg(Integer.parseInt(recherche)) != null) {
					total = rDao.calcPopRegion(rDao.rechercheReg(Integer.parseInt(recherche)).getIdReg());
					System.out.println(total);
				} else {
					System.out.println("Veuillez renseigner une region valide");
				}

				break;
			case 4:

				System.out.println("Quel est le code du département recherché ? ");
				recherche = scanner.nextLine();
				System.out.println("Choississez une population minimum (en milliers d'habitants): ");
				String saisieMin = scanner.nextLine();
				System.out.println("Choississez une population maximum (en milliers d'habitants): ");
				String saisieMax = scanner.nextLine();

				if (dDao.rechercheDep(recherche) != null) {
					lV = vDao.villeBorne(Integer.parseInt(saisieMin), Integer.parseInt(saisieMax),
							dDao.rechercheDep(recherche).getIdDep());

					for (Ville vi : lV) {
						System.out.println(vi);
					}
				} else {
					System.out.println("Veuillez renseigner un departement valide");
				}

				break;
			}
		} while (choix != 99);

		vDao.close();
		dDao.close();
		rDao.close();
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
