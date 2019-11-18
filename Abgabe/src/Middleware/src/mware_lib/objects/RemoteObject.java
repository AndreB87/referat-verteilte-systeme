package mware_lib.objects;

import java.io.IOException;
import java.net.UnknownHostException;

import mware_lib.SocketController;

public class RemoteObject implements MWareObject {
	
	/**
	 * Modul zur Kommunikation
	 */
	private SocketController communication;
	
	/**
	 * Name des entfernten Objektes
	 */
	private String name;
	
	/**
	 * Hostname des entfernten Objektes
	 */
	private String host;
	
	/**
	 * Port des entfernten Objektes
	 */
	private int port;
	
	/**
	 * Flag, ob geloggt wird oder nicht
	 */
	private boolean debug;
	
	public RemoteObject(String name, String host, int port, boolean debug) {
		this.name = name;
		this.host = host;
		this.port = port;
		this.debug = debug;
	}
	
	public synchronized String callMethod(String message) throws UnknownHostException, IOException {
		communication = new SocketController(host, port, debug);
		communication.sendMessage(message);
		String received = communication.receiveMessage();
		communication.close();
		return received;
	}
	
	public String getName() {
		return name;
	}
	
	public String getHost() {
		return host;
	}
	
	public int getPort() {
		return port;
	}
	
	public String toString() {
		return String.format("RemoteObject - %s:%s:%d", name, host, port);
	}
	
}
