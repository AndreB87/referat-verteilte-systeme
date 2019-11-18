package idl_compiler;

public enum DataTypes {
	
	INT, DOUBLE, STRING;
	
	public String getJavaKeyWord() {
		switch (this) {
			case INT:
				return "int";
			
			case DOUBLE:
				return "double";
			
			case STRING:
				return "String";
			default:
				return "";
		}
	}
	
	public String getIDLKeyWord() {
		switch (this) {
			case INT:
				return "int";
			
			case DOUBLE:
				return "double";
			
			case STRING:
				return "string";
				
			default:
				return "";
		}
	}
	
	public static DataTypes getFromName(String name) {
		switch (name) {
			case "int":
				return INT;
		
			case "double":
				return DOUBLE;
		
			case "string":
				return STRING;
			
			case "String":
				return STRING;
			
			default:
				return null;
		}
	}
}
