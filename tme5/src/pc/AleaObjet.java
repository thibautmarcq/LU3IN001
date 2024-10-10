package pc;

public class AleaObjet {
    private int poids;

    public AleaObjet(int min, int max){
        poids = (int)(Math.random() * (max - min + 1) + min);
    }

    public int getPoids(){
        return poids;
    }

    public String toString(){
        return ("Marchandise de poids " + poids);
    }
    
}
