package idl_compiler;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class IDLFileWriter {
	
	public static String writeAbstractClass(IDLClass idlClass) {
		String className = "_" + idlClass.getClassName() + "ImplBase";
		
		String fileValue = "package " + idlClass.getModuleName() + ";" + "\n";
		fileValue += "\n";
		fileValue += "import mware_lib.objects.MWareObject;\n";
		fileValue += "\n";
		fileValue += "public abstract class " + className + "{" + "\n";
		fileValue += "\n";
		for (IDLMethod method : idlClass.getMethods()) {
			fileValue += "    public abstract " + method.toJavaMethodString() + ";" + "\n";
			fileValue += "\n";
		}
		
		fileValue += "    public static " + className + " narrowCast(Object rawObjectRef) {\n";
		fileValue += "        return new " + idlClass.getClassName() + "((MWareObject)rawObjectRef);\n";
		fileValue += "    }\n";
		
		fileValue += "}\n";
		
		String fileName = idlClass.getModuleName() + "/" + className + ".java";
		
		File file = new File(fileName);
		try {
			file.getParentFile().mkdirs();
			file.createNewFile();
			FileWriter fw = new FileWriter(file);
			fw.write(fileValue);
			fw.flush();
			fw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return className;
	}
	
	public static void writeClass(IDLClass idlClass, String abstractClassName) {
		String className = idlClass.getClassName();
		String fileValue = "package " + idlClass.getModuleName() + ";" + "\n";
		fileValue += "\n";
		fileValue += "import mware_lib.objects.MWareObject;\n";
		fileValue += "\n";
		fileValue += "/**\n";
		fileValue += " * Automatisch vom IDLCompiler erzeugte Datei\n";
		fileValue += " */\n";
		fileValue += "public class " + className + " extends " + abstractClassName + " {" + "\n";
		fileValue += "\n";
		fileValue += "    private MWareObject rawObjectRef;\n";
		fileValue += "\n";
		fileValue += "    public " + className + "(MWareObject rawObjectRef){\n";
		fileValue += "        this.rawObjectRef = rawObjectRef;\n";
		fileValue += "    }\n";
		fileValue += "\n";
		for (IDLMethod method : idlClass.getMethods()) {
			fileValue += "    @Override\n";
			fileValue += "    public " + method.toJavaMethodString() + " {" + "\n";
			fileValue += "        String message = rawObjectRef.getName() + \"!" + method.getName() + "!\"";
			String parameters = "";
			for (IDLParameter param : method.getParameters()) {
				parameters += " + valueToString(" + param.getName() + ") + \":" + param.getDataType().getJavaKeyWord() + ",\""; 
			}
			if (parameters.length() > 1) {
				parameters = parameters.substring(0, parameters.length() - 2) + "\\n\";\n";
			}
			fileValue += parameters;
			fileValue += "        String result = \"\";\n";
			fileValue += "        try {\n";
			fileValue += "            result = rawObjectRef.callMethod(message);\n";
			fileValue += "        } catch (Exception e) {\n";
			fileValue += "            e.printStackTrace();\n";
			fileValue += "        }\n";
			fileValue += "        if (!result.isEmpty()) {\n";
			fileValue += "            String[] parts = result.split(\":\");\n";
			fileValue += "            switch(parts[0]) {\n";
			fileValue += "            case \"result\":\n";
			switch(method.getReturnType()) {
			case STRING:
				fileValue += "                return parts[1];\n";
				break;
			case INT:
				fileValue += "                return Integer.parseInt(parts[1]);\n";
				break;
			case DOUBLE:
				fileValue += "                return Double.parseDouble(parts[1]);\n";
				break;
			}
			fileValue += "            case \"exception\":\n";
			fileValue += "                throw new RuntimeException(parts[2]);\n";
			fileValue += "            }\n";
			fileValue += "        }\n";
			fileValue += "        throw new RuntimeException(\"Unknown Error\");\n";
			fileValue += "    }\n";
			fileValue += "\n";
		}
		
		fileValue += "    public String valueToString(Object value) {\n";
		fileValue += "        return String.valueOf(value);\n";
		fileValue += "    }\n";
		fileValue += "}\n";
		
		String fileName = idlClass.getModuleName() + "/" + className + ".java";
		
		File file = new File(fileName);
		try {
			file.getParentFile().mkdirs();
			file.createNewFile();
			FileWriter fw = new FileWriter(file);
			fw.write(fileValue);
			fw.flush();
			fw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}
}
