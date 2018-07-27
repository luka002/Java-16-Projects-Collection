package hr.fer.zemris.java.webserver;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PushbackInputStream;
import java.lang.reflect.InvocationTargetException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

import hr.fer.zemris.java.custom.scripting.exec.SmartScriptEngine;
import hr.fer.zemris.java.custom.scripting.nodes.DocumentNode;
import hr.fer.zemris.java.custom.scripting.parser.SmartScriptParser;
import hr.fer.zemris.java.custom.scripting.parser.SmartScriptParserException;
import hr.fer.zemris.java.webserver.RequestContext.RCCookie;

/**
 * Class that represents implementation of a server.
 * 
 * @author Luka Grgić
 */
public class SmartHttpServer {
	
	/** Address on which server listens */
	private String address;
	/** Domain name of web server */
	private String domainName;
	/** Port on which server listens */
	private int port;
	/** How many threads server will use */
	private int workerThreads;
	/** Duration of user sessions in seconds */
	private int sessionTimeout;
	/** Map of mime types */
	private Map<String, String> mimeTypes = new HashMap<String, String>();
	/** Server thread */
	private ServerThread serverThread;
	/** Thread pool */
	private ExecutorService threadPool;
	/** Path to document root */
	private Path documentRoot;
	/** Map of IWebWorkers */
	private Map<String, IWebWorker> workersMap = new HashMap<String, IWebWorker>();
	
	/** Map of sessions */
	private Map<String, SessionMapEntry> sessions = new HashMap<String, SmartHttpServer.SessionMapEntry>();
	/** Random generator */
	private Random sessionRandom = new Random();
	/** Flag that signals if server should shut down */
	private volatile boolean done = false;
	
	/**
	 * Constructor.
	 * 
	 * @param configFileName file with configuration setup.
	 * @throws IOException
	 * @throws ClassNotFoundException
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 * @throws InvocationTargetException
	 * @throws NoSuchMethodException
	 * @throws SecurityException
	 */
	public SmartHttpServer(String configFileName) throws IOException, ClassNotFoundException,
													InstantiationException, IllegalAccessException, 
													IllegalArgumentException, InvocationTargetException, 
													NoSuchMethodException, SecurityException {
		Properties serverProperties = new Properties();
		serverProperties.load(Files.newBufferedReader(Paths.get(configFileName), StandardCharsets.UTF_8));
		
		address = serverProperties.getProperty("server.address");
		domainName = serverProperties.getProperty("server.domainName");
		port = Integer.parseInt(serverProperties.getProperty("server.port"));
		workerThreads = Integer.parseInt(serverProperties.getProperty("server.workerThreads"));
		sessionTimeout = Integer.parseInt(serverProperties.getProperty("session.timeout"));
		
		Properties mimeProperties = new Properties();
		mimeProperties.load(Files.newBufferedReader(
				Paths.get(serverProperties.getProperty("server.mimeConfig")), 
				StandardCharsets.UTF_8)
		);
		for (String name: mimeProperties.stringPropertyNames()) {
		    mimeTypes.put(name, mimeProperties.getProperty(name));
		}
		
		serverThread = new ServerThread();
		documentRoot = Paths.get(serverProperties.getProperty("server.documentRoot"));
		
		Properties workerProperties = new Properties();
		workerProperties.load(Files.newBufferedReader(
				Paths.get(serverProperties.getProperty("server.workers")), 
				StandardCharsets.UTF_8)
		);
		for (String name: workerProperties.stringPropertyNames()) {
			Class<?> referenceToClass = this.getClass()
											.getClassLoader()
											.loadClass(workerProperties.getProperty(name));
			
			Object newObject = referenceToClass.getDeclaredConstructor().newInstance();
			IWebWorker iww = (IWebWorker)newObject;
			workersMap.put(name, iww);
		}

	}

	/**
	 * Initializes threadPool, starts server and starts thread
	 * that checks for inactive sessions and removes them.
	 */
	protected synchronized void start() {	
		threadPool = Executors.newFixedThreadPool(workerThreads, new ThreadFactory() {
			@Override
			public Thread newThread(Runnable r) {
				Thread worker = new Thread(r);
				worker.setDaemon(true);
				return worker;
			}
		});
		
		if (!serverThread.isAlive()) {
			serverThread.start();
		}
		
		Thread cleaner = new Thread(() -> {
			while (true) {
				try {
					Thread.sleep(300000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				
				synchronized (address) {
					for (String key : sessions.keySet()) {
						if (sessions.get(key).validUntil < Instant.now().getEpochSecond()) {
							sessions.remove(key);
						}
					}
				}
			}	
		});
		cleaner.setDaemon(true);
		cleaner.start();
	}
	
	/**
	 * Stops server.
	 */
	protected synchronized void stop() {
		done = true;
		threadPool.shutdown();
	}
	
	/**
	 * Entry for unique session id.
	 * 
	 * @author Luka Grgić
	 */
	private static class SessionMapEntry {
		/** Session id */
		String sid;
		/** Host name */
		String host;
		/** Entry valid until */
		long validUntil;
		/** Parameters map */
		Map<String,String> map;
		
		/**
		 * Constructor.
		 * 
		 * @param sid session id
		 * @param host host name
		 * @param validUntil valid until
		 */
		public SessionMapEntry(String sid, String host, long validUntil) {
			this.sid = sid;
			this.validUntil = validUntil;
			this.host = host;
			this.map = new ConcurrentHashMap();
		}
		
	}
	
	/**
	 * Class that represents server.
	 * 
	 * @author Luka Grgić
	 */
	protected class ServerThread extends Thread {
		
		@Override
		public void run() {
			try (ServerSocket serverSocket = new ServerSocket()){
				
				serverSocket.bind(new InetSocketAddress((InetAddress)null, port));
				
				while (!done) {
					Socket client = serverSocket.accept();
					ClientWorker cWorker = new ClientWorker(client);
					threadPool.submit(cWorker);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * Class that represents client.
	 * 
	 * @author Luka Grgić
	 */
	private class ClientWorker implements Runnable, IDispatcher {
		
		/** Clients socket */
		private Socket csocket;
		/** Client input stream */
		private PushbackInputStream istream;
		/** Client output stream */
		private OutputStream ostream;
		/** HTTP version*/
		private String version;
		/** method */
		private String method;
		/** Client address */
		private String host;
		/** Parameters */
		private Map<String,String> params = new HashMap<String, String>();
		/** Temporary parameters */
		private Map<String,String> tempParams = new HashMap<String, String>();
		/** Permanent parameters */
		private Map<String,String> permPrams = new HashMap<String, String>();
		/** Cookies */
		private List<RCCookie> outputCookies = new ArrayList<RequestContext.RCCookie>();
		/** Session id*/
		private String SID;
		/** Context */
		private RequestContext context = null;
		
		/**
		 * Constructor.
		 * 
		 * @param csocket client socket
		 */
		public ClientWorker(Socket csocket) {
			super();
			this.csocket = csocket;
			this.SID = "";
		}
		
		@Override
		public void run() {
			try {
				istream = new PushbackInputStream(csocket.getInputStream());
				ostream = csocket.getOutputStream();
				
				byte[] request = readRequest(istream);
				if(request==null) {
					sendError(ostream, 400, "Bad request");
					return;
				}
				
				String requestStr = new String(request, StandardCharsets.US_ASCII);
				
				List<String> headers = extractHeaders(requestStr);
				String[] firstLine = headers.isEmpty() ? null : headers.get(0).split(" ");

				if (firstLine == null || firstLine.length != 3) {
					sendError(ostream, 400, "Bad request");
					return;
				}
				
				method = firstLine[0].toUpperCase();
				if(!method.equals("GET")) {
					sendError(ostream, 405, "Method Not Allowed");
					return;
				}
				
				version = firstLine[2].toUpperCase();
				if(!version.equals("HTTP/1.1") && !version.equals("HTTP/1.0")) {
					sendError(ostream, 505, "HTTP Version Not Supported");
					return;
				}
				
				host = getHostValue(headers);
				params.put("host", host);
				checkSession(headers);
				
				String requestedPath = firstLine[1];
				String path = null;
				String paramString = null;
				
				if (requestedPath.contains("?")) {
					path = requestedPath.split("[?]")[0];
					paramString = requestedPath.split("[?]")[1];
				} else {
					path = requestedPath;
				}
				
				if (paramString != null) {
					parseParameters(paramString);
				}
				
				if (path.startsWith("/ext/")) {
					Class<?> referenceToClass = this.getClass()
							.getClassLoader()
							.loadClass("hr.fer.zemris.java.webserver.workers." + path.substring(5));

					Object newObject = referenceToClass.getDeclaredConstructor().newInstance();
					IWebWorker iww = (IWebWorker)newObject;
					iww.processRequest(new RequestContext(ostream, params, permPrams, outputCookies, tempParams, this));
				}
				else if (workersMap.containsKey(path.toString())) {
					IWebWorker iWebWorker = workersMap.get(path.toString());
					context = new RequestContext(ostream, params, permPrams, outputCookies, tempParams, this);
					iWebWorker.processRequest(context);
				} 
				else if (path.endsWith(".smscr")) {
					internalDispatchRequest(path, true);
				} 
				else {
					Path requestedFile = documentRoot.resolve(path.substring(1)).toAbsolutePath();
					
					if (!requestedFile.startsWith(documentRoot) || !Files.isReadable(requestedFile) || !Files.isRegularFile(requestedFile)) {
						sendError(ostream, 404, "File not found");
						return;
					}
					
					String mime = getMime(requestedFile.getFileName().toString());
					
					context = new RequestContext(ostream, params, permPrams, outputCookies);
					context.setMimeType(mime);
					context.setStatusCode(200);
					
					try (InputStream is = Files.newInputStream(requestedFile)) {
						byte[] buf = new byte[1024];
						
						while (true) {
							int r = is.read(buf);
							if (r<1) break;
							context.write(Arrays.copyOf(buf, r));
						}
					}
				}	
				
				ostream.flush();
					
				istream.close();
				ostream.close();
				csocket.close();
				
			} catch (IOException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}
		
		/**
		 * Checks if it there is already started session 
		 * with a client.
		 * 
		 * @param headers headers
		 */
		private void checkSession(List<String> headers) {
			synchronized (address) {
				String sidCandidate = null;
				
		A:		for (String header : headers) {
					if (header.toUpperCase().startsWith("COOKIE:")) {
						header = header.substring(7).trim();
						
						if (!header.isEmpty() && header.contains(";")) {
							String[] cookieValues = header.split(";");
							
							for (int i = 0; i < cookieValues.length; i++) {
								if (cookieValues[i].startsWith("sid")) {
									sidCandidate = cookieValues[i].trim().substring(5, cookieValues[i].length()-1);
									break A;
								}
							}
						} else if (header.startsWith("sid")) {
							sidCandidate = header.substring(5, header.length()-1);
							break;
						}
					}
				}
				
				if (sidCandidate != null && sessions.containsKey(sidCandidate)) {
					SessionMapEntry entry = sessions.get(sidCandidate);
					if (entry.host.equals(host) && entry.validUntil > Instant.now().getEpochSecond()) {
						entry.validUntil = Instant.now().getEpochSecond() + sessionTimeout;
						permPrams = entry.map;
						return;
					}
				}
				
				for (int i = 0; i < 20; i++) {
					SID += String.valueOf((char)(sessionRandom.nextInt(26) + 65));
				}
				
				SessionMapEntry newEntry = new SessionMapEntry(SID, host, Instant.now().getEpochSecond() + sessionTimeout);
				sessions.put(SID, newEntry);
				outputCookies.add(new RCCookie("sid", SID, null, host, "/"));	
				permPrams = newEntry.map;	
			}
		}

		/**
		 * Get mime type
		 * 
		 * @param name mime
		 * @return mime type
		 */
		private String getMime(String name) {
			name = name.toLowerCase();
					
			if (name.endsWith(".html")) return mimeTypes.get("html");
			if (name.endsWith(".htm")) return mimeTypes.get("htm");
			if (name.endsWith(".txt")) return mimeTypes.get("txt");
			if (name.endsWith(".gif")) return mimeTypes.get("gif");
			if (name.endsWith(".png")) return mimeTypes.get("png");
			if (name.endsWith(".jpg")) return mimeTypes.get("jpg");
			
			return "application/octet-stream";
		}

		/**
		 * Parse given parameters.
		 * 
		 * @param paramString parameters
		 */
		private void parseParameters(String paramString) {
			String[] parameters = null;
			
			if (paramString.contains("&")) {
				parameters = paramString.split("&");
			} else {
				parameters = new String[] {paramString};
			}
			
			for (int i = 0; i < parameters.length; i++) {
				if (parameters[i].endsWith("=")) {
					params.put(parameters[i].substring(0, parameters[i].length()-1), "");
				} else {
					params.put(parameters[i].split("=")[0], parameters[i].split("=")[1]);
				}
			}
		}

		/**
		 * Get value under Host: key from header.
		 * 
		 * @param headers header lines
		 * @return host value
		 */
		private String getHostValue(List<String> headers) {
			for (String header : headers) {
				if (header.startsWith("Host: ")) {
					String value = header.substring(5).trim();
					
					if (value.contains(":")) {
						value = value.split(":")[0];
					}
					
					return value;
				}
			}
			
			return domainName;
		}

		/**
		 * Extract headers from data
		 * 
		 * @param requestHeader data
		 * @return headers
		 */
		private List<String> extractHeaders(String requestHeader) {
			List<String> headers = new ArrayList<String>();
			String currentLine = null;
			
			for(String s : requestHeader.split("\n")) {
				if(s.isEmpty()) break;
				char c = s.charAt(0);
				
				if(c==9 || c==32) {
					currentLine += s;
				} else {
					if(currentLine != null) {
						headers.add(currentLine);
					}
					currentLine = s;
				}
			}
			
			if(!currentLine.isEmpty()) {
				headers.add(currentLine);
			}
			
			return headers;
		}

		/**
		 * Read data until two new lines.
		 * 
		 * @param is input stream
		 * @return  read request
		 * @throws IOException
		 */
		private byte[] readRequest(PushbackInputStream is) throws IOException {
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			
			int state = 0;
	l:		while(true) {
				int b = is.read();
				if(b==-1) return null;
				if(b!=13) {
					bos.write(b);
				}
				
				switch(state) {
					case 0: 
						if(b==13) { state=1; } else if(b==10) state=4;
						break;
					case 1: 
						if(b==10) { state=2; } else state=0;
						break;
					case 2: 
						if(b==13) { state=3; } else state=0;
						break;
					case 3: 
						if(b==10) { break l; } else state=0;
						break;
					case 4: 
						if(b==10) { break l; } else state=0;
						break;
				}
			}
			
			return bos.toByteArray();
		}
		
		/**
		 * Sent error header to client is error.
		 * 
		 * @param cos output stream
		 * @param statusCode status code
		 * @param statusText status text
		 * @throws IOException if error
		 */
		private void sendError(OutputStream cos, 
				int statusCode, String statusText) throws IOException {

				cos.write(
					("HTTP/1.1 "+statusCode+" "+statusText+"\r\n"+
					"Server: simple java server\r\n"+
					"Content-Type: text/plain;charset=UTF-8\r\n"+
					"Content-Length: 0\r\n"+
					"Connection: close\r\n"+
					"\r\n").getBytes(StandardCharsets.US_ASCII)
				);
				cos.flush();

			}

		@Override
		public void dispatchRequest(String urlPath) throws Exception {
			internalDispatchRequest(urlPath, false);
		}
		
		/**
		 * Method that uses SmartScriptEngine to process document
		 * and sends response to client.
		 * 
		 * @param urlPath path
		 * @param directCall is it direct call
		 * @throws Exception exception
		 */
		public void internalDispatchRequest(String urlPath, boolean directCall) throws Exception {
			if ((urlPath.equals("/private") || urlPath.startsWith("/private/")) && directCall) {
				sendError(ostream, 400, "Bad request");
				return;
			}
			
			Path requestedFile = documentRoot.resolve(urlPath.substring(1)).toAbsolutePath();
			
			if (!requestedFile.startsWith(documentRoot) || !Files.isReadable(requestedFile) || !Files.isRegularFile(requestedFile)) {
				sendError(ostream, 404, "File not found");
				return;
			}
			
			String docBody = null;
			
			try {
				docBody = new String(Files.readAllBytes(requestedFile), StandardCharsets.UTF_8);
			} catch (IOException e1) {
				e1.printStackTrace();
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
			if (context == null) {
				context = new RequestContext(ostream, params, permPrams, outputCookies, tempParams, this);
			}
			
			new SmartScriptEngine(document, context).execute();
		}
		
	}
	
	/**
	 * Method that first executes when program starts.
	 * 
	 * @param args command line arguments
	 */
	public static void main(String[] args) {
		String config = "config/server.properties";
		
		try {
			new SmartHttpServer(config).start();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		}
	}
}
