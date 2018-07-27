package hr.fer.zemris.java.custom.scripting.demo;

import hr.fer.zemris.java.webserver.RequestContext;
import hr.fer.zemris.java.webserver.RequestContext.RCCookie;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Program that creates three text files based on construced
 * RequestContext object.
 * 
 * @author Luka Grgić
 */
public class DemoRequestContext {
	
	/**
	 * Program starts executing here.
	 * 
	 * @param args command line arguments.
	 * @throws IOException if error with I/O
	 */
	public static void main(String[] args) throws IOException {
		
		demo1("primjer1.txt", "ISO-8859-2");
		demo1("primjer2.txt", "UTF-8");
		demo2("primjer3.txt", "UTF-8");
		
	}

	/**
	 * Creates file.
	 * 
	 * @param filePath path
	 * @param encoding encoding
	 * @throws IOException if error with I/O
	 */
	private static void demo1(String filePath, String encoding) throws IOException {
		OutputStream os = Files.newOutputStream(Paths.get(filePath));
		
		RequestContext rc = new RequestContext(os, new HashMap<String, String>(), 
								new HashMap<String, String>(),
								new ArrayList<RequestContext.RCCookie>());
		rc.setEncoding(encoding);
		rc.setMimeType("text/plain");
		rc.setStatusCode(205);
		rc.setStatusText("Idemo dalje");

		// Only at this point will header be created and written...
		rc.write("Čevapčići i Šiščevapčići.");
		
		os.close();
	}

	/**
	 * Creates file.
	 * 
	 * @param filePath path
	 * @param encoding encoding
	 * @throws IOException if error with I/O
	 */
	private static void demo2(String filePath, String encoding) throws IOException {
		OutputStream os = Files.newOutputStream(Paths.get(filePath));
		
		RequestContext rc = new RequestContext(os, new HashMap<String, String>(), 
								new HashMap<String, String>(),
								new ArrayList<RequestContext.RCCookie>());
		rc.setEncoding(encoding);
		rc.setMimeType("text/plain");
		rc.setStatusCode(205);
		rc.setStatusText("Idemo dalje");
		rc.addRCCookie(new RCCookie("korisnik", "perica", 3600, "127.0.0.1", "/"));
		rc.addRCCookie(new RCCookie("zgrada", "B4", null, null, "/"));
		
		// Only at this point will header be created and written...
		rc.write("Čevapčići i Šiščevapčići.");
		
		os.close();
	}
	
}
