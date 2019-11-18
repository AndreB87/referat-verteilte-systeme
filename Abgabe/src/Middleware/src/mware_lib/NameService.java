package mware_lib;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;

public abstract class NameService {
	
	/**
	 * Verbindung zum Nameservice
	 */
	protected SocketController nameServiceSocket;
	
	protected boolean debug;
	/**
	 * Aufbau einer neuen Verbindung zum NameService
	 * @param host
	 * @param port
	 * @param debug
	 */
	public NameService(String host, int port, boolean debug) {
		try {
			this.debug = debug;
			Socket socket = new Socket(InetAddress.getByName(host), port);
			this.nameServiceSocket = new SocketController(socket, debug);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * Binden eines neuen Objektes im Namensdienst
	 * 
	 * @param servant Objekt, das im Namensdienst gebunden werden soll
	 * @param name Name des Objektes
	 */
	public abstract void rebind(Object servant, String name) throws IOException;
	
	/**
	 * Abfragen eines Objekts aus dem Namensdienst
	 * 
	 * @param name Name des Objekts, dass beim Namensdienst angefragt wird
	 * @return Objekt mit dem Namen
	 */
	public abstract Object resolve(String name) throws IOException;
	
	public void closeConnection() {
		try {
			nameServiceSocket.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
