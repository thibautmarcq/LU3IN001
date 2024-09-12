package pc;


public class MatriceEntiere{
	private int[][] tab;
	
	public MatriceEntiere(int nbLines, int nbColumns){
		/* Constructeur 
		 * -- Initialise le tab et met ses valeurs à 0.
		 */
		this.tab= new int[nbLines][nbColumns];
		for (int line=0; line<nbLines; line++) {
			for(int column=0; column<nbColumns; column++) {
				this.tab[line][column]=0;
				}
		}
	}

	
}