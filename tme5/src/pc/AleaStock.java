package pc;

import java.util.ArrayList;

public class AleaStock {
    // biblio (tab) de plusieurs objets (AleaObjet)
    private final ArrayList<AleaObjet> stock;
    private final int taille;

    public AleaStock(int taille){
        /* Stock de taille taille objets max */
        this.taille = taille;
        stock = new ArrayList<>();
        System.out.println("Stock de taille "+taille+" crée");
    }

    public synchronized void addAleaObjet(AleaObjet obj){
        // Ajoute une marchandise obj au stock
        if (stock.size() < taille){
            stock.add(obj);
            System.out.println(obj+" ajouté au stock");
        } else {
            System.out.println("Stock rempli, "+obj+" non ajouté");
        }
    }

    public synchronized boolean isEmpty(){
        /* teste si le stock est vide */
        if (stock.isEmpty()){
            System.out.println("stock vide");
        }
        return stock.isEmpty();
    }

    public synchronized AleaObjet popAleaObjet(){
        // Extrait (get et remove) une marchandise du stock et la renvoie
        if (!isEmpty()){
            AleaObjet obj = stock.get(0);
            stock.remove(0);
            System.out.println(obj+" retiré du stock");
            return obj;
        }
        return null;
    }

}
