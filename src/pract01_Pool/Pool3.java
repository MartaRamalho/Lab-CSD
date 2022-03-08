package pract01_Pool;

public class Pool3 extends Pool{ //max capacity
	int instructorsSwimming = 0;
	int kidsSwimming = 0;
	int cap, ki;
	public void init(int ki, int cap) {
		this.cap=cap;
		this.ki=ki;
	}
	
    public synchronized void kidSwims() throws InterruptedException{
    	while(instructorsSwimming==0 || (instructorsSwimming+kidsSwimming)>=cap || kidsSwimming>=instructorsSwimming*ki) {
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
    public synchronized void instructorSwims() throws InterruptedException {
    	while((instructorsSwimming+kidsSwimming)>=cap) {
    		log.waitingToSwim();
    		wait();
    	}
    	log.swimming();
    	instructorsSwimming++;
    	notifyAll();
    }
    public synchronized void instructorRests() throws InterruptedException {
    	while(instructorsSwimming==1 && kidsSwimming>0 || (((instructorsSwimming-1)*ki)<=kidsSwimming && kidsSwimming > 0)) {
    		log.waitingToRest();
    		wait();
    	}
    	instructorsSwimming--;
    	log.resting();
    	notifyAll();
    }
}
