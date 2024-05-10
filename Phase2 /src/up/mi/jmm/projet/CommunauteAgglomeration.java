
package up.mi.jmm.projet;

import java.util.List;
import java.util.ArrayList;

/**
 * Représente une communauté d'agglomération comprenant plusieurs villes et routes entre elles. 
 * Permet la gestion des villes, des routes, et des zones de recharge.
 */
public class CommunauteAgglomeration {

	private int taille; // Nombre de villes dans la communauté d'agglomération
	private List<ArrayList<Integer>> matrice; // Matrice d'adjacence représentant les routes entre les villes
	private List<Ville> listeVilles; // Liste des villes dans la communauté d'agglomération

	/**
	 * Constructeur de la classe CommunauteAgglomeration avec une taille spécifiée.
	 *
	 * @param taille Le nombre de villes dans la communauté d'agglomération.
	 */
	public CommunauteAgglomeration(int taille) {
		this.taille = taille;
		matrice = new ArrayList<ArrayList<Integer>>();
		listeVilles = new ArrayList<Ville>();
	}

	/**
	 * Obtient la taille de la communauté d'agglomération.
	 *
	 * @return La taille de la communauté d'agglomération.
	 */
	public int getTaille() {
		return taille;
	}

	/**
	 * Définit la taille de la communauté d'agglomération.
	 *
	 * @param taille La nouvelle taille de la communauté d'agglomération.
	 */
	public void setTaille(int taille) {
		this.taille = taille;
	}

	/**
	 * Constructeur de la classe CommunauteAgglomeration sans spécification de
	 * taille. Initialise la matrice d'adjacence et la liste des villes.
	 */
	public CommunauteAgglomeration() {
		matrice = new ArrayList<ArrayList<Integer>>();
		listeVilles = new ArrayList<Ville>();
		taille = 0;
	}

	 /**
     * Ajoute une ville à la communauté d'agglomération.
     * @param v La ville à ajouter.
     */
	public void ajouterVille(Ville v) {
		listeVilles.add(v);

		// Ajoute un zéro à la fin de chaque ligne dans la matrice
		for (ArrayList<Integer> ligne : matrice) {
			ligne.add(0);
		}

		// Ajoute une nouvelle ligne à la matrice avec des zéros
		ArrayList<Integer> prochaineLigne = new ArrayList<>();
		for (int i = 0; i < listeVilles.size(); i++) {
			prochaineLigne.add(0);
		}
		matrice.add(prochaineLigne);
		taille++;// ajout 1 a la taille
	}

	/**
	 * Retourne une chaîne de caractères contenant les noms des villes ayant une
	 * zone de recharge.
	 *
	 * @return Les noms des villes avec une zone de recharge.
	 */
	public String villesAvecZoneDeRecharge() {
		StringBuilder sb = new StringBuilder("");
		for (Ville v : listeVilles) {
			if (v.AZoneDeRecharge()) {
				sb.append(v.toString() + "\t");
			}
		}
		return sb.toString();
	}

	/**
	 * Obtient une instance de Ville en fonction de son nom.
	 *
	 * @param ville Le nom de la ville recherchée.
	 * @return Une instance de Ville correspondant au nom spécifié.
	 */
	public Ville getVille(String nomVille) {
		for (Ville v : listeVilles) {
			if (v.getNom().equals(nomVille)) {
				return v;
			}
		}
		return null; // Retourne null si la ville n'est pas trouvée
	}

	/**
	 * Ajoute une route entre deux villes de la communauté d'agglomération.
	 *
	 * @param ville1 La première ville.
	 * @param ville2 La deuxième ville.
	 */
	public void ajouterUneRoute(Ville ville1, Ville ville2) {
		int indiceVille1 = listeVilles.indexOf(ville1);
		int indiceVille2 = listeVilles.indexOf(ville2);

		if (indiceVille1 != -1 && indiceVille2 != -1) {
			matrice.get(indiceVille1).set(indiceVille2, 1);
			matrice.get(indiceVille2).set(indiceVille1, 1);
			ville1.ajoutVoisins();
			ville2.ajoutVoisins();
		}
	}

	/**
	 * Ajoute une zone de recharge à la ville spécifiée.
	 *
	 * @param nomVille Le nom de la ville à laquelle ajouter la zone de recharge.
	 * @throws Exception 
	 */
	public void ajouterZoneRecharge(String nomVille) throws Exception {
		Ville ville = getVille(nomVille);
		if (ville != null) {
			ville.ajouterRecharge();
			System.out.println(villesAvecZoneDeRecharge());
		}
		else {
			throw new Exception("Erreur : Ville non trouvée . Veuillez réessayer");
			
			
		}
		
	}

	/**
	 * Vérifie si une ville viole la contrainte d'accessibilité.
	 *
	 * @param ville1 La ville pour laquelle vérifier la contrainte.
	 * @return
	 */
	private boolean violeContrainte(Ville ville1) {
		int entier1 = ville1.getIndice();
		boolean bool = true;

		ArrayList<Integer> listec = matrice.get(entier1);
		for (int j = 0; j < listeVilles.size(); j++) {
			if (listec.get(j) == 1) {
				Ville v = listeVilles.get(j);
				if (v.AZoneDeRecharge()) {
					bool = false;
				}
			}
		}

		return bool; // si pas de voisin avec zone de recharge retourne true
	}

	/**
	 * Supprime la zone de recharge de la ville spécifiée si elle ne viole pas la
	 * contrainte d'accessibilité.
	 *
	 * @param nomVille Le nom de la ville dont supprimer la zone de recharge.
	 */
	/**
	 * Supprime la zone de recharge de la ville spécifiée si elle ne viole pas la
	 * contrainte d'accessibilité.
	 *
	 * @param nomVille Le nom de la ville dont supprimer la zone de recharge.
	 */
	public void supprimerZoneRecharge(String nomVille) throws Exception {
		Ville villeASupprimer = null;

		// Trouver la ville correspondant au nom
		for (Ville ville : listeVilles) {
			if (ville.getNom().equals(nomVille)) {
				villeASupprimer = ville;
				break;
			}
		}

		if (villeASupprimer == null) {
			throw new Exception("Erreur : Ville non trouvée . Veuillez réessayer");

		}

		// Sauvegarder les indices des villes voisines sans zone de recharge
		List<Integer> villesVoisinesSansRecharge = new ArrayList<>();
		int indiceVilleASupprimer = villeASupprimer.getIndice();

		for (int i = 0; i < listeVilles.size(); i++) {
			if (matrice.get(indiceVilleASupprimer).get(i) == 1 && !listeVilles.get(i).AZoneDeRecharge()) {
				villesVoisinesSansRecharge.add(i);
			}
		}

		// Supprimer la zone de recharge
		villeASupprimer.supprimerRecharge();

		// Vérifier la contrainte d'accessibilité pour les villes voisines sans zone de
		// recharge
		for (int indice : villesVoisinesSansRecharge) {
			Ville villeVoisine = listeVilles.get(indice);
			if (violeContrainte(villeVoisine)) {
				// Restaurer la zone de recharge précédemment supprimée
				villeASupprimer.ajouterRecharge();
				System.out.println("La suppression de la zone de recharge violerait la contrainte d'accessibilité.");
				return;
			}
		}

		System.out.println(villesAvecZoneDeRecharge());

	}

	/**
	 * Obtient la liste des villes dans la communauté d'agglomération.
	 *
	 * @return La liste des villes.
	 */
	public List<Ville> getListeVille() {
		return listeVilles;
	}

	/**
	 * Obtient la matrice d'adjacence représentant les routes entre les villes.
	 *
	 * @return La matrice d'adjacence.
	 */
	public List<ArrayList<Integer>> getMatrice() {
		return matrice;
	}

	/**
	 * Vérifie si la communauté d'agglomération a des villes non colorées.
	 *
	 * @return true si au moins une ville n'est pas colorée, sinon false.
	 */
	public boolean aDesVillesNonColores() { // Implementation pour vérifier si la communauté d'agglomération a des
											// villes non colorées

		boolean bool = false;
		for (int i = 0; i < listeVilles.size(); i++) {
			if (listeVilles.get(i).getCouleur() == -1) {
				bool = true;
			}
		}
		return bool;
	}

	/**
	 * Vérifie si deux villes sont voisines.
	 *
	 * @param v1 La première ville.
	 * @param v2 La deuxième ville.
	 * @return true si les deux villes sont voisines, sinon false.
	 */
	public boolean sontVoisins(Ville v1, Ville v2) {
		// Implementation pour vérifier si deux villes sont voisines

		return matrice.get(v1.getIndice()).get(v2.getIndice()) == 1
				&& matrice.get(v2.getIndice()).get(v1.getIndice()) == 1;

	}

	/**
	 * Calcule et retourne le score de la communauté d'agglomération.
	 *
	 * @return Le nombre de zones de recharge dans la communauté d'agglomération.
	 */

	public int score() { // Implementation pour calculer le score de la communauté d'agglomération

		int nbZoneDeRecharges = 0;
		for (int i = 0; i < listeVilles.size(); i++) {
			if (listeVilles.get(i).AZoneDeRecharge()) {
				nbZoneDeRecharges++;

			}

		}
		return nbZoneDeRecharges;
	}
}
