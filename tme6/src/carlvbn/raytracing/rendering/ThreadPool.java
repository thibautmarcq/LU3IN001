package carlvbn.raytracing.rendering;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;

public class ThreadPool {
    ArrayBlockingQueue<Runnable> jobs;
    List<Thread> workers;

    private class PoolWorker implements Runnable{
        public void run(){
            try {
                while(!Thread.interrupted()){
                    Runnable job = jobs.take();
                    job.run();
                }
            } catch (InterruptedException e) {
                System.out.println("Thread "+Thread.currentThread().getName()+" interrupted.");
            }
        }
    }

    public ThreadPool(int qsize, int nbWorker){
        jobs = new ArrayBlockingQueue<>(qsize);
        workers = new ArrayList<>();
        for (int i=0; i<nbWorker; i++){
            Thread t = new Thread(new PoolWorker());
            workers.add(t);
            t.start();
        }
    }

    public void submit(Runnable job) throws InterruptedException{
        jobs.put(job);
    }

    public void shutdown(){
        for (Thread t: workers){
            t.interrupt();
            try {
                t.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        workers.clear();
        jobs.clear();
    }
}
