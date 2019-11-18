package mware_lib;

import java.io.IOException;
import java.net.Socket;

import mware_lib.logging.Logger;
import mware_lib.objects.MWareObject;
import mware_lib.objects.MWareObjectWarehouse;

public class Dispatcher extends Thread {
	
	/**
	 * Name der LogFile
	 */
	private final static String LOGFILE = "Dispatcher.log";
	
	/**
	 * KommunikationsModul
	 */
	private SocketController communication; 
	
	/**
	 * Referenz zum NameService
	 */
	private MWareObjectWarehouse warehouse;
	
	private boolean debug;
	
	public Dispatcher(Socket client, MWareObjectWarehouse warehouse, boolean debug) {
		try {
			this.communication = new SocketController(client, debug);
			this.warehouse = warehouse;
			this.debug = debug;
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void run() {
		try {
			String received = communication.receiveMessage();
			log("Nachricht erhalten:\n    " + received);
			String[] messageParts = received.split("!");
			
			MWareObject object = warehouse.getObject(messageParts[0]);
			
			String result = object.callMethod(received);
			
			communication.sendMessage(result);
			
			communication.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void log(String message) {
		if (debug) {
			try {
				Logger.quickLog(LOGFILE, message);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
