# Projet Communauté d'Agglomération

Ce projet Java simule la gestion d'une communauté d'agglomération, permettant à l'utilisateur de gérer des villes et des zones de recharge électrique. Il a été développé et testé en utilisant l'IDE Eclipse.

## Prérequis

- Java JDK 11 ou supérieur.
- Eclipse IDE (recommandé pour le développement et l'exécution).

## Installation et Configuration

Pour configurer et exécuter ce projet dans Eclipse :


1. **Dézipper le Fichier** :
    Commencez par extraire le contenu du fichier .zip. Vous pouvez le faire en utilisant un logiciel de décompression comme WinRAR, 7-Zip ou le gestionnaire
    d'archives intégré de votre système d'exploitation. Faites un clic droit sur le fichier .zip et choisissez Extraire ici ou Extraire vers [nom_du_dossier].
2. **Importer le Projet dans Eclipse** :
   - Ouvrez Eclipse et allez dans `File > Open Projects from File System...`.
   - Sélectionnez le dossier du projet et importer le.
3. **Configuration du Projet** :
   - Assurez-vous que le JDK approprié est sélectionné dans les paramètres du projet.

## Exécution du Programme

Pour exécuter le programme dans Eclipse :

1. **Ouvrir `GestionnaireCommunaute.java`** :
   - Trouvez le fichier dans l'explorateur de projet et ouvrez-le.
2. **Exécuter le Programme** :
   - Cliquez avec le bouton droit sur le fichier dans l'éditeur et choisissez `Run As > Java Application`.
3. **Configurer les Arguments du Programme** :
   - Allez dans `Run > Run Configurations...`.
   - Sous l'onglet `(x)= Arguments`, saisissez le chemin vers le fichier de communauté d'agglomération dans `Program arguments`.

Commande pour lancer le programme en ligne de commande :

```
java -cp bin up.mi.jmm.projet.GestionnaireCommunaute <chemin_vers_fichier_communaute>
```

Remplacez `<chemin_vers_fichier_communaute>` par le chemin du fichier décrivant la communauté d'agglomération.

## Fonctionnalités Implémentées


- **Restauration de la Communauté d'Agglomération** : Le programme peut lire un fichier spécifié et restaurer l'état de la communauté d'agglomération en fonction des données fournies.
- **Gestion Manuelle** : Les utilisateurs peuvent ajouter ou retirer manuellement des zones de recharge dans différentes villes.
- **Résolution Automatique** : Le programme inclut un algorithme qui optimise automatiquement la répartition des zones de recharge en respectant les contraintes d'accessibilité.
- **Sauvegarde de l'État** : Les utilisateurs peuvent sauvegarder l'état actuel de la communauté d'agglomération dans un fichier.
- **Contrainte d'Accessibilité** : Le programme vérifie si chaque ville dispose d'une zone de recharge ou est connectée à une ville qui en possède une.

## Limitations et Problèmes Connus

- Performance avec de grandes communautés 








