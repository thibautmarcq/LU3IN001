package pc;

public class Dechargeur {
    private final AleaStock stock;
    private final Chariot chariot;

    public Dechargeur(AleaStock stock, Chariot chariot){
        this.stock = stock;
        this.chariot = chariot;
        System.out.println("Déchargeur crée");
    }

    public void dechargerChariot(){
        while (true){
            try {                
                chariot.vide();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}