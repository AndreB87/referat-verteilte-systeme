package idl_compiler;

import java.util.ArrayList;
import java.util.List;

public class IDLMethod {
	
	private DataTypes returnType;
	
	private String name;
	
	private List<IDLParameter> parameters;
	
	public IDLMethod(DataTypes returnType, String name) {
		this.returnType = returnType;
		this.name = name;
	}

	public DataTypes getReturnType() {
		return returnType;
	}
	
	public String getName() {
		return name;
	}
	
	public void addParameter(IDLParameter parameter) {
		if (parameters == null) {
			parameters = new ArrayList<>();
		}
		parameters.add(parameter);
	}
	
	public List<IDLParameter> getParameters() {
		return parameters;
	}
	
	public boolean hasParameters() {
		return parameters == null;
	}
	
	public String toJavaMethodString( ) {
		String s = returnType.getJavaKeyWord();
		s += " ";
		s += name;
		s += "(";
		for (IDLParameter p : parameters) {
			s += p.toJavaParameterString();
			s += ", ";
		}
		s = s.substring(0, s.length() - 2);
		s += ")";
		return s;
	}
}
