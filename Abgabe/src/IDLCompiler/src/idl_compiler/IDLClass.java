package idl_compiler;

import java.util.ArrayList;
import java.util.List;

public class IDLClass {
	
	private String moduleName;
	
	private String className;
	
	private List<IDLMethod> methods;
	
	public IDLClass(String moduleName, String className) {
		this.moduleName = moduleName;
		this.className = className;
		this.methods = new ArrayList<>();
	}
	
	public String getModuleName() {
		return moduleName;
	}
	
	public String getClassName() {
		return className;
	}
	
	public void addMethod(IDLMethod method) {
		methods.add(method);
	}
	
	public List<IDLMethod> getMethods() {
		return methods;
	}
}
