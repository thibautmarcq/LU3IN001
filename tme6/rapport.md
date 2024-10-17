## Rapport TME 6

### Question 2
 Les deux boucles de la fonction permettent de parcourir les pixels de l'écran. Pour celà on boucle sur x et sur y avec un pas du pixel (de la résolution voulue).

### Question 4
La variable gfx peut subir un data race. On ne peut pas ajouter de synchronized directement sur les méthodes puisqu'elles viennent d'un package exterieur.
Il faut donc la protéger la variable gfx => ajout d'une section critique / synchronized sur lui.


### Question 5
Il y a trop de threads qui cherchent à accéder à gfx en même temps. Celà ne permet pas d'optimiser le rendu graphique.