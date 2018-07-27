package hr.fer.zemris.java.custom.scripting.demo;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import hr.fer.zemris.java.custom.scripting.exec.SmartScriptEngine;
import hr.fer.zemris.java.custom.scripting.nodes.DocumentNode;
import hr.fer.zemris.java.custom.scripting.parser.SmartScriptParser;
import hr.fer.zemris.java.custom.scripting.parser.SmartScriptParserException;
import hr.fer.zemris.java.webserver.RequestContext;
import hr.fer.zemris.java.webserver.RequestContext.RCCookie;

/**
 * Program that uses parameters a and b and adds them.
 * Script used for calculation is zbrajanje.smscr.
 * 
 * @author Luka Grgić
 */
public class Zbrajanje {

	/**
	 * Method that first executes when program starts.
	 * 
	 * @param args command line arguments.
	 */
	public static void main(String[] args) {
		String docBody = null;
		Map<String,String> parameters = new HashMap<String, String>();
		Map<String,String> persistentParameters = new HashMap<String, String>();
		List<RCCookie> cookies = new ArrayList<RequestContext.RCCookie>();
		
		try {
			docBody = new String(Files.readAllBytes(Paths.get("examples/zbrajanje.smscr")), StandardCharsets.UTF_8);
		} catch (IOException e1) {
			System.exit(1);
		}

		SmartScriptParser parser = null;

		try {
			parser = new SmartScriptParser(docBody);
		} catch (SmartScriptParserException e) {
			System.out.println("Unable to parse document!");
			System.exit(-1);
		} catch (Exception e) {
			System.out.println("If this line ever executes, you have failed this class!");
			System.exit(-1);
		}

		DocumentNode document = parser.getDocumentNode();
		parameters.put("a", "4");
		parameters.put("b", "2");
		
		new SmartScriptEngine(document, new RequestContext(System.out, parameters, persistentParameters, cookies)).execute();
	}

}
