package Router_synchronization;

import java.util.Scanner;
import java.util.Random;
import java.io.IOException;
import java.util.ArrayList;
import java.io.File;

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
