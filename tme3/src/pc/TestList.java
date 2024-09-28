package pc;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import pc.iter.SimpleListSync;
import pc.rec.SimpleListRecSync;

public class TestList {

	@Test
	public void testSimpleListSync() {
		IList<String> list = new SimpleListSync<>();

		runConcurrentTest(list, 10, 1000);
	}

	@Test
	public void testSimpleListRecSync() {
		IList<String> list = new SimpleListRecSync<>();

		runConcurrentTest(list, 10, 1000);
	}

	public static void testList(IList<String> list) {
		// Tests des versions itératives
		list.add("Hello");
		list.add("World");
		System.out.println("Taille : " + list.size());
		assertEquals(2, list.size());
		System.out.println("Contient 'World' : " + list.contains("World"));
		assertEquals(true, list.contains("World"));
		assertEquals(false, list.contains("Master"));
		
		list.clear();
		assertEquals(0, list.size());
		System.out.println("Taille après clear : " + list.size());
	}

	private void runConcurrentTest(IList<String> list, int N, int M) {
		System.out.println("Running test of "+list.getClass().getSimpleName());
		testList(list);
		
		long startTime = System.currentTimeMillis();

		List<Thread> threadsAdd = new ArrayList<>();
		List<Thread> threadsContains = new ArrayList<>();

		// Create threads to add elements to the list
		class AddListTh implements Runnable{
			private IList<String> list;
			private int M;

			public AddListTh(IList<String> list, int M){
				this.list=list;
				this.M=M;
			}

			@Override
			public void run(){
				for (int i=0; i<M; i++){
					list.add(Integer.toString(i));
				}
			}
		}

		for (int i=0; i<N; i++){
			threadsAdd.add(new Thread(new AddListTh(list, M)));
			threadsAdd.get(i).start();
		}
		System.out.println("test");
		// Create threads to check contains for non-existent elements
		class ContainsListTh implements Runnable{
			private IList<String> list;
			private int M;

			public ContainsListTh(IList<String> list, int M){
				this.list=list;
				this.M=M;
			}

			@Override
			public void run(){
				for (int i=0; i<M; i++){
					assertFalse(list.contains("coucou"));
				}

			}
		}

		for (int i=0; i<N; i++){
			threadsContains.add(new Thread(new ContainsListTh(list, M)));
			threadsContains.get(i).start();
		}
		
		// Start all threads

		// Wait for all threads to finish
        for (Thread t : threadsAdd) { /* fin de fonction, on attend que les threads se finissent */
            try {
                t.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
		for (Thread t : threadsContains) { /* fin de fonction, on attend que les threads se finissent */
            try {
                t.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
		// Check that the list size is N * M
		assertEquals(list.size(), N*M);
		System.out.println("Taille de la liste : "+list.size()+ "taille attendue "+N*M);
		// assertEquals("List size should be N * M", N * M, list.size());

		long endTime = System.currentTimeMillis();
		System.out.println("Test completed in " + (endTime - startTime) + " milliseconds");



	}

	// TODO support pour les threads
	// static class AddTask implements Runnable {

	// 	@Override
	// 	public void run() {
	// 	}
	// }

}

