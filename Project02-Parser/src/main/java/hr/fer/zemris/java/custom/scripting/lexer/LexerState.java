package hr.fer.zemris.java.custom.scripting.lexer;

/**
 * Enumeration of states that lexer can be in.
 * 
 * @author Luka Grgić
 * @version 1.0
 */
public enum LexerState {
	
	/** For handling FOR tag token. */
	FORTAG,
	/** For handling text tag token. */
	TEXT, 
	/** For handling ECHO tag starting with "=" symbol token. */
	ECHOEQUALSTAG, 
	/** For handling ECHO tag starting with variable token. */
	ECHOVARIABLETAG, 
	/** For handling END tag token. */
	ENDTAG	
}
