package pc;

public class AleaChariot {
    private int poidsMax;
    private int nbMax;

    private AleaObjet[] chariot;
    private int idLastObjet;
    private int cptPoids;

    public AleaChariot(int poidsMax, int nbMax){
        this.poidsMax = poidsMax;
        this.nbMax = nbMax;
        chariot = new AleaObjet[nbMax];
        idLastObjet = 0;
        cptPoids = 0;
    }

    public boolean poserMarchandise (AleaObjet marchandise){
        if ((idLastObjet < nbMax) && (cptPoids+marchandise.getPoids()<=poidsMax)){
            chariot[idLastObjet] = marchandise;
            idLastObjet++;
            cptPoids += marchandise.getPoids();
            return True;
        }
        return false;
    }
}
