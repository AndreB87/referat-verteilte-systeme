package calculator;

public class Calculator {
	
	public Calculator() {}
	
	public double add(double a, double b) {
		return a + b;
	}
	
	public String getStr(double a) {
		return String.format("%d", a);
	}
}
