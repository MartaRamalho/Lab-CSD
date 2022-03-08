package pract01_Pool;

public class Pool4 extends Pool { //kids cannot enter if there are instructors waiting to exit
	int instructorsSwimming = 0;
	int kidsSwimming = 0;
	int cap, ki;
	int instructorsWaiting=0;
	public void init(int ki, int cap) {
		this.cap=cap;
		this.ki=ki;
	}
	
    public synchronized void kidSwims() throws InterruptedException{
    	while(instructorsWaiting>0 || instructorsSwimming==0 || (instructorsSwimming+kidsSwimming)>=cap || kidsSwimming>=instructorsSwimming*ki) {
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
    	boolean hasWaited = false;
    	if(instructorsSwimming==1 && kidsSwimming>0 || (((instructorsSwimming-1)*ki)<=kidsSwimming && kidsSwimming > 0)) {
    		hasWaited=true;
    		instructorsWaiting++;
    	}
    	while(instructorsSwimming==1 && kidsSwimming>0 || (((instructorsSwimming-1)*ki)<=kidsSwimming && kidsSwimming > 0)) {
    		log.waitingToRest();
    		wait();
    	}
    	if(hasWaited) instructorsWaiting--;
    	instructorsSwimming--;
    	log.resting();
    	notifyAll();
    }
}
