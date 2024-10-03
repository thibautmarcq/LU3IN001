package pc.philo;

import java.util.concurrent.atomic.AtomicInteger;

public class Philosopher implements Runnable {
	private Fork left;
	private Fork right;
	private static AtomicInteger nbPhilo = new AtomicInteger(0);

	public Philosopher(Fork left, Fork right) {
		if (nbPhilo.get()==0){
			this.left = right;
			this.right = left;
		} else {
			this.left = left;
			this.right = right;
		}
		nbPhilo.incrementAndGet();
	}

    public void run() {
        try {
            while (!Thread.currentThread().isInterrupted()) {
                think();
                System.out.println(Thread.currentThread().getName() + " thinks");
                left.acquire();
                System.out.println(Thread.currentThread().getName() + " has one fork (left)");
                right.acquire();
                System.out.println(Thread.currentThread().getName() + " has one fork (right)");
                eat();
                System.out.println(Thread.currentThread().getName() + " eats");
                left.release();
                right.release();
				Thread.sleep(10);
            }
        } catch (InterruptedException e) {
            System.out.println(Thread.currentThread().getName() + " was interrupted");
            Thread.currentThread().interrupt();
        }
    }

	private void eat() {
		System.out.println(Thread.currentThread().getName() + " is eating");
	}

	private void think() {
		System.out.println(Thread.currentThread().getName() + " is thinking");
	}

}
