package mware_lib.objects;

import java.io.IOException;
import java.lang.reflect.Method;

import mware_lib.logging.Logger;

public class LocalObject implements MWareObject {
	
	/**
	 * Name der LogFile
	 */
	private final String logfile;
	
	/**
	 * Objekt, auf dem die Methoden ausgefuehrt werden
	 */
	private Object object;
	
	/**
	 * Name des Objekts
	 */
	private String name;
	
	/**
	 * Debug Flag
	 */
	private boolean debug;
	
	public LocalObject(Object object, String name, boolean debug) {
		this.logfile = String.format("RemoteObject-%s.log", name);
		this.object = object;
		this.name = name;
		this.debug = debug;
	}
	
	public synchronized String callMethod(String message) {
		String result = null;
		
		String[] messageParts = message.split("!");
		
		String method = messageParts[1];
		String[] arguments = messageParts[2].split(",");
		
		Object[] values = new Object[arguments.length];
		Class<?>[] types = new Class<?>[arguments.length];
		
		String logMessage = null;
		if (debug) {
			logMessage = method + "(";
		}
		
		for (int i = 0; i < arguments.length; i++) {
			String[] argument = arguments[i].split(":");
			switch (argument[1]) {
			case "int":
				types[i] = int.class;
				values[i] = Integer.parseInt(argument[0]);
				break;
			case "double":
				types[i] = double.class;
				values[i] = Double.parseDouble(argument[0]);
				break;
			case "String":
				types[i] = String.class;
				values[i] = argument[0];
				break;
			default:
				types[i] = null;
				values[i] = null;
			}
			
			if (debug) {
				logMessage += argument[1] + " " + argument[0];
				if (i < arguments.length - 1) {
					logMessage += ", ";
				} else {
					logMessage += ");";
				}
			}
		}
		log("Rufe Methode auf: " + logMessage);
		
		try {
			Method toCall = object.getClass().getMethod(method, types);
			result = "result:" + String.valueOf(toCall.invoke(object, values)) ;
		} catch (Exception e) {
			//e.printStackTrace();
			result = "exception:" + e.getClass().toString() + ":" + e.getMessage();
		}
		
		return result;
	}
	
	public String getName() {
		return name;
	}
	
	private void log(String message) {
		if (debug) {
			try {
				Logger.quickLog(logfile, message);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
