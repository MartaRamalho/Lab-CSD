package pract01_Pool;

public class Pool1 extends Pool {   //no kids alone
	int instructorsSwimming = 0;
	int kidsSwimming = 0;
	public void init(int ki, int cap) {
		
	}
	
    public synchronized void kidSwims() throws InterruptedException{
    	while(instructorsSwimming==0) {
    		log.waitingToSwim();
    		wait();
    	}
    	log.swimming();
    	kidsSwimming++;
    	notifyAll();
    }
    public synchronized void kidRests() {
    	log.resting();
    	kidsSwimming--;
    	notifyAll();
    }
    public synchronized void instructorSwims() {
    	log.swimming();
    	instructorsSwimming++;
    }
    public synchronized void instructorRests() throws InterruptedException {
    	while(instructorsSwimming==1 && kidsSwimming>0) {
    		log.waitingToRest();
    		wait();
    	}
    	instructorsSwimming--;
    	log.resting();
    	notifyAll();
    }
}
