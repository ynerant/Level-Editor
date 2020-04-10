---
title: Projet programmation 2
author: Yohann D'ANELLO, Édouard Némery
---

# The Game

## Présentation globale

*The Game* est un jeu de Tower Defense. Sur une grille pré-définie, des mobs apparaissent aléatoirement
sur la carte en quantité aléatoire. Leur objectif : rejoindre la partie gauche de la carte.

Une partie se déroule en 4 manches. Dès qu'une manche se termine, une nouvelle commence.

Un mob contient diverses propriétés :

* Un sprite (une image de taille 16x16 représentant le mob)
* Un nombre de points de vie
* Une vitesse
* Un butin.

Le joueur a 5 points de vie. À chaque fois qu'un mob atteint la partie gauche de l'écran, celui-ci perd un point de vie.
Il perd lorsqu'il n'a plus de points de vie. Il gagne s'il tue tous les mobs à l'issue des 4 manches.

Pour faire face aux mobs, l'utilisateur peut acheter et placer des tours. Une tour a un prix, et le joueur ne peut
l'acheter que s'il a la somme requise.

Une tour contient également diverses propriétés :

* Des dégâts par tir
* Une vitesse de tir
* Un prix
* Une fonction indiquant ce qu'il se passe lorsque la tour tire

Le jeu fonctionne par tick. Toutes les 50 millisecondes a lieu un tick. Toutes les tours sont mises à jour, puis les
mobs se déplacent éventuellement. La vitesse des tours et des mobs influe sur le fait de faire quelque chose pendant le
tick ou attendre un suivant (une vitesse de 10 indique qu'une action est faite tous les 10 ticks).

Si une tour doit tirer pendant un tick, elle tire sur tous les mobs à portée, et blesse les mobs en fonction des dégâts
réalisés. Si le mob n'a plus de point de vie, il disparaît.

Si un mob doit se déplacer, un chemin est calculé jusqu'au bord de la fenêtre via un parcours en largeur. Un système
de collisions est en effet géré, empêchant 2 mobs ou tours de se trouver au même endroit. Si un tel chemin existe, alors
le mob avance d'une case selon ce chemin. Sinon, il reste sur place.

### Différents mobs

Il existe différents types de mobs. Ils contiennent 3 propriétés : points de vie (dégâts nécessaires pour être tués), 
lenteur (nombre de ticks de jeu nécessaires à déclencher un tick chez le mob), butin (nombre de pièces ramassé par
le joueur lorsque le mob est tué).

À chaque tick de mob, une fonction de tick spécifique au mob est appelée, qui permet des actions supplémentaires.

Les mobs peuvent être gelés, si tel est le cas le nombre de ticks d'attente est multiplié par 2. Par ailleurs, un facteur
aléatoire compris entre 0.95 et 1.05 est appliqué.

#### Mob1

Ne fait rien de spécial.

* Points de vie : 2
* Lenteur : 50
* Butin : 10

#### Mob2

Ne fait rien de spécial.

* Points de vie : 6
* Lenteur : 20
* Butin : 20

#### MobStrong

Ne fait rien de spécial.

* Points de vie : 50
* Lenteur : 100
* Butin : 100

#### MobHealer

Soigne de 2 points de vie à chacun de ses ticks tous les mobs à 3 blocs à la ronde, à l'exception de lui-même.

* Points de vie : 20
* Lenteur : 60
* Butin : 20

#### MobSpeeder

Accélère d'un facteur 3 les ticks des mobs à 3 blocs à la ronde, à l'exception de lui-même.

* Point de vie : 25
* Lenteur : 60
* Butin : 30

#### MobBreaker

Casse les tours sur son passage (à tuer à distance)

* Points de vie : 110
* Lenteur : 120
* Butin : 70

### Différentes tours

Différentes tours sont à la disposition du joueur, avec des prix différents (rendant toutes les tours non accessibles
au début du jeu). Elles ont toutes un nombre de dégât indicatif, un nombre de ticks à attendre entre 2 tirs, et si
elle est améliorée ou non (voir UpgradeTower). Elles disposent aussi d'une fonction `shot` précisant l'action de la tour
à chaque tir.

#### BasicTower

Cette tour tire sur une unique cible aléatoire à 3 blocs à la ronde.

* Période : 5
* Prix : 10
* Dégâts : 1 (3 si améliorée)

#### WallTower

Cette tour ne fait rien, agit uniquement comme un mur empêchant les mobs de passer par là.

* Période : +infini
* Prix : 5
* Dégâts : 0

#### FreezeTower

Cette tour ne faît aucun dégât et gêle pendant 40 ticks (2 secondes), 100 ticks (5 secondes) si améliorée, tous les mobs
à 3 blocs à la ronde.

* Période : 10
* Prix : 40
* Dégâts : 0 (1 si améliorée)

#### ExploderTower

Cette tour lance des projectiles explosifs, qui inflige le double des dégâts à la cible ainsi que des dégâts aux mobs
présents à 3 blocs à la ronde de ce mob. La portée est de 5 blocs.

* Période : 20
* Prix : 70
* Dégâts : 3 (7 si améliorée)

#### UpgradeTower

Cette tour améliore de façon permanente toutes les tours à 5 blocs à la ronde (sauf elle-même).

* Période : 60
* Prix : 65
* Dégâts : 0 (1 si améliorée)

#### LaserTower

Cette tour tire des rayons laser dans les quatre directions et chaque mob reçoit des dégâts.

* Période : 40
* Prix : 80
* Dégâts : 3

## Implémentation

Le projet est intégralement fait en Java. On ne détaillera pas ici la partie éditeur de niveau, bien qu'elle soit
conséquente.

Une fois que l'utilisateur a choisi la carte à utilisateur via un menu de sélection, le jeu se lance dans une fenêtre
distincte, gérée par la classe `fr.ynerant.leveleditor.game.GameFrame`.

Le coeur du jeu est géré dans un Thread en boucle infinie, une boucle attendant 50 ms avant de passer à l'itération suivante.
Le fonctionnement est détaillé plus haut.

L'affichage du jeu est quant à lui géré dans un `JPanel` dédié, avec la fonction `paintComponent` réécrite.
Dès que le panel est paint, on demande à ce qu'il soit repaint pour s'assurer que l'affichage est correct.

Dans l'ordre, on dessine les sprites de la carte (couche 1, couche 2 puis couche 3), puis les différents mobs et les tours
via leurs sprites.

La carte est composée d'une longueur, d'une largeur et de la liste des cases.

Une case est une position et 3 sprites (couche 1, couche 2, couche 3).

Un sprite est une image de taille 16x16, qui contient des informations sur l'endroit où le chercher.

Un Mob est une classe abstraite contenant des informations abstraites (détaillées plus haut). Un type de mob sera donc
une classe héritant de `Mob`.

Il en est de même pour les tours, qui hérite de `Tower`.

L'intérêt de l'héritage par rapport à un type donné à une classe Mob (paramètres donnés dans une enumération `MobType`
par exemple) est de pouvoir mieux personnaliser les fonctions, par exemple en intégrant les dégâts aléatoires.

