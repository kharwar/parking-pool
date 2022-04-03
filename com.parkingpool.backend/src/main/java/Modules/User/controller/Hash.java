package Modules.User.controller;

import java.math.BigInteger;
import java.security.MessageDigest;

public class Hash {
    public static String getHashCode(String inputString) {
        String encryptedText = "";
        try {
            MessageDigest MD = MessageDigest.getInstance("MD5");
            MD.update(inputString.getBytes());
            byte[] digest = MD.digest();
            BigInteger num = new BigInteger(1, digest);
            encryptedText = num.toString(16);
            while (encryptedText.length() < 32) {
                encryptedText = "0" + encryptedText;
            }
        }catch (Exception e){
            System.out.println("Exception occurred: " + e.toString());
        }
        return encryptedText;
    }
}
