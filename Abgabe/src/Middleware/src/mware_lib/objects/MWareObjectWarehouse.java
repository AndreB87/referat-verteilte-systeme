package mware_lib.objects;

import java.util.HashMap;
import java.util.Map;

public class MWareObjectWarehouse {
	
	/**
	 * Alle lokalen Objekte gespeichert in einer Map, 
	 * um mehrere Objekte ueber den gleichen Socket ansprechen zu koennen
	 */
	private Map<String, MWareObject> objectMap;
	
	/**
	 * Hostname der Connection
	 */
	private String host;
	
	/**
	 * Port der Connection
	 */
	private int port;
	
	public MWareObjectWarehouse(String host, int port) {
		this.objectMap = new HashMap<>();
		this.host = host;
		this.port = port;
	}
	
	public boolean addObject(String name, LocalObject object) {
		if (objectMap.containsKey(name)) {
			return false;
		} else {
			objectMap.put(name, object);
			return true;
		}
	}
	
	public boolean removeObject(String name) {
		if (objectMap.containsKey(name)) {
			objectMap.remove(name);
			return true;
		} else {
			return false;
		}
	}
	
	public void replaceObject(String name, LocalObject object) {
		if (objectMap.containsKey(name)) {
			objectMap.replace(name, object);
		} else {
			objectMap.put(name, object);
		}
	}
	
	public MWareObject getObject(String name) {
		return objectMap.get(name);
	}
	
	public String getHost() {
		return host;
	}
	
	public int getPort() {
		return port;
	}
	
	public void setPort(int port) {
		this.port = port;
	}
}
