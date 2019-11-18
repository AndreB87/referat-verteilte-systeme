package mware_lib;

import java.io.IOException;

import mware_lib.logging.Logger;
import mware_lib.objects.LocalObject;
import mware_lib.objects.MWareObject;
import mware_lib.objects.MWareObjectWarehouse;
import mware_lib.objects.RemoteObject;

public class NameServiceImpl extends NameService {

	private final static String LOGFILE = "NameService.log";
	
	private MWareObjectWarehouse warehouse;
	
	public NameServiceImpl(String host, int port, 
		MWareObjectWarehouse warehouse, boolean debug) {
		super(host, port, debug);
		this.warehouse = warehouse;
	}

	@Override
	public void rebind(Object servant, String name) throws IOException {
		LocalObject object;
		if (servant instanceof LocalObject) {
			object = (LocalObject)servant;
		} else {
			object = new LocalObject(servant, name, debug);
		}
		warehouse.replaceObject(name, object);
		String message = String.format("rebind!%s:%s:%d", name, warehouse.getHost(), warehouse.getPort());
		nameServiceSocket.sendMessage(message);
	}

	@Override
	public Object resolve(String name) throws IOException {
		log(String.format("resolve: Objekt %s angefragt", name));
		MWareObject o = warehouse.getObject(name);
		if (o != null) {
			return o;
		}
		nameServiceSocket.sendMessage("resolve!" + name);
		String result = nameServiceSocket.receiveMessage();
		if (result.startsWith("ok")) {
			log(String.format("Objekt gefunden: %s", result));
			String[] resArr = result.split(":");
			String resName = resArr[1];
			if (resName.equals(name)) {
				log("Objekt wird zurueckgegeben");
			} else {
				log("Objekt hat falschen Namen");
			}
			String host = resArr[2];
			int port = Integer.parseInt(resArr[3]);
			return new RemoteObject(name, host, port, debug);
		} else {
			log("Objekt wurde nicht gefunden");
			return null;
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
