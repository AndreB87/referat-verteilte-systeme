package idl_compiler;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class IDLParser {
	
	private static final String END = "};";
	private static final String MODULE = "module";
	private static final String CLASS = "class";
	private static final String PARENTHESIS_OPEN = "(";
	private static final String PARENTHESIS_CLOSE = ")";
	private static final String PARAM_SEPARATOR = ",";
	
	private BufferedReader reader;
	
	private int lineCount;
	
	public IDLParser(String filename) {
		try {
			File file = new File(filename);
			reader = new BufferedReader(new FileReader(file));
			lineCount = 0;
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * Parst eine IDL Datei und wandelt sie in eine IDLClass
	 * 
	 * @return Datei als IDLClass
	 * @throws IDLFileException
	 * @throws IOException
	 */
	public List<IDLClass> parse() throws IDLFileException, IOException {
		String line; 
		String moduleName = "";
		String className = "";
		List<IDLClass> classes = new ArrayList<>();
		List<IDLMethod> methods = new ArrayList<>();
		int endsFound = 0;
		while (endsFound < 2) {
			while (!(line = reader.readLine().trim()).equals(END)) { 
				lineCount++;
				String[] words = line.split(" ");
				switch (words[0]) {
				case MODULE:
					moduleName = words[1];
					break;
					
				case CLASS:
					endsFound = 0;
					className = words[1];
					methods = new ArrayList<>();
					break;
					
				case "":
					break;
					
				default:
					if (line.endsWith(";")) {
						IDLMethod method = parseMethod(line);
						methods.add(method);
					} else {
						throw new IDLFileException(String.format("Ungueltiges Format der IDL-Datei - Zeile %d", lineCount));
					}
					break;
				}
			}
			
			if (!className.equals("")) {
				IDLClass idlClass = new IDLClass(moduleName, className);
				
				for (IDLMethod method : methods) {
					idlClass.addMethod(method);
				}
				classes.add(idlClass);
			}
			className = "";
			endsFound++;
		}
		
		return classes;
	}
	
	/**
	 * Parst eine IDL-Methode und wandelt sie in eine IDLMethod
	 * 
	 * @param line Zeile der IDL-Datei
	 * @return Methode der IDL-Datei
	 */
	public IDLMethod parseMethod(String line) {
		String[] words = line.split(" ");
		DataTypes returnType = DataTypes.getFromName(words[0]);
		String methodName = words[1].substring(0, words[1].indexOf(PARENTHESIS_OPEN));
		IDLMethod method = new IDLMethod(returnType, methodName);
		String parenthesisValue = line.substring(line.indexOf(PARENTHESIS_OPEN) + 1, line.indexOf(PARENTHESIS_CLOSE));
		for (String param : parenthesisValue.split(PARAM_SEPARATOR)) {
			String[] paramTupel = param.trim().split(" ");
			DataTypes type = DataTypes.getFromName(paramTupel[0].trim());
			String value = paramTupel[1].trim();
			method.addParameter(new IDLParameter(type, value));
		}
		return method;
	}
}
