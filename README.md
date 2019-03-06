# TAKENOKO
Takenoko est un jeu de société créé en 2011 par Antoine Bauza et co-édité par Bombyx et
Matagot. Le mot japonais "takenoko" signifie "pousse de bambou". Takenoko a remporté l'As
d'or Jeu de l'année 2012. Le jeu se joue de 2 à 4 joueurs, à partir de 8 ans, pour des parties
d'environ 45 minutes.
[Source-Wikipedia](https://fr.wikipedia.org/wiki/Takenoko)


## Objectif du projet :

 Le but de notre projet est d’avoir des joueurs intelligents grâce à l’IA qui essaient de finir le jeu avant leur adversaire en terminant le nombre des objectifs nécessaire, ces joueurs doivent jouer intelligemment pour pouvoir terminer le jeu le plus tôt possible et pour avoir le maximum score c’est à dire atteindre des objectifs qui ont le plus grand score possible .

## Contexte et définition du problème :

   Le but de TER est d’avoir une bonne IA dans notre version du jeu TAKENOKO, IA peut être inclus dans le choix des objectifs à faire, pousser des bambous en faire bouger le gardener quand on a plus des objectifs de type gardener que celles de panda, etc.
Le problème c’est comment intégrer les algorithmes de l’intelligence artificiel pour qu’on puisse avoir un ou plusieurs joueurs intelligents, nos joueurs devront réaliser un nombre d’objectifs pour finir la partie mais au même temps avoir le maximum score pour gagner.

## Restrictions du jeu:

##### Weather
L'élimination de “weather” pour simplifier les règles du jeu.

##### Improvements
Par l'élimination des  “weather” nous ne pouvons pas déléguer le choix des improvements au joueur. Par conséquent nous avons décidé de de garder les improvements seulement sur les tuiles qui les contient au début.

| Impr\Hex      | Vert| Rose  | Jaune |
| ------------- |:----:|-----:|------:|
| Enclosure     | 2    |    1 |      1|
| Fertilizer     | 1    |    1 |      1|
| Watershed     | 2    |    1 |      1|

##### Objectifs
Par faute de temps, nous avons décidé de ne pas mettre en jeu les “objectifs pattern”. 

Par contre nous nous sommes bien concentré sur les objectifs Panda et Jardinier et nous avons réussi de les implémenter tous.

Comme nous avons observé dans le jeu à deux joueurs avec 45 objectifs on fini une partie quand on atteint 9 objectifs donc 20%. Dans notre cas on va finir une partie quand on va atteindre 6(0,2*30) objectifs(pour 2 joueurs);

##### Irrigation
Pour simplifier le moteur du jeu nous avons modifié le processus d’irrigation; À chaque fois quand le joueur choisit l’action “placeIrrigationChannel” il prend un canal d’irrigation et le place directement sur une tuile qui a au moins une tuile voisine irriguée.

Les joueurs gardent pas les canaux d’irrigation. 

A chaque dépôt d’un canal est irriguée une seule tuile.

## Spécification pour intelligence artificielle

Nous avons décidé de séparer le développement d'Intelligence artificielle pour notre jeu en deux parties plus complexes:
1. Joueur joue intelligemment les **objectifs Panda**
2. Joueur joue intelligemment les **objectifs Gardener**

On va essayer de développer deux niveaux d'intelligence.
* Premier plus simple: **GardenerStrategy IA** et **PandaStrategy IA**;
* Deuxième plus avancée: **Advanced IA**;

### Objectifs Panda IA
Les actions effectués par le joueur auront comme but d'accomplir plus vite et
d’une manière plus optimale le nombre des objectifs panda nécessaire pour gagner 
une partie du jeu.

1. Le joueur doit choisir que les objectifs de type panda.
2. Le joueur détermine les sections des bambous dont il a besoin pour accomplir les objectifs qu'il possède.
3. Chercher à travers les parcelles accessible (goable hex) si il existe le 
bambou dont on a besoin(determiné en 2).
    * Si nous avons parcouru tous les hexagons et nous n’avons pas trouvé aucune parcelle qui contient des bambous satisfaisantes on bouge le panda sur une parcelle qui contient au moins un bambou. Si n'existe pas des parcelles avec des bambous on déplace le panda aléatoirement.
4. Déplacer le panda dans le hexagon déterminé en (3).

### Objectifs Gardener IA 
Les actions effectués par le joueur auront comme but d'accomplir le plus rapide possible
les objectifs de jardinier avec le maximum score possible.

1. Le joueur doit choisir que les objectifs de type jardinier.
2. Le joueur doit ajouter une barre d'irrigation dans les hexagones où on doit accomplir des objectifs de type jardinier s'ils sont pas irrigés.
3. Le joueur doit déplacer le jardinier en priorité aux hexagones qu'ont la même configuration des objectifs jardinier.
4. Le joueur doit chosir de déposer un hexagone qu'a la même configuration d'un des objectives jardinier.
5. Assurer que l'action moveGardener doit étre choisit parmis les actions de joueur.
6. Si l'endroit où on veut déplacer le jardinier n'est pas accessible à ce dernier :
le joueur doit déplacer le jardinier en premier temps dans une tuile accessible, d'où aprés on peut le déplacer à  l'endroit souhaité.
7. Le joueur doit chosir en priorité d'accomplir les objectives qu'ont le maximum score.


### Advanced IA

Le joueur regroupe les deux stratégies (GardenerStrategy IA et PandaStrategy IA) pour avoir une meilleure performance.


# Fonctionnalité du jeu

* Panda`(accompli)`
* Jardinier`(accompli)`
* Le plateau du jeu`(accompli)`
* Les parcelles`(accompli)`
* Bamboos`(accompli)`
    * Restriction par nombre pour chaque couleur ` `
* Les canals d'irrigations `(accompli)`
    * Les parcelles qui contient un amenagement watershed sont irrigue `(accompli)`
    * Tous les pacelles voisins de la première parcelle(base) sont immediatement irrigue `(accompli)`
   
* Les cartes d'objectifs 
    * Objectifs panda `(accompli)`
    * Objectifs jardinier `(accompli) `
    * Objectifs parcelles ` `
    
* Actions
    * Déplacement du panda `(accompli)`
    * Déplacement du jardinier `(accompli)`
    * Mise en jeu les parcelles `(accompli)`
    * Choisir une carte d'objectif ` (accompli)`
    * Mise en jeu les les canals d'irrigations ` (accompli)`
    
* Aménagements
    * L'amenagement par default(aucun amenagement)`(accompli)`
    * Watershed`(accompli)`
    * Enclosure `(accompli)`
    * Fertilizer `(accompli)`
