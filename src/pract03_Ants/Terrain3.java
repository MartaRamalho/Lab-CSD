package pract03_Ants;

import java.util.concurrent.locks.*;
import java.util.concurrent.TimeUnit;

public class Terrain3 implements Terrain{
	Viewer v;
	ReentrantLock lock;
	Condition[][] isTaken;
	private int gridSize;
	
    public  Terrain3 (int t, int ants, int movs, String msg) {
    	gridSize=t;
    	lock = new ReentrantLock();
    	isTaken=new Condition[gridSize][gridSize];
        v=new Viewer(t,ants,movs,msg);
        for(int i = 0; i<gridSize;i++) {
        	for(int j = 0; j<gridSize; j++) {
        		isTaken[i][j]=lock.newCondition();
        	}
        }
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
    				Boolean timeNotOver = isTaken[dest.x][dest.y].await(300, TimeUnit.MILLISECONDS);
    				if(!timeNotOver) {
    					v.chgDir(a);
    					dest=v.dest(a);
    				} else v.retry(a);
    			} catch (InterruptedException e) {}
    		}
    		v.go(a); 
    		isTaken[dest.x][dest.y].signalAll();
    	} finally {
    		lock.unlock();
    	}
    }

}
