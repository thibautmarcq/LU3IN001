package pc;

import java.util.ArrayList;

public class TestAlea {
    public static void main(String[] args){
        // Stock => Chargeur => Chariot => Dechargeur => Poubelle

        // Création et ajout d'obj dans le stock
        AleaStock stock = new AleaStock(100);
        for (int i=0; i<1001; i++){
            stock.addAleaObjet(new AleaObjet(1, 15)); // ajoute 100 objets de taille [1,15] au stock
        }
        
        // Création d'un chariot, 2 chargeurs et 2 déchargeurs
        Chariot chariot1 = new Chariot(50, 10);
        Chargeur charg1 = new Chargeur(stock, chariot1);
        Chargeur charg2 = new Chargeur(stock, chariot1);
        Dechargeur decharg1 = new Dechargeur(stock, chariot1);

        // Gestion des Threads
        System.out.println("oui");
        ArrayList<Thread> th = new ArrayList<>();
        
        // Thread charg1 & 2 + dechargeur & start
        th.add(new Thread(()-> charg1.chargerChariot()));
        th.add(new Thread(()-> charg2.chargerChariot()));
        th.add(new Thread(()-> decharg1.dechargerChariot()));

        for (Thread t: th){
            t.start();
        }

        // sleep
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // join
        for (Thread t: th){
            try{
                t.join();
            } catch (InterruptedException e){
                e.printStackTrace();
            }
        }
    }



}