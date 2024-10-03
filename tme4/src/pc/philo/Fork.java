package pc.philo;

import java.util.concurrent.locks.ReentrantLock;

public class Fork {

	private ReentrantLock lock;

	public Fork(){
		this.lock = new ReentrantLock();
	}
	
	public void acquire () {
		lock.lock();
    }
	
	
	public void release () {
		lock.unlock();
	}
}
