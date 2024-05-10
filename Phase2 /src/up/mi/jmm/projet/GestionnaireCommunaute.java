package up.mi.jmm.projet;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.StringTokenizer;

/**
 * GestionnaireCommunaute est la classe principale qui gère l'interface
 * utilisateur pour la gestion d'une communauté d'agglomération. Elle permet de
 * résoudre manuellement ou automatiquement la distribution des zones de
 * recharge, de sauvegarder l'état actuel et de quitter le programme.
 */
public class GestionnaireCommunaute {
	private static final String INVALIDE_CHOIX_MSG = "Choix non valide. Veuillez choisir à nouveau.";
	private static final Scanner scanner = new Scanner(System.in);

	/**
	 * Point d'entrée principal du programme. Il initialise le processus en
	 * restaurant l'état d'une communauté d'agglomération à partir d'un fichier et
	 * affiche le menu principal.
	 * 
	 * @param args Arguments de la ligne de commande. args[0] doit être le chemin du
	 *             fichier pour restaurer la communauté d'agglomération.
	 */
	public static void main(String[] args) {
		CommunauteAgglomeration ca = restaurer(args[0]);
		if (ca == null) {
			System.err.println("Erreur lors de la restauration de la communauté d'agglomération.");
			return; // Quitte le programme car la communauté d'agglomération n'est pas valide
		}
		afficherMenuPrincipal(ca);
	}

	/**
	 * Affiche le menu principal permettant à l'utilisateur de choisir entre
	 * diverses options de gestion de la communauté d'agglomération.
	 * 
	 * @param ca L'instance de CommunauteAgglomeration à gérer.
	 */
	private static void afficherMenuPrincipal(CommunauteAgglomeration ca) {
		int choix = 0;
		do {
			System.out.println("(1) Résoudre manuellement");
			System.out.println("(2) Résoudre automatiquement");
			System.out.println("(3) Sauvegarder");
			System.out.println("(4) Quitter");
			System.out.print("Choix : ");

			try {
				choix = scanner.nextInt();
			} catch (Exception e) {
				System.out.println(INVALIDE_CHOIX_MSG);
				scanner.nextLine(); // Pour consommer le reste de la ligne et éviter une boucle infinie
				continue; // Pour retourner au début de la boucle do-while
			}

			switch (choix) {
			case 1:
				gererManuellement(ca);
				break;
			case 2:
				TestAlgorithme.algoOptimal(ca);
				System.out.println(ca.villesAvecZoneDeRecharge());
				break;
			case 3:
				System.out.print("Entrez le chemin du fichier pour la sauvegarde : ");
				scanner.nextLine(); // Pour consommer le reste de la ligne après nextInt
				String cheminFichier = scanner.nextLine();
				sauvegarder(ca, cheminFichier);
				break;
			case 4:
				System.out.println("Fin du programme.");
				break;
			default:
				System.out.println(INVALIDE_CHOIX_MSG);
			}
		} while (choix != 4);
	}

	/**
	 * Gère la résolution manuelle du problème des zones de recharge dans la
	 * communauté d'agglomération. Permet à l'utilisateur d'ajouter ou de retirer
	 * des zones de recharge selon son choix.
	 *
	 * @param ca L'instance de CommunauteAgglomeration à gérer.
	 */
	private static void gererManuellement(CommunauteAgglomeration ca) {
		int choix = 0;
		do {
			afficherMenuManuel();

			try {
				choix = scanner.nextInt();
			} catch (Exception e) {
				System.out.println(INVALIDE_CHOIX_MSG);
				scanner.nextLine(); // Pour consommer le reste de la ligne et éviter une boucle infinie
				continue; // Pour retourner au début de la boucle do-while
			}

			scanner.nextLine(); // Consommer le reste de la ligne après le nombre

			switch (choix) {
			case 1:
				gererZonesRecharge(ca);
				break;
			case 2:
				System.out.println("Retour au menu principal.");
				break;
			default:
				System.out.println(INVALIDE_CHOIX_MSG);
			}
		} while (choix != 2);
	}

	/**
	 * Affiche un menu secondaire pour la gestion manuelle des zones de recharge.
	 */

	private static void afficherMenuManuel() {
		System.out.println("(1) Gérer les zones de recharge");
		System.out.println("(2) Quitter");
		System.out.print("Choix : ");
	}

	/**
	 * Permet à l'utilisateur de gérer spécifiquement les zones de recharge au sein
	 * de la communauté d'agglomération. L'utilisateur peut ajouter ou retirer des
	 * zones de recharge dans les villes individuelles.
	 *
	 * @param ca L'instance de CommunauteAgglomeration concernée.
	 */

	private static void gererZonesRecharge(CommunauteAgglomeration ca) {
		int choix = 0;
		do {
			System.out.println("(1) Ajouter une zone de recharge");
			System.out.println("(2) Retirer une zone de recharge");
			System.out.println("(3) Retour au menu principal");
			System.out.print("Choix : ");

			try {
				choix = scanner.nextInt();
			} catch (Exception e) {
				System.out.println(INVALIDE_CHOIX_MSG);
				scanner.nextLine(); // Pour consommer le reste de la ligne et éviter une boucle infinie
				continue; // Pour retourner au début de la boucle do-while
			}

			scanner.nextLine(); // Consommer le reste de la ligne après le nombre

			switch (choix) {
			case 1:
				System.out.print("Nom de la ville : ");
				String villeAjout = scanner.nextLine();
				try {
					ca.ajouterZoneRecharge(villeAjout);

				} catch (Exception e) {
					System.out.println(e.getMessage());
				}
				break;
			case 2:
				System.out.print("Nom de la ville : ");
				String villeRetrait = scanner.nextLine();
				try {
					ca.supprimerZoneRecharge(villeRetrait);
				} catch (Exception e) {
					System.out.println(e.getMessage());
				}
				break;
			case 3:
				break;
			default:
				System.out.println(INVALIDE_CHOIX_MSG);
			}
		} while (choix != 3);
	}

	/**
	 * Restaure l'état d'une communauté d'agglomération à partir d'un fichier de
	 * sauvegarde spécifié. Lit le fichier et reconstruit la structure de la
	 * communauté d'agglomération, y compris les villes, les routes et les zones de
	 * recharge.
	 *
	 * @param nomFichier Le chemin du fichier à partir duquel restaurer la
	 *                   communauté d'agglomération.
	 * @return La communauté d'agglomération restaurée.
	 */
	public static CommunauteAgglomeration restaurer(String nomFichier) { // creer agglomeration

		CommunauteAgglomeration ca = null;

		try {
			FileReader fr = new FileReader(nomFichier);
			BufferedReader br = new BufferedReader(fr);
			String ligne = null;
			String methode = null;
			String nomV = null;
			String nomV1 = null;
			String nomV2 = null;
			ca = new CommunauteAgglomeration();

			while ((ligne = br.readLine()) != null) {
				StringTokenizer st = new StringTokenizer(ligne, "(,).");
				methode = st.nextToken();

				switch (methode) {
				case "ville":
					nomV = st.nextToken();

					ca.ajouterVille(new Ville(ca.getTaille(), nomV));

					break;
				case "route":

					nomV1 = st.nextToken();

					nomV2 = st.nextToken();
					ca.ajouterUneRoute(ca.getVille(nomV1), ca.getVille(nomV2));
					break;
				case "recharge":
					nomV = st.nextToken();
					ca.ajouterZoneRecharge(nomV);
					break;

				}

			}
			br.close();
			fr.close();
			// System.out.println(ca.getMatrice());

		} catch (FileNotFoundException e) {
			System.err.println("Erreur : Le fichier '" + nomFichier + "' n'a pas été trouvé.");
			return null;
		} catch (IOException e) {
			System.err.println("Erreur lors de la lecture du fichier : " + e.getMessage());
			return null;
		} catch (Exception e) {
			System.err.println("Erreur : " + e.getMessage());
			return null;

		}
		boolean aDesZonesDeRecharge = false;
		boolean respecteContrainteAccessibilite = true;

		for (Ville ville : ca.getListeVille()) {
			if (ville.AZoneDeRecharge()) {
				aDesZonesDeRecharge = true;
			}
			if (!verifierContrainteAccessibilite(ville, ca)) {
				System.out.println("Ne verfie pas la contrainte");
				respecteContrainteAccessibilite = false;
				break; // Pas besoin de continuer si une ville viole la contrainte
			}
		}

		if (!aDesZonesDeRecharge || !respecteContrainteAccessibilite) {
			appliquerSolutionNaive(ca);
		}

		return ca;

	}

	/**
	 * Applique une solution naïve pour la gestion des zones de recharge dans la
	 * communauté d'agglomération. Cette méthode ajoute une zone de recharge dans
	 * chaque ville de la communauté.
	 *
	 * @param ca La communauté d'agglomération dont les villes doivent recevoir une
	 *           zone de recharge.
	 */

	private static void appliquerSolutionNaive(CommunauteAgglomeration ca) {
		for (Ville ville : ca.getListeVille()) {
			ville.ajouterRecharge();
			;
		}
	}

	/**
	 * Vérifie si une ville donnée respecte la contrainte d'accessibilité en matière
	 * de zones de recharge. Une ville respecte cette contrainte si elle possède une
	 * zone de recharge ou si elle est directement reliée à une ville qui en possède
	 * une.
	 *
	 * @param ville La ville à vérifier.
	 * @param ca    La communauté d'agglomération dans laquelle la ville est située.
	 * @return true si la ville respecte la contrainte d'accessibilité, false sinon.
	 */
	public static boolean verifierContrainteAccessibilite(Ville ville, CommunauteAgglomeration ca) {

		if (ville.AZoneDeRecharge()) {
			return true;
		}

		// Vérifie si la ville est directement reliée à une ville avec une zone de
		// recharge
		List<Ville> listeVilles = ca.getListeVille();
		List<ArrayList<Integer>> matrice = ca.getMatrice();
		int indiceVille = listeVilles.indexOf(ville);

		for (int i = 0; i < listeVilles.size(); i++) {
			if (matrice.get(indiceVille).get(i) == 1 && listeVilles.get(i).AZoneDeRecharge()) {
				// La ville est reliée à une ville avec une zone de recharge
				return true;
			}
		}

		// La ville n'est reliée à aucune ville avec une zone de recharge
		return false;
	}

	/**
	 * Sauvegarde l'état actuel de la communauté d'agglomération dans un fichier
	 * spécifié par l'utilisateur. Écrit les détails des villes, des routes et des
	 * zones de recharge dans le fichier de sauvegarde.
	 *
	 * @param ca            La communauté d'agglomération à sauvegarder.
	 * @param cheminFichier Le chemin du fichier où la communauté d'agglomération
	 *                      doit être sauvegardée.
	 */

	private static void sauvegarder(CommunauteAgglomeration ca, String cheminFichier) {
		try (BufferedWriter writer = new BufferedWriter(new FileWriter(cheminFichier))) {
			// Sauvegarder les villes
			for (Ville ville : ca.getListeVille()) {
				writer.write("ville(" + ville.getNom() + ").");
				writer.newLine();
			}

			// Sauvegarder les routes
			for (int i = 0; i < ca.getTaille(); i++) {
				for (int j = i + 1; j < ca.getTaille(); j++) {
					if (ca.getMatrice().get(i).get(j) == 1) {
						writer.write("route(" + ca.getListeVille().get(i).getNom() + ","
								+ ca.getListeVille().get(j).getNom() + ").");
						writer.newLine();
					}
				}
			}

			// Sauvegarder les zones de recharge
			for (Ville ville : ca.getListeVille()) {
				if (ville.AZoneDeRecharge()) {
					writer.write("recharge(" + ville.getNom() + ").");
					writer.newLine();
				}
			}

			System.out.println("Sauvegarde réussie dans le fichier : " + cheminFichier);
		} catch (IOException e) {
			System.out.println("Erreur lors de la sauvegarde : " + e.getMessage());
		}
	}

}
