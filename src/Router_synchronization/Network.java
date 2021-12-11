
package Router_synchronization;

import java.util.Random;
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.Semaphore;
import java.util.*;
import java.util.Scanner;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;


//the array list of the devices and occupy,release methods
class Router {
	
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

//***********************************************************************************************************************

//semaphore to control which thread (device) will link with the router
//as in the lab 
class Samephore {
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

//************************************************************************************************************************
 
 
 class Device extends Thread {
	    private String current_connection;
		private String name; // the name of the device
		private String type; // the type of the device
		private Router router;

		Random random = new Random(); 
		
		
		// parameterized constructor
		public Device(String name, String type, Router router) {
			this.name = name;
			this.type = type;
			this.router = router;
		}

		
		public void setRouter(Router router) {
			this.router = router;
		}

		public void setname(String name) {
			this.name = name;
		}

		public void setType(String type) {
			this.type = type;
		}
		
		public void setConnection(String current_connection) {
			this.current_connection = current_connection;
		}

		
		
		public Router getRouter() {
			return router;
		}

		public String getname() {
			return name;
		}

		public String getType() {
			return type;
		}
		// connection ely ana sha8al 3leeh dilwa2ty ely hwa anhy (device) sha8al mn ely mwgooden fel array list
		public String getConnection() {
			return current_connection;
		}
		
		
		
	    
		// the first activity of the device 
		public void connect() throws InterruptedException, IOException {
			current_connection = router.getConnectionPalce(this); // put the place of the device (getConnectionPlace) that is already connected to the router in the (current_connection) 
			router.occupy(this);
		}

		
		// the second activity of the device 
		public void online() throws IOException, InterruptedException {
	        Files file =new Files(current_connection + ": " + name + " performs online activity");
	        System.out.println(current_connection + ": " + name + " performs online activity");
	        sleep(1000);
		}
		
		
		// the third activity of the device 
		public void disconnect() throws IOException, InterruptedException{
	        Files file =new Files(current_connection + ": " + name + " Logged out");
	        System.out.println(current_connection + ": " + name + " Logged out");
	        router.release(this);
		}
		
		
		@Override
		public void run() {
	        try {
	            this.connect();
	            this.online();
	            this.disconnect();
	        } catch (InterruptedException | IOException e) {
	            e.printStackTrace();
	        }
			
		}
			
	}

//******************************************************************************************************************


 class Files {
	final String currentDirectory = System.getProperty("user.dir");
	private File logFile;
	public FileWriter Writer;

	public Files(String content) throws IOException {
        logFile = new File(currentDirectory + "\\" + "logFile.txt");
        Writer = new FileWriter(logFile, true);
        if (!logFile.exists()) {
            logFile.createNewFile();
        }
        Writer.write(content);// using this method we will push the output in the file
        Writer.write(System.getProperty("line.separator")); // to take a separate line 
        Writer.close();
    }

	public Files getInstance() {
		return this;
	}

}
 
 //****************************************************************************************************************
 
public class Network {
		
		
		
		
		
	       public static void main(String[] args) throws InterruptedException, IOException  {
		    final String currentDirectory = System.getProperty("user.dir");
			int N = 0; //max number of connections a router can accept
			int TC = 0; //total number of devices that wish to connect
			String name; // the name of connected devices
			String type ; // the type of connected devices
			ArrayList<Device> allDevices = new ArrayList<Device>();
			//Device[] allDevices; // array of all possible devices 
			Router router ; // object from router to use it in perform and consume
			
			
			Scanner input =  new Scanner (System.in);
			Scanner str= new Scanner(System.in);
			
			System.out.println("What is number  of WI-FI Connections?");
			N = input.nextInt();
			router = new Router(N);
			System.out.println("What is number of devices Clients want to connect?");
			TC = input.nextInt();
	       // allDevices = new Device[TC];
	        for (int i = 0 ; i < TC ; i ++) {
	        	System.out.println("Please enter the name and type of the device like 'name (i.e. C1) and type (i.e. mobile, pc, tablet...)' ");
	        	name = str.next();
	        	type = str.next();
	        	allDevices.add(new Device(name,type,router));
	        	//allDevices[i] =  new Device("C"+String.valueOf(i+1), type, router);
	        }
	        
	        
	        for (int i = 0; i < TC; i++ ){

	            allDevices.get(i).start();
	           // Thread.sleep(time);
	        }
	 
	        File f1=new File(currentDirectory + "\\" + "logFile.txt");
	        f1.delete();
	 }

	

}

