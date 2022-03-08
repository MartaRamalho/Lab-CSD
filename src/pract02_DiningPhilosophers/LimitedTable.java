package pract02_DiningPhilosophers;

// CSD Mar 2013 Juansa Sendra

public class LimitedTable extends RegularTable { //max 4 in dinning-room
    private int philoAtTable;
	public LimitedTable(StateManager state) {super(state);}
    
    public synchronized void enter(int id) throws InterruptedException {
    	while(philoAtTable==4) {
    		state.wenter(id);
    		wait();
    	}
    	state.enter(id);
    	philoAtTable++;
    	notifyAll();
    }
    public synchronized void exit(int id)  {
    	state.exit(id);
    	philoAtTable--;
    	notifyAll();
    }
}
