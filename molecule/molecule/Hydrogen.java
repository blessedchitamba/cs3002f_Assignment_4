package molecule;

public class Hydrogen extends Thread {

	private static int carbonCounter =0;
	private int id;
	private Methane sharedMethane;
	
	
	public Hydrogen(Methane methane_obj) {
		Hydrogen.carbonCounter+=1;
		id=carbonCounter;
		this.sharedMethane = methane_obj;
		
	}
	
	public void run() {
	    try {
         //acquire mutex and do the stuff
         sharedMethane.mutex.acquire();
         sharedMethane.addHydrogen();
         
         //for every fourth H, release C and 4 H
         if( (sharedMethane.getHydrogen() >= 4) && (sharedMethane.getCarbon() >= 1) ) {
            sharedMethane.carbonQ.release();
            sharedMethane.removeCarbon(1);
            sharedMethane.hydrogensQ.release(4);
            sharedMethane.removeHydrogen(4);
         }
         
         else { sharedMethane.mutex.release(); }
         
         sharedMethane.hydrogensQ.acquire();
         
         //once released, move to barrier for waiting
         sharedMethane.barrier.b_wait();
         
         //once allowed to pass, bond
         sharedMethane.bond("H"+ this.id);
       
	    }
	   catch (InterruptedException ex) { /* not handling this  */}
	    //System.out.println(" ");
	}

}
