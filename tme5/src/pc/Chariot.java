package pc;

import java.util.ArrayList;

public class Chariot {
    private final int poidsMax;
    private int poidsTot;
    private final int nbMax;
    private boolean chargement = true; // indique si le chariot est en cours de chargement ou non
    private final ArrayList<AleaObjet> chariot = new ArrayList<>();
    
    public Chariot(int poidsMax, int nbMax){
        this.poidsMax = poidsMax;
        this.nbMax = nbMax;
        System.out.println("Chariot crée (poidsMax: "+poidsMax+", nbMax:"+nbMax+")");
    }
    
    public synchronized void insere(AleaObjet obj) throws InterruptedException {
        /* permet d'insérer un objet dans le chariot */
        if (obj==null){
            return;
        }
        // attente si : on décharge, on dépasse le poids max si ajout, si on a atteint le max du chariot (en nb d'obj)
        while((!chargement) && (poidsTot + obj.getPoids() < poidsMax) && (chariot.size() == nbMax)) {
            wait();
        }

        chariot.add(obj);
        System.out.println(obj+" ajouté au chariot");
        // on arrete de remplir (commence à vider) si : on a atteint la taille max, on a dépassé/atteint le poids max
        if(chariot.size() == nbMax || poidsTot >= poidsMax - obj.getPoids()) {
            chargement = false;
            notifyAll(); // donne la main au déchargement
        }
    }

    public synchronized void vide() throws InterruptedException{
        // vide le chariot d'un élément
        while(chargement){
            wait();
        }
        // chariot.get(0);
        chariot.remove(0);
        System.out.println("Chariot vidé de 1");

        if (chariot.isEmpty()){
            chargement = true;
            notifyAll(); // redonne la main au chargement
        }
    }
}