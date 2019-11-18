package mware_lib.logging;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class Logger {

	/**
	 * Datei, in die geloggt wird
	 */
	private File file;

	/**
	 * Objekt zum Schreiben in die Datei
	 */
	private FileWriter writer;

	public Logger(String filePath) throws IOException {
		this.file = new File(filePath);
		if (!file.exists()) {
			file.createNewFile();
		}
		this.writer = new FileWriter(file);
		this.log("########## - Start: " + LocalDate.now() + " - ##########");
	}
	
	/**
	 * Schnittstelle nach aussen zum loggen
	 * 
	 * @param message Nachricht, die geloggt werden soll
	 * @param debug true - Nachricht wird ins Logfile eingetragen und auf der Konsole ausgegeben
	 * 				false - Nachricht wird nur ins Logfile eingetragen
	 * 
	 * @throws IOException
	 */
	public void logging(String message, boolean debug) throws IOException {
		if (debug) {
			this.logWithConsole(message);
		} else {
			this.log(message);
		}
	}

	/**
	 * Schreiben der Nachricht in die Datei
	 * 
	 * @param message Nachricht, die in die Datei eingetragen wird
	 * 
	 * @throws IOException
	 */
	private void log(String message) throws IOException {
		this.writer.write(message + " | " + LocalDateTime.now().toString() + "\n");
		this.writer.flush();
	}

	/**
	 * Loggen der Nachricht plus ausgeben auf der Konsole
	 * 
	 * @param message Nachricht, die in die Datei und auf die Konsole geschrieben wird
	 * @throws IOException
	 */
	private void logWithConsole(String message) throws IOException {
		this.log(message);
		System.out.println(message);
	}

	public void close() throws IOException {
		this.log("########## - End: " + LocalDate.now() + " - ##########\n");
		this.writer.close();
	}
	
	public static void quickLog(String filename, String message) throws IOException {
		String logMessage = message + " | " + LocalDateTime.now().toString();
		File file = new File(filename);
		if (!file.exists()) {
			file.createNewFile();
		}
		FileWriter writer = new FileWriter(filename, true);
		writer.append(logMessage + "\n");
		System.out.println(logMessage);
		writer.close();
	}
}
