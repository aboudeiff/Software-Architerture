// the the devices (threads) and activities of the devices
package Router_synchronization;

import java.io.IOException;
import java.util.*;
import java.util.Scanner;
import java.util.Random;


public class Device extends Thread {
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
