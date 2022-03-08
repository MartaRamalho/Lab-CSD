package pract01_Pool;

public class Kid extends Swimmer {
    public Kid(int id, Pool p) {super(id,p);}
    void swims() throws InterruptedException {pool.kidSwims();}
    void rests() throws InterruptedException {pool.kidRests();}
}