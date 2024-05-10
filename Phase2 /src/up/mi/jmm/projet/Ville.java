package up.mi.jmm.projet;

/**
 * Représente une ville dans une communauté d'agglomération. Contient des informations sur la ville, y compris la présence de zones de recharge.
 */
public class Ville implements Comparable<Ville> {

	private int indice; // Indice unique de la ville
	private String nom; // Nom de la ville
	private boolean zoneDeRecharge; // Indique si la ville a une zone de recharge
	private int nbVoisins; // Nombres de voisins
	private int couleur; // Couleur attribuée à la ville dans un contexte graphique

	/**
     * Constructeur pour créer une nouvelle ville.
     * @param indice L'indice unique de la ville.
     * @param nom    Le nom de la ville.
     */
	Ville(int indice, String nom) {
		this.indice = indice;
		this.nom = nom;
		this.zoneDeRecharge = false; 
		this.nbVoisins = 0;
		this.couleur = -1; // 
	}

	/**
	 * Constructeur pour une nouvelle ville avec seulement un nom.
	 *
	 * @param nom Le nom de la ville.
	 */
	Ville(String nom) {
		this.nom = nom;
		zoneDeRecharge = true;
		nbVoisins = 0;
		couleur = -1; 
	}

	/**
	 * Ajoute une zone de recharge à la ville.
	 */
	public void ajouterRecharge() {
		if (!zoneDeRecharge) {
			zoneDeRecharge = true;
		} else {
			System.out.println("Il y a déjà une zone de recharge.");
		}
	}

	/**
	 * Supprime la zone de recharge de la ville.
	 */
	public void supprimerRecharge() {
		if (zoneDeRecharge) {
			zoneDeRecharge = false;
		} else {
			System.out.println("Il n'y a pas de zone de recharge à supprimer.");
		}
	}

	/**
	 * Obtient le nom de la ville.
	 *
	 * @return Le nom de la ville.
	 */
	public String getNom() {
		return nom;
	}

	/**
	 * Vérifie si la ville a une zone de recharge.
	 *
	 * @return true si la ville a une zone de recharge, sinon false.
	 */
	public boolean AZoneDeRecharge() {
		return zoneDeRecharge;
	}

	/**
	 * Obtient l'indice unique de la ville.
	 *
	 * @return L'indice de la ville.
	 */
	public int getIndice() {
		return indice;
	}

	/**
	 * Obtient le nombre de voisins de la ville.
	 *
	 * @return Le nombre de voisins de la ville.
	 */
	public int getNbVoisins() {
		return nbVoisins;
	}

	/**
	 * Incrémente le nombre de voisins de la ville.
	 */
	public void ajoutVoisins() {
		nbVoisins++;
	}

	/**
	 * Obtient la couleur attribuée à la ville dans un contexte graphique.
	 *
	 * @return La couleur attribuée à la ville.
	 */
	public int getCouleur() {
		return couleur;
	}

	/**
	 * Attribue une couleur à la ville dans un contexte graphique.
	 *
	 * @param couleur La couleur à attribuer.
	 */
	public void attributCouleur(int couleur) {
		this.couleur = couleur;
	}

	/**
	 * Redéfinition de la méthode compareTo pour l'interface Comparable. Compare les
	 * villes en fonction du nombre de voisins.
	 *
	 * @param v2 La deuxième ville à comparer.
	 * @return Un entier négatif, positif ou nul selon que cette ville a moins, plus
	 *         ou autant de voisins que la deuxième.
	 */
	@Override
	public int compareTo(Ville v2) {
		if (this.getNbVoisins() > v2.getNbVoisins()) {
			return 1;
		} else if (this.getNbVoisins() < v2.getNbVoisins()) {
			return -1;
		} else {
			return 0;
		}
	}

	/**
	 * Redéfinition de la méthode toString pour obtenir une représentation textuelle
	 * de la ville.
	 *
	 * @return Le nom de la ville.
	 */
	@Override
	public String toString() {
		return nom;
	}
}
