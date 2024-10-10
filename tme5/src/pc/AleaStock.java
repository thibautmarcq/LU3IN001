package pc;

import java.util.ArrayList;

public class AleaStock {
    private ArrayList<AleaObjet> stock;
    private int taille;

    public AleaStock(int taille){
        this.taille = taille;
        stock = new ArrayList<AleaObjet>();
    }

    public void addAleaObjet(AleaObjet obj){
        // Ajoute une marchandise au stock
        if (stock.size() < taille){
            stock.add(obj);
        }
    }

    public AleaObjet getAleaObjet(){
        // Extrait (get et remove) une marchandise du stock et la renvoie
        AleaObjet obj = stock.get(0);
        stock.remove(0);
        return obj;
    }

}
