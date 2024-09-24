package pc.test;

import static org.junit.Assert.fail;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.junit.jupiter.api.Test;

import pc.MatriceEntiere;
import pc.TaillesNonConcordantesException;

public class TestMatriceEntiereMT {
	@Test
	public void testOriginal() {
		
		// -------------------- Test de comparaison entre avec ou sans thread et affichage
		
		try {
			
			MatriceEntiere m2 = MatriceEntiere.parseMatrix(new File("data/donnees_produit1"));
			MatriceEntiere m4 = MatriceEntiere.parseMatrix(new File("data/donnees_produit2"));
			
			
			// -------------------- Test produit
			System.out.println("------------------ Test produit ------------------");
			System.out.println(m2.toString() + "*\n" + m4.toString() + "=\n");
			try {
				long startTime = System.currentTimeMillis();
				MatriceEntiere mat1 = m2.produit(m4);
				long final1 = System.currentTimeMillis()-startTime;
				assertEquals(20, mat1.nbLignes());
				assertEquals(20, mat1.nbColonnes());
				System.out.println(mat1.toString());
				
				System.out.println("------------------ Test produitTM ------------------");
				startTime = System.currentTimeMillis();
				MatriceEntiere mat2 = m2.produit(m4);
				long final2 = System.currentTimeMillis()-startTime;
				assertEquals(20, mat2.nbLignes());
				assertEquals(20, mat2.nbColonnes());
				System.out.println(mat2.toString());
				checkValues(mat1, mat2);
				
				System.out.println("Time : "+(final1) + " ms pour test produit sans thread");
				System.out.println("Time : "+(final2) + " ms pour test produit avec thread");
			} catch (TaillesNonConcordantesException e) {
				System.out.println(e.getMessage());
				fail("Exception non attendue");
			}
			
			
			// -------------------- Test produit scalaire
			{
				System.out.println("------------------ Test scalaire ------------------");
				System.out.println(m2.toString() + "*\n3 \n=\n");
				long startTime = System.currentTimeMillis();
				MatriceEntiere mat1 = m2.produitParScalaire(3);
				long final3 = System.currentTimeMillis()-startTime;
				assertEquals(20, mat1.nbLignes());
				assertEquals(20, mat1.nbColonnes());
				System.out.println(mat1);
				
				System.out.println("------------------ Test scalaireMT ------------------");
				startTime = System.currentTimeMillis();
				MatriceEntiere mat2 = m2.produitParScalaireMT(3);
				long final4 = System.currentTimeMillis()-startTime;
				assertEquals(20, mat2.nbLignes());
				assertEquals(20, mat2.nbColonnes());
				System.out.println(mat2);
				checkValues(mat1, mat2);
				
				System.out.println("Time : "+(final3) + " ms pour test scalaire sans thread");
				System.out.println("Time : "+(final4) + " ms pour test scalaire avec thread");
			}
			
		} catch (FileNotFoundException e) {
			System.out.println(e.getMessage());
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
	}
	
	
	private void checkValues(MatriceEntiere mat1, MatriceEntiere mat2) {
		for (int i = 0; i < mat1.nbLignes() * mat1.nbColonnes(); i++) {
			assertEquals(mat1.getElem(i / mat1.nbColonnes(), i % mat1.nbColonnes()), mat2.getElem(i / mat2.nbColonnes(), i % mat2.nbColonnes()));
		}
	}

}
