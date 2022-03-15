package pract03_Ants;

import java.util.concurrent.locks.*;

public class Terrain2 implements Terrain{
	Viewer v;
	ReentrantLock lock;
	Condition[][] isTaken;
	private int gridSize;
    public  Terrain2 (int t, int ants, int movs, String msg) {
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
    	Pos pos=v.getPos(a); 
    	isTaken[pos.x][pos.y].signalAll();
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
    		Pos pos = v.getPos(a);
    		while (v.occupied(dest)) {
    			try {
    				isTaken[dest.x][dest.y].await(); 
    			} catch (InterruptedException e) {
                }
    			v.retry(a);
    		}
    		v.go(a); 
    		isTaken[pos.x][pos.y].signalAll();
    	} finally {
    		lock.unlock();
    	}
    }

}
