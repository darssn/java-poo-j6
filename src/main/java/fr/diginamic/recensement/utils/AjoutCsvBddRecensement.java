package fr.diginamic.recensement.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import fr.diginamic.recensement.dao.DepDaoJdbc;
import fr.diginamic.recensement.dao.RegionDaoJdbc;
import fr.diginamic.recensement.dao.VilleDaoJdbc;
import fr.diginamic.recensement.entites.Departement;
import fr.diginamic.recensement.entites.Recensement;
import fr.diginamic.recensement.entites.Region;
import fr.diginamic.recensement.entites.Ville;

public class AjoutCsvBddRecensement {

	private static final Logger LOG = LoggerFactory.getLogger(AjoutCsvBddRecensement.class);
	
	

	public static void create() {

		long before = System.currentTimeMillis();
		
		String path = ClassLoader.getSystemClassLoader().getResource("recensement.csv").getFile();

		Recensement liste = RecensementUtils.lire(path);


		RegionDaoJdbc rDao = new RegionDaoJdbc();
		DepDaoJdbc dDao = new DepDaoJdbc();
		VilleDaoJdbc vDao = new VilleDaoJdbc();
		

		for (Ville v : liste.getVilles()) {

			int codeReg = Integer.parseInt(v.getCodeRegion());
			String nomReg = v.getNomRegion().replace("'", "''");
			String codeDep = v.getCodeDepartement();
			int codeVille = Integer.parseInt(v.getCodeVille());
			String nomVille = v.getNom().replace("'", "''");
			int pop = v.getPopulation();

			try {

				if (rDao.rechercheReg(codeReg) == null) {
					rDao.insert(new Region(codeReg, nomReg));
				}

				int idReg = rDao.rechercheReg(codeReg).getIdReg();

				if (dDao.rechercheDep(codeDep) == null) {

					dDao.insert(new Departement(codeDep, idReg));

				}

				int idDep = dDao.rechercheDep(codeDep).getIdDep();

				if (vDao.rechercheVille(nomVille) == null) {

					vDao.insert(new Ville(nomVille, codeVille, pop, idDep));

				}

			} catch (Exception e) {
				
				LOG.error(e.getMessage());
			}

		}


		dDao.close();
		rDao.close();
		vDao.close();
		
		long after= System.currentTimeMillis();

		System.out.println("Ajout Terminé en "+(after-before));

	}

}
