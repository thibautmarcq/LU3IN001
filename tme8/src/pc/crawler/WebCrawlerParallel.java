package pc.crawler;
import java.io.*;
import java.net.*;
import java.nio.file.*;
import java.util.*;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;


public class WebCrawlerParallel {

    public static class Pair{   //(url, depth)
        private final String url;
        private final int depth;

        public Pair(String url, int depth){
            this.url=url;
            this.depth=depth;
        }

        public String getURL(){
            return url;
        }

        public int getDepth(){
            return depth;
        }
    }

    private static final int NB_THREADS = 4;

    public static void main(String[] args) {
        // Hardcoded base URL to start crawling from
        String baseUrl = "https://www-licence.ufr-info-p6.jussieu.fr/lmd/licence/2023/ue/LU3IN001-2023oct/index.php";
        
        // Hardcoded output directory where downloaded pages will be saved
        Path outputDir = Paths.get("/tmp/crawler/");
        
        try {
            // Ensure the output directory exists; create it if it doesn't
            if (!Files.exists(outputDir)) {
                Files.createDirectories(outputDir);
                System.out.println("Created output directory: " + outputDir.toAbsolutePath());
            }

            BlockingQueue<Pair> bq = new LinkedBlockingQueue<>();
            ConcurrentHashMap<String, Boolean> visitedUrls = new ConcurrentHashMap<>;
            bq.add(new Pair(baseUrl, 10));
            ExecutorService exec = Executors.newFixedThreadPool(NB_THREADS);

            for (int i=0; i<NB_THREADS; i++){
                exec.submit(new CrawlerTask(bq, outputDir));
            }
            //shutdown et fin
            try {
                Thread.sleep(10000);
            } catch (Exception e) {
                System.out.println("prout");
            }
            System.out.println("prout");
            exec.close();
        } catch (IOException e){ //exception de createDirectories
            e.printStackTrace();
        }
    }

    private static class CrawlerTask implements Runnable{
        private final BlockingQueue<Pair> bq;
        private final Path outputDir;
        private final ConcurrentHashMap<String, Boolean> visitedURLs;

        public CrawlerTask(BlockingQueue<Pair> bq, Path outputDir, ConcurrentHashMap<String, Boolean> visitedURLs){
            this.bq=bq;
            this.outputDir=outputDir;
            this.visitedURLs=visitedURLs;
        }

        @Override
        public void run(){
            while(true){
                try {
                    Pair pair=bq.take();
                    String url=pair.getURL();
                    int depth=pair.getDepth();
                    
                    if (depth>=0 && visitedURLs.putIfAbsent(url, true)==null){ // l'url n'est pas encore dans les URL visités
                        System.out.println("Processing (Depth "+depth+"): " + url);
                        List<String> extractedUrls = Collections.emptyList();

                        try {
                            extractedUrls = WebCrawlerUtils.processUrl(url, url, outputDir);
                        } catch (URISyntaxException|IOException e) {
                            System.err.println("Error during crawling: " + e.getMessage());
                        }

                        for (String extrURL: extractedUrls){
                            bq.add(new Pair(extrURL, depth-1));
                        }

                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        }
    }
            		
    // Ne sert plus: les depth 1 ou plus sont directement rajoutées à la queue
            // // Check if there are URLs extracted to process at depth 1
            // if (extractedUrls.isEmpty()) {
            //     System.out.println("No URLs found to process at depth 1.");
            // } else {
            //     // Process each extracted URL (depth 1)
            // 	for (String url : extractedUrls) {
            // 		System.out.println("Processing (Depth 1): " + url);
            // 		try {
            // 			WebCrawlerUtils.processUrl(url, baseUrl, outputDir);
            // 		} catch (URISyntaxException|IOException e) {
            // 			System.err.println("Error during crawling: " + e.getMessage());
            // 		}
            // 	}
            // }

        //     System.out.println("Sequential crawling completed successfully.");

        // } catch (IOException e) {
        //     // Handle exceptions that may occur during crawling
        //     System.err.println("Error during crawling: " + e.getMessage());
        //     e.printStackTrace();
        // }
    

}