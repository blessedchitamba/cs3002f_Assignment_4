package dishWashS;
import java.util.concurrent.Semaphore;
import java.util.ArrayList;

public class WetDishRack {
	// add variables
   private int rackSize;
   private int removedDish;
   Semaphore mutex;
   Semaphore full;
   Semaphore empty;
   ArrayList<Integer> rack;

	WetDishRack(int rackSize) {
	    // add correct code here
       mutex = new Semaphore(1);
       full = new Semaphore(0);
       empty = new Semaphore(rackSize);
       this.rackSize = rackSize;
       rack = new ArrayList<Integer>(rackSize);
	}
	
	public void addDish(int dish_id)  throws InterruptedException {
		// add correct code here
      //wait on the empty semaphore and only add a dish once a free spot exists
      //also wait on the mutex to ensure only one thread changes the rack
      try{
         empty.acquire();
         mutex.acquire();
         
         rack.add(dish_id);
         
         mutex.release();
         full.release();
      }
      catch (InterruptedException ex) {
         //not catching it too...
      }

	}
	
	public int removeDish() throws InterruptedException {
      try {
         full.acquire();
         mutex.acquire();
         
         removedDish = rack.get(0);
         rack.remove(rack.get(0));
         
         mutex.release();
         empty.release();
         
      }
      catch (InterruptedException ex) {
         //not catching it too...
      }
      
      return removedDish;

	}
	
}



