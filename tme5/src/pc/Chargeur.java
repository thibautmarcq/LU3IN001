package pc;


public class Chargeur {
    private final AleaStock stock;
    private final Chariot chariot;

    public Chargeur(AleaStock stock, Chariot chariot){
        this.stock = stock;
        this.chariot = chariot;
        System.out.println("Chargeur crée");
    }

    public void chargerChariot(){
        while (true){
            try {                
                try {
                    AleaObjet obj = stock.popAleaObjet();
                    chariot.insere(obj);                    
                } catch (NullPointerException e) {
                    e.printStackTrace();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }


}
