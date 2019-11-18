package idl_compiler;

public class IDLParameter {
	
	private DataTypes dataType;
	
	private String name;
	
	public IDLParameter(DataTypes dataType, String name) {
		this.dataType = dataType;
		this.name = name;
	}
	
	public DataTypes getDataType() {
		return dataType;
	}
	
	public String getName() {
		return name;
	}
	
	public String toJavaParameterString() {
		String s = dataType.getJavaKeyWord();
		s += " ";
		s += name;
		return s;
	}

}
