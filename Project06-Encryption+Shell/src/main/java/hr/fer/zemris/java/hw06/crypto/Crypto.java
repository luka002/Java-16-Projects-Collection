package hr.fer.zemris.java.hw06.crypto;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.spec.AlgorithmParameterSpec;
import java.util.Scanner;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * Program that  allow the user to encrypt/decrypt given file using 
 * the AES cryptoalgorithm and the 128-bit encryption key or calculate 
 * and check the SHA-256 file digest. Arguments are provided trough 
 * command line. For digest first argument is "checksha" and second
 * is file name for which digest will be caculated. For encryption/decryption
 * first argument is "encrypt"/"decrypt" followed by source file and destination
 * file respectively.
 * 
 * @author Luka Grgić
 * @version 1.0
 */
public class Crypto {

	/**
	 * Method that first executes when program starts.
	 * 
	 * @param args Command line arguments.
	 */
	public static void main(String[] args) {

		if (args.length == 2) {
			String keyWord = args[0];
			String inputFile = args[1];

			if (!keyWord.equals("checksha")) {
				System.err.println("Wrong key word.");
				return;
			}
				

			Scanner scanner = new Scanner(System.in);

			System.out.print("Please provide expected sha-256 digest for " + inputFile + ":\n> ");
			String expectedDigest = scanner.nextLine();

			scanner.close();
			MessageDigest sha = null;

			try {
				sha = MessageDigest.getInstance("SHA-256");
			} catch (NoSuchAlgorithmException e) {
				System.err.println("No such algorithm.");
			}

			Path path = Paths.get(inputFile);

			try (InputStream inputStream = Files.newInputStream(path, StandardOpenOption.READ)) {
				byte[] buff = new byte[1024];

				while (true) {
					int r = inputStream.read(buff);
					if (r < 1)
						break;
					sha.update(buff, 0, r);
				}
			} catch (IOException ex) {
				System.err.println("Error with file.");
			}

			byte[] hash = sha.digest();
			String calculatedDigest = Util.bytetohex(hash);

			if (expectedDigest.equals(calculatedDigest)) {
				System.out.println("Digesting completed. Digest of " + inputFile + " matches expected digest.");
				return;
			}

			System.out.println("Digesting completed. Digest of " + inputFile
					+ " does not match the expected digest. Digest\n" + "was: " + calculatedDigest);

		} else if (args.length == 3) {
			String keyWord = args[0];
			String inputFile = args[1];
			String outputFile = args[2];
			boolean encrypt;

			if (keyWord.equals("encrypt")) {
				encrypt = true;
			} else if (keyWord.equals("decrypt")) {
				encrypt = false;
			} else {
				System.err.println("Wrong key word.");
				return;
			}

			Scanner scanner = new Scanner(System.in);

			System.out.print("Please provide password as hex-encoded text (16 bytes, i.e. 32 hex-digits):\n> ");
			String keyText = scanner.nextLine().trim();
			System.out.print("Please provide initialization vector as hex-encoded text (32 hex-digits):\n> ");
			String ivText = scanner.nextLine().trim();

			scanner.close();
			
			SecretKeySpec keySpec = new SecretKeySpec(Util.hextobyte(keyText), "AES");
			AlgorithmParameterSpec paramSpec = new IvParameterSpec(Util.hextobyte(ivText));
			Cipher cipher = null;
			try {
				cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
				cipher.init(encrypt ? Cipher.ENCRYPT_MODE : Cipher.DECRYPT_MODE, keySpec, paramSpec);
			} catch (Exception e) {
				
			}

			try (InputStream inputStream = new BufferedInputStream(new FileInputStream(inputFile));
					OutputStream outputStream = new BufferedOutputStream(new FileOutputStream(outputFile))) {
				byte[] buff = new byte[1024];

				while (true) {
					int r = inputStream.read(buff);
					if (r < 1)
						break;
					byte[] output = cipher.update(buff, 0, r);
					outputStream.write(output, 0, output.length);
				}
				
				byte[] output = cipher.doFinal();
				if (output != null) {
					outputStream.write(output);
				}
			} catch (Exception ex) {
				ex.printStackTrace();
			}

			System.out.println(encrypt ? "Encryption" : "Decryption" + " completed. Generated file " + outputFile + " based on file " + inputFile);	
		}

	}

}
