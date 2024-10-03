package pc.philo;

public class TestPhilo {

	public static void main (String [] args) {
		final int NB_PHIL = 5;
		Thread [] tPhil = new Thread[NB_PHIL];
		Fork [] tChop = new Fork[NB_PHIL];

		for (int i=0; i<NB_PHIL; i++){
			tChop[i] = new Fork();
		}

		for (int i=0; i<NB_PHIL; i++){
			tPhil[i] = new Thread(new Philosopher(tChop[i], tChop[(i+1)%NB_PHIL]));
			tPhil[i].start();
		}
		
		for (int i=0; i<NB_PHIL; i++){
			tPhil[i].interrupt();
		}

		for (int i = 0; i < NB_PHIL; i++) {
            try {
                tPhil[i].join();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }

		System.out.println("Fin du programme");

	}
}