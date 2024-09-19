package pc.countwords;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class WordCount implements Runnable{

	private String filename;
	private int id;
	private int[] tab;

	public WordCount(String filename, int id, int[] tab){
		this.filename=filename;
		this.id=id;
		this.tab=tab;
	}

	public static int countWords(String filename) throws IOException {
		long startTime = System.currentTimeMillis();
		try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
			int total = 0;
			for (String line = br.readLine(); line != null; line = br.readLine()) {
				total += line.split("\\s+").length;
			}
			System.out.println("Time for file "+filename+" : "+(System.currentTimeMillis()-startTime) + " ms for "+ total + " words");
			return total;
		}
	}

	@Override
	public void run(){
		try {
			this.tab[this.id]=countWords(this.filename);
		} catch (IOException e) {
			System.err.println("Error reading file: " + this.filename);
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		long startTime = System.currentTimeMillis();
		int [] wordCount = new int[args.length];
		
		ArrayList<Thread> th = new ArrayList<>();
		for (int i = 0; i < args.length; i++) {
			th.add(new Thread(new WordCount(args[i], i, wordCount)));
			th.get(i).start();
		}
		for (Thread t: th){
			try {
				t.join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		System.out.println("Word count:" + Arrays.toString(wordCount));
		int total = 0;
		for (int count : wordCount) {
			total += count;
		}
		System.out.println("Total word count:" + total);
		System.out.println("Total time "+(System.currentTimeMillis()-startTime) + " ms");
	}
}
