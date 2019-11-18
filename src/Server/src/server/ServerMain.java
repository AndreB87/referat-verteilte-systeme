package server;

import calculator.Calculator;
import mware_lib.*;

public class ServerMain {

	public static void main(String[] args) {
		ObjectBroker broker = ObjectBroker.init("127.0.0.1", 42000, true);
		broker.addObject(new Calculator(), "Calculator");
	}
}
