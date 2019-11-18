package mware_lib;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;

import mware_lib.logging.Logger;

public class SocketController {
	
	private static final String LOGFILE = "communicationModule.log";
	
	private Socket socket;
	
	private BufferedReader reader;
	
	private OutputStream writer;
	
	private boolean debug;
	
	public SocketController(Socket socket, boolean debug) throws IOException {
		this.socket = socket;
		this.reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		this.writer = socket.getOutputStream();
		this.debug = debug;
	}
	
	public SocketController(String host, int port, boolean debug) throws IOException {
		this(new Socket(host, port), debug);
	}
	
	public void sendMessage(String message) throws IOException {
		writer.write((message).getBytes());
		writer.flush();
		log("Nachricht wurde gesendet:\n    " + message);
	}
	
	public String receiveMessage() throws IOException {
		String received = null;
		while (received == null) {
			received = reader.readLine();
		}
		log("Nachricht erhalten:\n    " + received);
		return received;
	}
	
	public void close() throws IOException {
		socket.close();
		log("Verbindung wurde geschlossen");
	}

	private void log(String message) {
		if (debug) {
			try {
				Logger.quickLog(LOGFILE, message);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
