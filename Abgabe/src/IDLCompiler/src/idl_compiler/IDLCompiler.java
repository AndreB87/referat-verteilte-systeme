package idl_compiler;

import java.io.IOException;
import java.util.List;

public class IDLCompiler {
	
	public static void compile(String filename) {
		try {
			List<IDLClass> idlClasses = new IDLParser(filename).parse();
			for (IDLClass idlClass : idlClasses) {
				String abstractClassName = IDLFileWriter.writeAbstractClass(idlClass);
				IDLFileWriter.writeClass(idlClass, abstractClassName);
			}
		} catch (IDLFileException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public static void main(String[] args) {
		if (args.length > 0) {
			for (int i = 0; i < args.length; i++) {
				IDLCompiler.compile(args[i]);
			}
		}
	}
	
}
