package hr.fer.zemris.java.webserver;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 * Class containing all required data for responding
 * to client request.
 * 
 * @author Luka Grgić
 */
public class RequestContext {

	/**
	 * Class containing cookie data.
	 * 
	 * @author Luka Grgić
	 */
	public static class RCCookie {
		/** Cookie name */
		final String name;
		/** Cookie value */
		final String value;
		/** When cookie expires */
		final Integer maxAge;
		/** Domain on which cookie will be sent */
		final String domain;
		/** Path */
		final String path;
		
		/**
		 * Constructor.
		 * @param name Cookie name
		 * @param value Cookie value
		 * @param maXAge When cookie expires
		 * @param domain Domain on which cookie will be sent
		 * @param path Path 
		 */
		public RCCookie(String name, String value, Integer maxAge, String domain, String path) {
			Objects.requireNonNull(name, "Name can not be null.");
			Objects.requireNonNull(value, "Value can not be null.");
			
			this.name = name;
			this.value = value;
			this.maxAge = maxAge;
			this.domain = domain;
			this.path = path;
		}
	}
	
	/** Output to client */
	private OutputStream outputStream;
	/** Charset used for data encoding */
	private Charset charset;
	
	/** Encoding page */
	private String encoding = "UTF-8";
	/** Status code */
	private int statusCode = 200;
	/** Status text */
	private String statusText = "OK";
	/** Mime type */
	private String mimeType = "text/html";
	
	/** Parameters provided trough get method */
	private Map<String,String> parameters;
	/** Temporary parameters */
	private Map<String,String> temporaryParameters;
	/** Persistent parameters that are stored trough session */
	private Map<String,String> persistentParameters;
	/** Cookies */
	private List<RCCookie> outputCookies;
	/** Is header generated*/
	private boolean headerGenerated = false;
	/** Dispatcher that creates data that will be sent to client */
	private IDispatcher dispatcher;
	 
	/** Default HTTP version */
	private final String HTTP_VERSION = "HTTP/1.1";
	
	/**
	 * Constructor.
	 * @param outputStream Output to client
	 * @param parameters Parameters provided trough get method
	 * @param persistentParameters Persistent parameters that are stored trough session
	 * @param outputCookies Cookies
	 */
	public RequestContext(OutputStream outputStream, Map<String,String> parameters,
						Map<String,String> persistentParameters, List<RCCookie> outputCookies) {
		
			this(outputStream, parameters, persistentParameters, outputCookies, new HashMap<>(), null);
	}
	
	/**
	 * Constructor.
	 * @param outputStream Output to client
	 * @param parameters Parameters provided trough get method
	 * @param persistentParameters Persistent parameters that are stored trough session
	 * @param outputCookies Cookies
	 * @param temporaryParameters Temporary parameters
	 * @param dispatcher Dispatcher that creates data that will be sent to client 
	 */
	public RequestContext(OutputStream outputStream, Map<String, String> parameters,
			Map<String, String> persistentParameters, List<RCCookie> outputCookies, 
			Map<String,String> temporaryParameters, IDispatcher dispatcher) {
		
		Objects.requireNonNull(outputStream, "Output can not be null.");
		
		this.outputStream = outputStream;
		this.parameters = (parameters != null) ? Collections.unmodifiableMap(parameters) : new HashMap<>();
		this.persistentParameters = (persistentParameters != null) ? persistentParameters: new HashMap<>();
		this.outputCookies = (outputCookies != null) ? outputCookies : new ArrayList<>();
		this.temporaryParameters = temporaryParameters;
		this.dispatcher = dispatcher;
	}

	/**
	 * Get dispatcher.
	 * 
	 * @return dispatcher
	 */
	public IDispatcher getDispatcher() {
		return dispatcher;
	}

	/**
	 * Set encoding.
	 * 
	 * @param encoding encoding
	 * @throws IllegalStateException if called after header has been generated.
	 */
	public void setEncoding(String encoding) {
		if (headerGenerated) {
			throw new IllegalStateException("Can not change encoding after header is generated.");
		}
		
		this.encoding = encoding;
	}

	/**
	 * Set status code.
	 * 
	 * @param statusCode stataus code
	 * @throws IllegalStateException if called after header has been generated.
	 */
	public void setStatusCode(int statusCode) {
		if (headerGenerated) {
			throw new IllegalStateException("Can not change status code after header is generated.");
		}
		
		this.statusCode = statusCode;
	}

	/**
	 * Set status text.
	 * 
	 * @param statusText status text
	 * @throws IllegalStateException if called after header has been generated.
	 */
	public void setStatusText(String statusText) {
		if (headerGenerated) {
			throw new IllegalStateException("Can not change status text after header is generated.");
		}
		
		this.statusText = statusText;
	}

	/**
	 * Set mime type.
	 * 
	 * @param mimeType mime type
	 * @throws IllegalStateException if called after header has been generated.
	 */
	public void setMimeType(String mimeType) {
		if (headerGenerated) {
			throw new IllegalStateException("Can not change mime type after header is generated.");
		}
		
		this.mimeType = mimeType;
	}
	
	/**
	 * Add cookie to outputCookies
	 * 
	 * @param outputCookies cookies
	 * @throws IllegalStateException if called after header has been generated.
	 */
	public void addRCCookie(RCCookie outputCookies) {
		if (headerGenerated) {
			throw new IllegalStateException("Can not add cookies after header is generated.");
		}
		
		this.outputCookies.add(outputCookies);
	}

	/**
	 * Get parameters.
	 * 
	 * @return parameters
	 */
	public Map<String, String> getParameters() {
		return Collections.unmodifiableMap(parameters);
	}

	/**
	 * Get temporary parameters.
	 * 
	 * @return temporary parameters
	 */
	public Map<String, String> getTemporaryParameters() {
		return temporaryParameters;
	}

	/**
	 * Set temporary parameters.
	 * 
	 * @param temporaryParameters temporary parameters
	 */
	public void setTemporaryParameters(Map<String, String> temporaryParameters) {
		this.temporaryParameters = temporaryParameters;
	}

	/**
	 * Get persistent parameters.
	 * 
	 * @return persistent parameters
	 */
	public Map<String, String> getPersistentParameters() {
		return persistentParameters;
	}

	/**
	 * Set persistent parameters.
	 * 
	 * @param persistentParameters persistent parameters
	 */
	public void setPersistentParameters(Map<String, String> persistentParameters) {
		this.persistentParameters = persistentParameters;
	}
	
	/**
	 * Get parameter with key name.
	 * 
	 * @param name key
	 * @return value under key name or null if non existent
	 */
	public String getParameter(String name) {
		if (parameters.containsKey(name)) return parameters.get(name);
		
		return null;
	}

	/**
	 * Get parameters keys.
	 * 
	 * @return parameters key set
	 */
	public Set<String> getParameterNames() {
		return Collections.unmodifiableSet(parameters.keySet());
	}
	
	/**
	 * Get persistent parameter under key name.
	 * 
	 * @param name key
	 * @return value under key name or null if non existent
	 */
	public String getPersistentParameter(String name) {
		if (persistentParameters.containsKey(name)) return persistentParameters.get(name);
		
		return null;
	}
	
	/**
	 * Get persistent parameters key set.
	 * 
	 * @return persistent parameters key set.
	 */
	public Set<String> getPersistentParameterNames() {
		return Collections.unmodifiableSet(persistentParameters.keySet());
	}
	
	/**
	 * Set persistent parameter.
	 * 
	 * @param name key
	 * @param value value
	 */
	public void setPersistentParameter(String name, String value) {
		persistentParameters.put(name, value);
	}
	
	/**
	 * Remove persistent parameter with key name.
	 * 
	 * @param name key
	 */
	public void removePersistentParameter(String name) {
		persistentParameters.remove(name);
	}
	
	/**
	 * Get temporary parameter with key name.
	 * 
	 * @param name key
	 * @return value under key name or null if non existent
	 */
	public String getTemporaryParameter(String name) {
		if (temporaryParameters.containsKey(name)) return temporaryParameters.get(name);
		
		return null;
	}
	
	/**
	 * Get temporary parameters key set.
	 * 
	 * @return temporary parameters key set
	 */
	public Set<String> getTemporaryParameterNames() {
		return Collections.unmodifiableSet(temporaryParameters.keySet());
	}

	/**
	 * Set temporary parameter.
	 * 
	 * @param name key
	 * @param value value
	 */
	public void setTemporaryParameter(String name, String value) {
		temporaryParameters.put(name, value);
	}
	
	/**
	 * Remove temporary parameter with key name
	 * 
	 * @param name key
	 */
	public void removeTemporaryParameter(String name) {
		temporaryParameters.remove(name);
	}

	/**
	 * Writes data to outputStream.
	 * 
	 * @param data data to be sent to outputStream
	 * @return this
	 * @throws IOException if error when writing
	 */
	public RequestContext write(byte[] data) throws IOException {
		if (!headerGenerated) {
			String header = createHeader();
			outputStream.write(header.getBytes(StandardCharsets.ISO_8859_1));
			headerGenerated = true;	
		}
		
		outputStream.write(data);
		return this;
	}
	
	/**
	 * Writes text to outputStream.
	 * 
	 * @param text text to be sent to outputStream
	 * @return this
	 * @throws IOException if error when writing
	 */
	public RequestContext write(String text) throws IOException {
		if (!headerGenerated) {
			String header = createHeader();
			outputStream.write(header.getBytes(StandardCharsets.ISO_8859_1));
			headerGenerated = true;
		}
		
		outputStream.write(text.getBytes(charset));
		return this;
	}

	/**
	 * Creates header.
	 * 
	 * @return created header
	 */
	private String createHeader() {
		charset = Charset.forName(encoding);
		String mimeCharset = (mimeType.startsWith("text/")) ? "; charset=" + encoding : "";
		StringBuilder cookies = new StringBuilder("");
		
		for (RCCookie cookie : outputCookies) {
			cookies.append("Set-Cookie: " + cookie.name + "=\"" + cookie.value + "\"");
			
			cookies.append((cookie.domain != null) ? "; Domain=" + cookie.domain : "");
			cookies.append((cookie.path != null) ? "; Path=" + cookie.path : "");
			cookies.append((cookie.maxAge != null) ? "; Max-Age=" + cookie.maxAge: "");
			cookies.append((cookie.name.equals("sid")) ? "; HttpOnly": "");
			cookies.append("\r\n");
		}
		
		return HTTP_VERSION + " " + statusCode + " " + statusText + "\r\n" +
				"Content-Type: " + mimeType + mimeCharset +"\r\n" +
				cookies.toString() +
				"\r\n";
	}
	
}
