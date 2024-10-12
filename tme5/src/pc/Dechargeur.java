package pc;

public class Dechargeur {
    private volatile boolean running = true;
    private AleaStock stock;
    private Chariot chariot;

    public Dechargeur(AleaStock stock, Chariot chariot) {
        this.stock = stock;
        this.chariot = chariot;
        System.out.println("Déchargeur crée");
    }

    public void dechargerChariot() {
        while (running) {
            try {
                chariot.vide();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("stop decharge");
    }

    // Méthode pour arrêter le déchargeur
    // public void stop() {
    //     running = false;
    //     System.out.println("stop");
    // }
}