<h1 align=center>IHM - Projet Android</h1> 
<p align=center style="font-style: italic;">Lacaze Yon - Loya Dylan</p> 
<p align=center style="font-style: italic;">Promohub</p> 
<p align=center>12 avril 2024</p>  

![[icon.png|center|100]]

<hr>

## Table des matières
- [[#Objectif de l'application|Objectif de l'application]]
- [[#Conception|Conception]]
- [[#Application en cours d'exécution|Application en cours d'exécution]]
	- [[#Page d’accueil|Page d’accueil]]
	- [[#Personnaliser la recherche|Personnaliser la recherche]]
	- [[#Personnaliser la recherche#Sélection des magasins|Sélection des magasins]]
	- [[#Personnaliser la recherche#Paramètres des deals|Paramètres des deals]]
	- [[#Personnaliser la recherche#Paramètres des prix des deals|Paramètres des prix des deals]]
	- [[#Personnaliser la recherche#Résultats de la recherche|Résultats de la recherche]]
	- [[#Détail d'un deal|Détail d'un deal]]
	- [[#Deals enregistré|Deals enregistré]]
	- [[#Jeux possédés|Jeux possédés]]
	- [[#Tout les deals|Tout les deals]]
	- [[#Recherche|Recherche]]
	- [[#Paramètres|Paramètres]]
- [[#Arborescence|Arborescence]]
	- [[#API|API]]
	- [[#Database|Database]]
	- [[#Results|Results]]
	- [[#Utils|Utils]]
	- [[#Le reste|Le reste]]


## Objectif de l'application

L'objectif de l'application Promohub est de fournir aux utilisateurs une plateforme pour découvrir et rechercher des offres promotionnelles sur une vaste gamme de jeux vidéo. En utilisant l'API de CheapShark, Promohub permet aux utilisateurs de parcourir une multitude de deals et de trouver des jeux vidéo à des prix avantageux.

Les fonctionnalités clés de l'application incluent la possibilité de rechercher des deals en fonction de différents critères tels que le titre du jeu, le prix, le pourcentage de réduction, la plateforme, et bien d'autres encore. Les utilisateurs peuvent également parcourir les deals les plus populaires et les plus récents, ainsi que sauvegarder leurs offres préférées pour y accéder ultérieurement et leurs préférences de recherche pour faire des recherches rapidement.

Promohub vise à simplifier le processus de recherche de bonnes affaires pour les jeux vidéo en centralisant les informations pertinentes et en offrant des options de filtrage avancées. En permettant aux utilisateurs de trouver rapidement et facilement les meilleures offres disponibles, l'application vise à améliorer l'expérience d'achat de jeux vidéo et à aider les joueurs à réaliser des économies significatives.  

## Conception
Pendant l'étape de conception du projet nous avons considérés plusieurs APIs:  
- [CheapShark](https://apidocs.cheapshark.com/)
- [IsThereAnyDeal](https://docs.isthereanydeal.com/)
- [Games-Deals-API](https://github.com/MohamedAmgd/Game-Deals-API)  

Nous avons décidés d'utiliser celle de CheapShark car c'est celle qui correspondait le mieux à nos besoins tout en étant la plus simple d'utilisation (pas besoin de clé, ni de compte).

## Application en cours d'exécution
#### Page d’accueil

```image-layout-a
![page d'acceuil en thème clair](./images/home_light.jpeg)
![page d'acceuil en thème sombre](./images/home_dark.jpeg)
```

Nous pouvons effectuer plusieurs actions sur la page d'accueil, les 3 principales:
- Personnaliser la recherche
- Voir tout les deals
- Voir les deals enregistrés  

Nous avons aussi accès à la page de recherche et à la page de paramètres.
Enfin, en bas se trouve un bouton quitter qui permet de sortir de l'application.
#### Personnaliser la recherche
##### Sélection des magasins


```image-layout-a
![[select_store_checked.jpeg]]
![[select_store_unchecked.jpeg]]
```

Lorsqu'on appuie sur le bouton "Personnaliser la recherche" depuis la page d'accueil, nous somme redirigés sur la sélections des magasin. Ils sont tous sélectionné par défaut. Nous pouvons les trier par ordre alphabétique ou bien par identifiant de magasin (par défaut).  
##### Paramètres des deals
```image-layout-a
![[deal_parameters_light.jpeg]]
![[deal_parameters_dark.jpeg]]
```
Une fois la sélection des magasins passée, nous pouvons choisir les paramètres des deals voulus. Nous pouvons notamment modifier le nombre de deals, les trier selon plusieurs critères et modifier l'ordre de tri. Enfin, nous pouvons spécifier l'âge maximum du deal.
Si nous n'avons pas activé l'enregistrement automatique des paramètres dans les paramètres de l'application, un petit texte nous le rappel.
##### Paramètres des prix des deals
```image-layout-a
![[price_parameters_light.jpeg]]
![[price_parameters_dark.jpeg]]
```
Lorsque les paramètres des deals sont choisis, nous sommes redirigés sur cette activité, sur laquelle nous pouvons modifier des paramètres agissant sur les prix des deals.
Nous pouvons d'abord définir un intervalle de prix dans lesquels les deals se situeront, ou, si l'on active le switch, voir tout les deals indépendamment du prix.
Nous pouvons ensuite cochés deux paramètres:
- Les jeux doivent ils êtres des AAA ?
- Les jeux doivent ils êtres en promo ?
Enfin si cela nous intéresse nous pouvons choisir une note minimal sur Metacritic et sur Steam pour nos jeux.   
On remarque la présence du même texte que sur l'activité précédente nous rappelant que nous pouvons enregistrer nos paramètres automatiquement.

##### Résultats de la recherche
```image-layout-a
![[loading_results.jpeg]]
![[result_list.jpeg]]
```
Enfin lorsque l'on à terminer de changer ses réglages on peut lancer la recherche.
Promohub nous redirige alors sur une nouvelle activité avec un spinner nous indiquant qu'une opération est en cours. Une fois les résultats reçus, Ils sont affichés dans un RecyclerView. Chaque deal nous donne quelques informations telles que l'image du jeu, le titre, le magasin et le prix en promotion.

#### Détail d'un deal
Si un deal nous intéresse, on peut cliquer sur son entrée dans la liste précédente.
```image-layout-a
![[detail_loading.jpeg]]
![[detail_loaded.jpeg]]
```
Nous arrivons sur sa page de détail. Les images de la capture de gauche sont des placeholder en attendant que l'application charge les images du jeu. Nous avons accès à plusieurs informations concernant le jeu sur cette page:
- Le titre
- Le prix en promotion
- Le prix normal
- La note metacritic
- La note steam
Ainsi que 3 boutons permettant de partager le deal, en partageant un lien permettant d'accéder à ce lien, d'ouvrir le deal dans un navigateur et de sauvegarder le deal dans l'application pour pouvoir revenir dessus plus tard.

#### Deals enregistré
Depuis la page d'accueil nous pouvons accéder aux deals enregistrés.
```image-layout-a
![[saved_no_deal.jpeg]]
![[saved_deal.jpeg]]
```
Nous voyons sur l'image de gauche que si nous n'avons pas de deal sauvegardé, le titre nous l'affiche. Sinon la liste des deals sauvegarder est affichée et nous pouvons y accéder de la même manière que les deals de la recherche.

#### Jeux possédés
![[owned_games.jpeg|350]]  
Depuis la page d'accueil nous pouvons aussi accéder aux jeux possédés. L'utilisateur peut enregistrer des jeux en tant que jeux possédés pour ne pas les voir dans les résultats des deals. Il peut modifier ce comportement dans la page des paramètres

#### Tout les deals
Depuis la page d'accueil lorsque l'on appuie sur le bouton "voir tout les deals", nous voyons les 60 deals les plus récent renvoyés par l'API, indépendamment de tous paramètres.
>[!info] La taille maximale d'une page de deal fournie par l'API de CheapShark est de 60 deals  


```image-layout-a
![[all_deals.jpeg]]
![[all_deals_doublons.jpeg]]
```
La deuxième photo est intéressante car nous voyons plusieurs fois le même deal. La différence est le magasin sur lequel est ce deal. Pour CheapShark, vu qu'ils viennent de magasins différents ce sont des deals différents, même si c'est le même jeu au même prix. 

#### Recherche
```image-layout-a
![[search.jpeg]]
![[search_exact.jpeg]]
```
Nous pouvons aussi rechercher des deals sur des jeux. On peut rentrer le nom du jeu rechercher et sélectionner si nous voulons une rechercher exacte ou pas. Nous voyons la différence sur les captures précédentes. Si l'option exacte n'est pas cochée nous aurons tout les jeux contenant la chaîne de caractère que nous avons rentrés dans le titre. Si l'option est cochée nous n'aurons que les jeux dont le titre est exactement celui que nous avons rentrés.

#### Paramètres
```image-layout-a
![[settings.jpeg]]
![[settings_suite.jpeg]]
```
Enfin nous avons une page de paramètres dans laquelle l'utilisateur peut sélectionner si il veut enregistrer ses paramètres de recherche à chaque fois qu'il les changes, et si il veut afficher les jeux qu'il possède dans les résultats. Nous affichons aussi un avis aux utilisateurs expliquant comment sont récupérés les deals et créditons les auteurs des assets que nous utilisons.

## Arborescence
La structure globale de l'arborescence du code est la suivante:
```
├── api
├── database
├── MainActivity.java
├── results
├── search
├── select_deals_parameters
├── select_deals_price_parameters
├── select_store
├── settings
└── utils
```
Chaque entrée, excepté `MainActivity.java` est un package.
#### API
```
api
├── CheapShark.java
├── DealFetcherCallable.java
├── Deal.java
├── SteamGridDb.java
├── StoreFetcherCallable.java
└── Store.java
```
Dans ce package on retrouve toutes les classes et fonctions relatives aux appels à l'API. Les fichiers `CheapShark.java` et `SteamGridDb.java` définissent les fonctions permettant de communiquer avec les différentes APIs. Les 4 autres fichiers sont les définitions des classes contenant les informations de l'API et les classes implémentant `Callable` permettant d'appeler les APIs.

#### Database
```
database
├── AppDatabase.java
├── DealDao.java
└── DealEntity.java
```
Les classes de ce package définissent la base de données et l'entité permettant d'enregistrer les deals.

#### Results
```
results
├── AllDealsActivity.java
├── DealAdapter.java
├── DealDetail.java
├── ResultsActivity.java
└── SavedDealsActivity.java
```
Les classes de ce package correspondent à toutes les activités affichant des résultats des APIs.

#### Utils
Ce package contient les éléments dont on peut avoir besoin qui ne correspondent pas aux autres packages

#### Le reste
Tout les autres packages correspondent aux différentes activités de l'application.
