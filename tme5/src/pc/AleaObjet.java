package pc;

public class AleaObjet {
    private final int poids;

    public AleaObjet(int min, int max){
        /* crée un objet de taille random entre min et max (inclus) */
        poids = (int)(Math.random() * (max - min + 1) + min);
        System.out.println("Objet de poids "+poids+" crée");
    }

    public int getPoids(){
        return poids;
    }

    @Override
    public String toString(){
        return ("Objet de poids " + poids);
    }
    
}
