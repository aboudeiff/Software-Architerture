//the array list of the devices and occupy,release methods
package Router_synchronization;

import java.util.Random;
import java.io.IOException;
import java.util.ArrayList;

public class Router {
	
	private int size; // the max number of connections a router can accept
    private int inptr = 0; // input pointer for occupy a connection in the router
    private int outptr = 0; // output pointer for release a connection from the router
    private int pointer = 0; // pointer to return the place of the connection
    ArrayList<String> connections = new ArrayList<String>();
   
    
    Files file; // an object to save the output data
    Samephore semaphore = new Samephore(size);
   
    
   
    public Router(int sizee) {

        size = sizee;
        semaphore.setValue(size);
        for (int i = 1; i <= size; i++) {
            connections.add("connection" + i); // add the device with a number
        }
    }
    
    
       
    // this function to set the number of connected devices
    public void setNumofConnections(int NumofConnections) {
    	size = NumofConnections ;
    }
    
    
    // this function to return the place of connected devices in the array list
    public String getConnectionPalce(Device device) {
        String connectionPlace;
        connectionPlace = connections.get(pointer); // put the place of the connection in the variable and return it
        pointer = (pointer + 1) % size; // to back to the first element if we the array list has ended
        return connectionPlace; // the place of the device in the array list
    }
    
    
    
    //this function to occupy a device
    public void occupy(Device device) throws InterruptedException, IOException  { 
    	 this.semaphore.P(device); // to check if the space is greater than 0 or not , if there is a available buffer to connect to router will occupy a place in the router
    	 Files file =new Files(device.getConnection() +": "+ device.getname() +" Occupied"); // save in file
    	 System.out.println(device.getConnection() +": "+ device.getname() +" Occupied");
    }
    
    
    //this function to release a device
    public void release(Device device)  {
    	
    	semaphore.V(device);// end connect with the router
    }
    
    
     
    
}
