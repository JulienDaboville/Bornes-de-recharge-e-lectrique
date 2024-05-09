package up.mi.jmm.projet;

import java.util.ArrayList;

/**
 * TestAlgorithme contient des méthodes statiques pour tester différents algorithmes sur une communauté d'agglomération.
 * Inclut des stratégies pour la gestion optimale des zones de recharge.
 */
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

public class TestAlgorithme {
	/**
	 * Applique une stratégie naïve de modification aléatoire des recharges.
	 * 
	 * @param ca La communauté d'agglomération à traiter.
	 * @param k  Le nombre d'itérations pour la modification aléatoire.
	 * @return La communauté d'agglomération modifiée.
	 */

	static public CommunauteAgglomeration naif(CommunauteAgglomeration ca, int k) {
		int i = 0;
		while (i < k) {
			

			Random r = new Random();
			int indice = r.nextInt(ca.getTaille());
			Ville v = ca.getListeVille().get(indice);

			if (v.AZoneDeRecharge()) {
				v.supprimerRecharge();
			} else {
				v.ajouterRecharge();
			}
			i++;

		}
		return ca;

	}

	/**
	 * Applique une stratégie plus complexe avec des itérations jusqu'à
	 * l'amélioration du score. Les villes sont choisies au hasard, la recharge est
	 * ajoutée ou supprimée, et la modification est conservée si elle améliore le
	 * score.
	 *
	 * @param ca La communauté d'agglomération à traiter.
	 * @param k  Le nombre d'itérations pour la modification avec amélioration du
	 *           score.
	 * @return La communauté d'agglomération modifiée.
	 */

	public static CommunauteAgglomeration algo2(CommunauteAgglomeration ca, int k) {
		int i = 0;
		int scoreCourant = ca.score();
		while (i < k) {
			
			Random r = new Random();
			int indice = r.nextInt(ca.getTaille());
			Ville v = ca.getListeVille().get(indice);
			if (v.AZoneDeRecharge()) {
				v.supprimerRecharge();
			} else {
				v.ajouterRecharge();
			}

			if (ca.score() < scoreCourant) {
				i = 0;
				scoreCourant = ca.score();

			} else {
				i++;
			}

		}
		return ca;

	}

	/**
	 * Applique un algorithme optimal pour attribuer des couleurs aux villes de la
	 * communauté d'agglomération. Les villes sont triées par ordre décroissant,
	 * puis chaque ville et ses voisins reçoivent une couleur distincte. Les
	 * recharges sont ajoutées aux villes colorées et supprimées des autres.
	 *
	 * @param ca La communauté d'agglomération à traiter.
	 * @return La communauté d'agglomération modifiée avec des recharges optimisées.
	 */

	public static CommunauteAgglomeration algoOptimal(CommunauteAgglomeration ca) {
		List<Ville> listeVilles = ca.getListeVille();

		Collections.sort(listeVilles, Comparator.reverseOrder());

		int couleur = -1;
		List<Ville> villesAvecZoneRecharge = new ArrayList<Ville>();

		for (int i = 0; i < listeVilles.size() && ca.aDesVillesNonColores(); i++) {
			if (listeVilles.get(i).getCouleur() == -1) {
				couleur++;
				Ville v1 = listeVilles.get(i);
				v1.attributCouleur(couleur);
				villesAvecZoneRecharge.add(v1);

				for (int j = i + 1; j < listeVilles.size(); j++) {
					Ville v2 = listeVilles.get(j);
					if (!ca.sontVoisins(v1, v2) && v2.getCouleur() == -1) {
						v2.attributCouleur(couleur);

					}

				}

			}
		}

		for (int j = couleur + 1; j < listeVilles.size(); j++) {

			listeVilles.get(j).supprimerRecharge();
		}

		return ca;

	}

	public static void main(String[] args) {
		CommunauteAgglomeration ca = GestionnaireCommunaute.restaurer(args[0]);
		algoOptimal(ca);
		System.out.println("État après algorithme optimal : " + ca.villesAvecZoneDeRecharge());
	}

}
