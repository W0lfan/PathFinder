# Java Maze PathFinder
## Problème
On nous donne un labyrinthe composé de 0 et de 1, où les 1 représentent les chemins
que l'on peut emprunter pour sortir du labyrinthe. L'idée du programme est de pouvoir obtenir les 
chemins à emprunter pour sortir du labyrinthe (sortie située aux coordonnées `(0;0)`) à partir des
trois autres entrées (situées respectivement en `(0,height-1)`, `(width-1,0)`, `(width-1, height-1)`).

## Résolution
### Généralités
La méthode employée ici est la création de: 
* un objet `Maze` dont les méthodes 
ont la capacité de bouger le pointer directionnel, ainsi que de donner des informations
sur des positions. De plus, cet objet va scanner le lien passé en 
paramètre du constructeur pour générer la matrice de 0 et de 1 à partir du fichier
texte qu'il va trouver au bout dudit lien. On lui indique également quelle est 
l'entrée (la position initiale du pointer, donc) du labyrinthe.
* un objet `Pointer` dont les méthodes strictes empêchent une modification 
de ses coordonnées par erreur.
* un objet `Memory` qui permet de stocker les instructions à exécuter, ainsi que
des méthodes strictes permettant de modifier son état (état identifié 
par des `ArrayList<String[]>` et `ArrayList<int[]>`).
* un object `PathFinder` qui va résoudre le labyrinthe en calculant 
les chemins exécutables, et en traitant strictement certains cas, puis en 
exécutant les instructions qu'il a stocké en mémoire. Le `main` de `PathFinder`,
qui est en réalité l'exécution de l'algorithme, possède un mode manuel et automatique. Veillez à changer la valeur 
de la variable `manual` pour modifier ce mode.

De ce fait, lors de l'exécution de la classe `main`, on crée un nouveau `Maze`, en passant en
paramètres le lien vers le fichier texte contenant le labyrinthe ainsi que les coordonées x et y de l'entrée du labyrinthe. Ensuite, 
on crée le `PathFinder` en précisant en paramètre de construction le labyrinthe tout 
just créé. Puis, on exécute la méthode `pathFinder.main()`.
Selon le mode d'exécution, il faudra intéragir avec la console ou non pour que 
le programme trouve la sortie tout seul.

### PathFinder.findNextMovement()
La méthode `PathFinder.findNextMovement()` peut être assez difficile à comprendre.
Nous allons donc la disséquer.
Tout d'abord, la méthode est privée, car elle exécute des actions sensibles. On ne souhaite pas 
que quelconque développeur l'appelle manuellement. De ce fait, 
son appel est réservé exclusivement à l'exécution du programme (Note: il peut être plus sûr, en effet,
de découper cette méthode en plusieurs autres).

Notez qu'une instruction est représentées comme suit:
* Pour la mémoire générale,
```java
String[] i = {"Direction","x","y"}; 
```
* Pour la mémoire multi-directionelle,
```java
int[]  i = {
        x,
        y,
        -1, // ou 1, selon si Right a déjà été effecutée
        -1, // ou 1, selon si Left a déjà été effecutée
        -1, // ou 1, selon si Down a déjà été effecutée
        -1, // ou 1, selon si Up a déjà été effecutée
} 
```

Donc, on récupère la position x et y du pointer, ainsi que la première instruction stockée en mémoire. On regarde également si la position 
où l'on se trouve a déjà impliqué plusieurs directions à emprunter (par exemple, en position `10,2`, on pourrait avoir la possibilité d'aller
en haut, en bas, ou à droite).
```java
int x = this.maze.getPointer().getX();
int y = this.maze.getPointer().getY();

String[] f = this.memory.getFirstInstruction();
int[] g = this.memory.getMultipleInstructionItem(x,y);
```

Puis, classiquement, on vérifie pour toutes les directions possibles s'il est possible de se déplacer
en un point X et Y à partir des coordonnées x et y déjà obtenues. 
```java
this.maze.isEmptyAt(x+1,y)
```

Deux conditions sont également rajoutées 
à cette vérification: 
* Si la première instruction en mémoire (c'est-à-dire celle qui vient d'être effecutée) est la direction
opposée à celle que l'on essaie de vérifier, alors on ne va pas la donner en instructions, sinon
le code ferait des aller-retours continus entre les mêmes directions. Cependant, si la première instruction n'existe
pas, alors on ne regarde pas si elle est existe.
```java 
f == null || f[0] != "Direction opposée"
```
* Si la position du pointer existe déjà dans la mémoire multi-directionnelle, alors on regarde
quelles directions n'ont pas déjà été effectuées. Si le multi-directionnel n'existe pas, alors on ne regarde pas
cette condition.
```java 
g == null || g[IndiceDeLaDirection] == -1
```

Puis, dans le cas où la première instruction existe, on regarde si elle existe aussi
dans les instructions que l'on vient de créer. Cela veut dire qu'on devrait continuer d'exécuter la direction 
sur laquelle on a lancé le pointer. Donc, si par exemple la dernière instruction effecutée est L (Left) et que les instructions contiennent 
l'instruction L, alors on va placer l'instruction L en première dans 
les instructions à effecuter, sans oublier de supprimer la deuxième instruction L.
```java
if (f != null) {
    int $ = FindUtils.isInInstructions(instructions, f);
    if ($ != -1) {
        instructions.add(0,instructions.get($));
        instructions.remove($+1);
    }
}
```
Ensuite, s'il y a plus d'une instruction dans la variable qui les contient, cela signifie que le point 
est multi-directionel. Donc, on stocke le point dans la mémoire multi-directionelle.
```java
this.memory.storeMultipleInstructionItem(x,y);
```
Puis, si le pointeur n'a pas été bougé, alors on le positionne. Sinon, on supprime la première instruction de la mémoire (celle qui vient donc d'être effectuée).
```java
if (this.memory.getPointer() == -1) {
    this.memory.setPointer();
} else {
    this.memory.removeFirstInstruction();
}
```
Enfin, on stocke en mémoire les instructions restantes.
```java
for (String[] $ : instructions) this.memory.store($);
```

## Resources
Veuillez trouver [la playlist Youtube](https://youtube.com/playlist?list=PLpTI9gaTJiUXMjXpiVkjU6SNS17YdYgja&si=1MILtC_PWUP472yi) du 
développement de ce PathFinder si vous souhaitez comprendre en profondeur le 
processus de création de l'algorithme.

Le problème à résoudre se trouve sur [codeabbey](https://www.codeabbey.com/index/task_view/maze-pathfinder), 
si vous souhaitez vous entraîner à le résoudre également.

Notez que le programme n'est pas entièrement terminé, qu'il manque encore
le retour des instructions de mouvement pour sortir du labyrinthe. Mais, la structure est là.
