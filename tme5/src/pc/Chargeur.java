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
        while (!(stock.isEmpty())){
            try {                
                AleaObjet obj = stock.popAleaObjet();
                chariot.insere(obj);                    

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("Fin chargement chargeur");
    }


}
