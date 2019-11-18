package mware_lib.objects;

import java.io.IOException;
import java.net.UnknownHostException;

public interface MWareObject {
	
	public String getName();
	
	public String callMethod(String message) throws UnknownHostException, IOException;

}
