package pract01_Pool;

public class Pool2 extends Pool{ //max kids/instructor
	int instructorsSwimming = 0;
	int kidsSwimming = 0;
	int ki;
	int cap;

	public void init(int ki, int cap) {
		this.ki=ki;
		this.cap=cap;
	}
	
    public synchronized void kidSwims() throws InterruptedException{
    	while(instructorsSwimming==0 || kidsSwimming>=instructorsSwimming*ki) {
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
    	notifyAll();
    }
    
    public synchronized void instructorRests() throws InterruptedException {
    	while(instructorsSwimming==1 && kidsSwimming > 0 || (((instructorsSwimming-1)*ki)<=kidsSwimming && kidsSwimming > 0)){
            log.waitingToRest();
            wait();
        }
    	instructorsSwimming--;
    	log.resting();
    	notifyAll();
    }
}

