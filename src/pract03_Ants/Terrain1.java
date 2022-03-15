package pract03_Ants;

import java.util.concurrent.locks.*;

public class Terrain1 implements Terrain{
	Viewer v;
	ReentrantLock lock;
	Condition isTaken;
	
    public  Terrain1 (int t, int ants, int movs, String msg) {
    	lock = new ReentrantLock();
    	isTaken = lock.newCondition();
        v=new Viewer(t,ants,movs,msg);
    }
    
    public void hi (int a) {
    	lock.lock();
    	try {
    		v.hi(a);
    	} finally {
    		lock.unlock();
    	}
    }
    public void bye (int a) {
    	lock.lock();
    	isTaken.signalAll();
    	try {
    		v.bye(a);
    	} finally {
    		lock.unlock();
    	}    
    }
    public void move (int a) throws InterruptedException {
        lock.lock();
    	try{
    		v.turn(a); 
    		Pos dest=v.dest(a); 
    		while (v.occupied(dest)) {
    			try {
    				isTaken.await(); 
    			} catch (InterruptedException e) {
                }
    			v.retry(a);
    		}
    		v.go(a); 
    		isTaken.signalAll();
    	} finally {
    		lock.unlock();
    	}
    }

}
