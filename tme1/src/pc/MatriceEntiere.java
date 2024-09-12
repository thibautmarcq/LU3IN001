package pc;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.security.DrbgParameters.NextBytes;

import javax.sound.sampled.Line;
import javax.swing.text.TabableView;

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
	
	

	
	
}