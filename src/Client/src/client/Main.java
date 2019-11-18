package client;

import math_ops._CalculatorImplBase;
import mware_lib.*;

public class Main {

	public static void main(String[] args) {
		
		ObjectBroker broker = ObjectBroker.init("127.0.0.1", 42000, true);
		Object o = broker.getObject("Calculator");
		
		_CalculatorImplBase calc = _CalculatorImplBase.narrowCast(o);
		
		System.out.println(calc.add(20.6, 21.4));
		System.out.println(calc.add(20.6, 21.4));
		System.out.println(calc.add(20.6, 21.4));
	}
}
