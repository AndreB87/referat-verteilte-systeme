package mware_lib;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Random;

import mware_lib.logging.Logger;
import mware_lib.objects.MWareObject;
import mware_lib.objects.MWareObjectWarehouse;

public class ObjectBroker extends Thread { // FrontEnd der Middleware
	
	/**
	 * Schnittstelle zum NameService
	 */
	private NameService nameService;
	
	/**
	 * Speichert alle LocalObjects, um mehrere Objekte ueber ein Socket ansprechen zu koennen
	 */
	private MWareObjectWarehouse warehouse;
	
	/**
	 * Socket des ObjectBrokers
	 */
	private ServerSocket serverSocket;
	
	/**
	 * Debug-Flag
	 */
	private boolean debug;

	/**
	 * Name der LogFile
	 */
	private String logFile;
	
	/**
	 * Flag, ob der Server laeuft
	 */
	private boolean running;
	
	/**
	 * Erstellen eines neuen ObjectBrokers, der eine Verbindung zum NameService aufbaut
	 * und darueber eine Verbindung zum angefragten Objekt erstellt
	 * 
	 * @param serviceHost Name des Hostes, der beim Nameservice angefragt werden soll
	 * @param listenPort Port des Hostes der beim Nameservice angefragt werden soll
	 * @param debug true - Debugging ist eingeschaltet und Logausgaben werden ausgegeben
	 * 				false - Debugging ist ausgeschaltet und Logausgaben werden nicht ausgegeben
	 * @throws UnknownHostException
	 */
	private ObjectBroker(String serviceHost, int listenPort, boolean debug) throws UnknownHostException {
		String[] localHost = InetAddress.getLocalHost().toString().split("/");
		String host = localHost[localHost.length - 1];
		this.warehouse = new MWareObjectWarehouse(host, 0);
		this.nameService = new NameServiceImpl(serviceHost, listenPort, warehouse, debug);
		this.debug = debug;
		
		if (debug) {
			logFile = "ObjectBroker.log";
			log("Neuer Object Broker initialisiert");				
		}
	}
	
	/**
	 * Startet einen neuen Middleware-Server
	 * @param port Port, auf dem der Server horcht
	 * @throws IOException
	 */
	public void startServer(int port) throws IOException {
		running = true;
		this.warehouse.setPort(port);
		this.serverSocket = new ServerSocket(port);
		this.start();
	}
	
	/**
	 * Startet einen Server mit einem Random Port (Automatisch)
	 */
	public void startRandomServer() {
		try {
			startServer(10000 + new Random().nextInt(5000));
		} catch (IOException e) {
			startRandomServer();
		}
	}
	
	/**
	 * Faehrt den Server herunter
	 * @throws IOException
	 */
	public void shutDown() throws IOException {
		running = false;
		this.interrupt();
		nameService.closeConnection();
		serverSocket.close();
	}
	
	@Override
	public void run() {
		while (!isInterrupted()) {
			try {
				Socket socket = this.serverSocket.accept();
				new Dispatcher(socket, warehouse, debug).start();
			} catch (SocketException e) {
				log("Socket wurde geschlossen");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * Erstellen einer Instanz des ObjectBrokers 
	 * @param serviceHost Name des Hostes, der beim Nameservice angefragt werden soll
	 * @param listenPort Port des Hostes der beim Nameservice angefragt werden soll
	 * @param debug true - Debugging ist eingeschaltet und Logausgaben werden ausgegeben
	 * 				false - Debugging ist ausgeschaltet und Logausgaben werden nicht ausgegeben
	 * @return Neues ObjectBroker Objekt
	 */
	public static ObjectBroker init(String serviceHost, int listenPort, boolean debug)  {
		try {
			ObjectBroker broker = new ObjectBroker(serviceHost, listenPort, debug);
			broker.startRandomServer();	
			return broker;
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * Fuegt ein Objekt zum ObjectBroker hinzu
	 * @param servant
	 * @param name
	 */
	public void addObject(Object servant, String name) {
		if (running) {
			try {
				nameService.rebind(servant, name);
			} catch (IOException e) {
				log("Objekt konnte nicht hinzugefuegt werden - Nameservice nicht erreichbar");
				e.printStackTrace();
			}
		}
	}
	
	public MWareObject getObject(String name) {
		try {
			Object o = nameService.resolve(name);
			if (o instanceof MWareObject) {
				return (MWareObject) o;
			} else {
				log("Unbekanntes Objekt bekommen");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * Getter NameService
	 * @return NameService Objekt
	 */
	public NameService getNameService() {
		return nameService;
	}
	
	/**
	 * Getter Warehouse
	 * @return Warehouse fuer Objekte der Middleware
	 */
	public MWareObjectWarehouse getWarehouse() {
		return warehouse;
	}
	
	/**
	 * Methode zum loggen
	 * 
	 * @param message Nachricht, die geloggt wird
	 */
	private void log(String message) {
		if (debug) {
			try {
				Logger.quickLog(logFile, message);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
