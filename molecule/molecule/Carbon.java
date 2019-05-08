package molecule;

public class Carbon extends Thread {
	
	private static int carbonCounter =0;
	private int id;
	private Methane sharedMethane;
	
	public Carbon(Methane methane_obj) {
		Carbon.carbonCounter+=1;
		id=carbonCounter;
		this.sharedMethane = methane_obj;
	}
	
	public void run() {
	    try {	 
         //acquire mutex and increment C in methane
         sharedMethane.mutex.acquire();
         sharedMethane.addCarbon();
         //System.out.println("Carbon "+this.id+" added to methane variable");
         
         if( (sharedMethane.getHydrogen() >= 4) ) {
            sharedMethane.carbonQ.release();
            sharedMethane.removeCarbon(1);
            sharedMethane.hydrogensQ.release(4);
            sharedMethane.removeHydrogen(4);
         }
         
         else { sharedMethane.mutex.release(); }
        
         
         //thread adds itself to carbonQ waiting queue
         sharedMethane.carbonQ.acquire();
         
         
         //once released, print message and move into reusable barrier
         System.out.println("---Group ready for bonding---");
         sharedMethane.barrier.b_wait();
         	
	    	sharedMethane.bond("C"+ this.id);  //bond
         sharedMethane.mutex.release();
	    	 
	    	 
	    	   	 
	    }
	    catch (InterruptedException ex) { /* not handling this  */}
	   // System.out.println(" ");
	}

}
