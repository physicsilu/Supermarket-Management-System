package application;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


public class SHA {
    protected static String getSHA256Hash(String input) {
    	 try {
			MessageDigest md = MessageDigest.getInstance("SHA-256");
			byte[] hashbyte = md.digest(input.getBytes());
			StringBuilder hashPassword = new StringBuilder();
			for(byte b : hashbyte) {
				hashPassword.append(String.format("%02x", b));
			}
			return hashPassword.toString();
 		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			return null;
		}
     }
    
    public void main() {
    	getSHA256Hash("123");
    }
}
