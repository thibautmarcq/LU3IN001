package pc;
import pc.TaillesNonConcordantesException;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;


public class MatriceEntiere{
	private int[][] tab;
	
	public MatriceEntiere(int nbLines, int nbColumns){
		/* Constructeur 
		 * -- Initialise le tab et met ses valeurs à 0.
		 */
		this.tab = new int[nbLines][nbColumns];
		for (int line=0; line<nbLines; line++) {
			for(int column=0; column<nbColumns; column++) {
				this.tab[line][column]=0;
				}
		}
	}

	public int getElem(int lig, int col) {
		/* Renvoie l'élément de ligne lig et de colonne col */
		return tab[lig][col];
	}
	
	public void setElem(int lig, int col, int val) {
		/* Initialise l'élément de ligne lig et de colonne col avec val */
		tab[lig][col]=val;
	}
	
	public int nbLignes() {
		return tab.length;
	}
	
	public int nbColonnes() {
		return tab[0].length;
	}
	
	

	
    public static MatriceEntiere parseMatrix(File fichier) throws IOException {
        BufferedReader in = null;
        MatriceEntiere mat = null;

        try {
            in = new BufferedReader(new FileReader(fichier));
            
            String line = in.readLine();
            if (line == null) {
                throw new IOException("Le fichier est vide ou mal formaté.");
            }
            int nbLines = Integer.parseInt(line);

            line = in.readLine();
            if (line == null) {
                throw new IOException("Le fichier est mal formaté, pas assez de lignes pour les dimensions.");
            }
            int nbColumns = Integer.parseInt(line);

            mat = new MatriceEntiere(nbLines, nbColumns);

            int row = 0;
            while ((line = in.readLine()) != null && row < nbLines) {
                String[] values = line.split(" ");
                for (int col = 0; col < nbColumns; col++) { // ajout des données de la ligne
                    mat.setElem(row, col, Integer.parseInt(values[col]));
                }
                row++;
            }

        } finally {
            if (in != null) {
                in.close();
            }
        }

        return mat;
    }
	
    @Override
	public String toString(){
        String res = "";
        res+=this.nbLignes()+"\n"+this.nbColonnes()+"\n";
        
        for (int i=0; i<nbLignes(); i++){
            for (int j=0; j<nbColonnes(); j++){
                res+=getElem(i, j)+" ";
            }
            res+="\n";
        }
        return res;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || !(o instanceof MatriceEntiere)) return false;

        // test valeur par valeur
        MatriceEntiere tmp = (MatriceEntiere) o;
        if (this.nbLignes() != tmp.nbLignes() || this.nbColonnes() != tmp.nbColonnes()) return false;
        for (int i = 0; i < this.nbLignes(); i++) {
            for (int j = 0; j < this.nbColonnes(); j++) {
                if (this.getElem(i, j) != tmp.getElem(i, j)) return false;
            }
        }
        return true;
    }

    public MatriceEntiere ajoute(MatriceEntiere m) throws TaillesNonConcordantesException{
        if ((this.nbLignes()!=m.nbLignes()) || (this.nbColonnes()!=m.nbColonnes())){
            throw new TaillesNonConcordantesException();
        }

        MatriceEntiere res = new MatriceEntiere(nbLignes(), nbColonnes());
        for (int i=0; i<nbLignes(); i++){
            for (int g=0; g<nbColonnes(); g++){
                res.setElem(i, g, m.getElem(i, g)+this.getElem(i, g));
            }
        }
        return res;
    }

    public MatriceEntiere produit(MatriceEntiere m) throws TaillesNonConcordantesException {
        if (this.nbColonnes() != m.nbLignes()) {
            throw new TaillesNonConcordantesException();
        }
        MatriceEntiere res = new MatriceEntiere(this.nbLignes(), m.nbColonnes());
    
        for (int i = 0; i < this.nbLignes(); i++) {
            for (int j = 0; j < m.nbColonnes(); j++) {
                int sum = 0;
                for (int k = 0; k < this.nbColonnes(); k++) {
                    sum += this.getElem(i, k) * m.getElem(k, j);
                }
                res.setElem(i, j, sum);
            }
        }
    
        return res;
    }


    public MatriceEntiere transposee() {
        MatriceEntiere res = new MatriceEntiere(this.nbColonnes(), this.nbLignes());
        for (int i = 0; i < this.nbLignes(); i++) {
            for (int j = 0; j < this.nbColonnes(); j++) {
                res.setElem(j, i, this.getElem(i, j));
            }
        }
        return res;
    }

}

