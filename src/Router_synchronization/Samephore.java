//semaphore to control which thread (device) will link with the router
package Router_synchronization;

import java.io.IOException;
import java.util.concurrent.Semaphore;

// as in the lab 
public class Samephore {
	protected int value = 0; // initial value 

	protected Samephore() {
		value = 0;
	}

	protected Samephore(int initial) {
		value = initial;
	}
	
	protected void setValue(int value) {
		this.value = value;
		
	}
	
	
	public synchronized void P(Device device) throws InterruptedException , IOException { // to make sure one thread will do this operation so we used (synchronized)
		value --; // we decrement the value of buffer because there is device take a place in the router
		if ( value < 0 ) // that main there is a waiting here
		{
            Files file =new Files("(" + device.getname() + ")" + "(" + device.getType()+ ")" + "arrived and waiting");// in the waiting area, wait to connect to router (waiting)
            System.out.println("(" + device.getname() + ")" + "(" + device.getType()+ ")" + "arrived and waiting");
            wait();
		}else {
            Files file =new Files("(" + device.getname() + ")" + "(" + device.getType() + ")" + "arrived");// the device in the runnable area and will run 
            System.out.println("(" + device.getname() + ")" + "(" + device.getType() + ")" + "arrived");
		}
	}
	
	public synchronized void V(Device device) { // to make sure one thread will do this operation so we used (synchronized)
		value ++;// we increment the value of buffer because there is device end the connect with the router
		if (value <= 0) notify() ; // to notify the consumer that there is an available buffer to consume  
	}
}
