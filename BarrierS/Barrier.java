package BarrierS;
import java.util.concurrent.Semaphore;

public class Barrier {
	
	// add missing variables
   private int size;    //size of barrier
   Semaphore sem;
   int counter;
   private volatile boolean release;
   
	
	Barrier(int n) {
		// complete this constructor
      size = n;
      sem = new Semaphore(1);
      counter = 0;
	}
	
	public void b_wait() throws InterruptedException{
		// this is the only additional method you will need to complete
      try {
         //acquire the semaphore and increment the counter. Critical section
         sem.acquire();
         if(counter < size) {release = false;}
         counter++;
         sem.release();
         
         //check if all the threads have arrived
         if(counter == size) {
            //counter = 0;  //reset counter to 0
            release = true;
         }
         //else just wait in a spin loop and set release = true only after counter == size
         while(!release) {
            //do nothing...
         }
      } 
      catch (InterruptedException ex) {
         //not catching it too...
      }
	}

}
