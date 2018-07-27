package hr.fer.zemris.java.hw06.crypto;

/**
 * Utility class for conversion hex string to byte array and
 * byte array to hex string.
 * 
 * @author Luka Grgić
 * @version 1.0
 */
public class Util {

	/**
	 * Converts hex string to byte array.
	 * 
	 * @param keyText Hex string.
	 * @return Converted byte array.
	 */
	public static byte[] hextobyte(String keyText) {
		keyText = keyText.toLowerCase();

		if (keyText.length() % 2 != 0 || !keyText.matches("[0-9a-f]*")) {
			throw new IllegalArgumentException("Whong hex number.");
		}

		if (keyText.length() == 0)
			return new byte[0];

		byte[] data = new byte[keyText.length() / 2];

		for (int i = 0; i < keyText.length(); i += 2) {
			char first = keyText.charAt(i);
			char second = keyText.charAt(i + 1); 

			first = (char) (Character.isDigit(first) ? first - '0' : (first - 'a' + 10));
			second = (char) (Character.isDigit(second) ? second - '0' : (second - 'a' + 10));

			data[i / 2] = (byte) ((first << 4) + second);
		}

		return data;
	}

	/**
	 * Converts byte array to hex string.
	 * 
	 * @param bytearray Byte array.
	 * @return Converted hex string.
	 */
	public static String bytetohex(byte[] bytearray) {
		if (bytearray.length == 0)
			return "";

		char[] hex = new char[bytearray.length * 2];

		for (int i = 0; i < bytearray.length; i++) {
			hex[i * 2] = (char) ((bytearray[i] & 0xF0) >>> 4);
			hex[i * 2 + 1] = (char) (bytearray[i] & 0x0F);
		}
		
		for (int i = 0; i < hex.length; i++) {
			if (hex[i] >= 0 && hex[i] < 10) {
				hex[i] += '0';
			} else {
				hex[i] += 'a' - 10;
			}
		}
		
		return new String(hex);
	}

}
