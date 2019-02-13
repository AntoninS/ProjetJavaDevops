package Util;

import java.nio.charset.StandardCharsets;
import java.security.*;

public class MD5Util {

    public MD5Util() {

    }

    /**
     * Permet d'obtenir le hash MD5 d'une String
     * @param stringToHash
     * @return
     */
    public static String getMD5(String stringToHash) {
        MessageDigest md = null;
        String stringHashed = null;
        try {
            md = MessageDigest.getInstance("MD5");
            byte[] hashInBytes = md.digest(stringToHash.getBytes(StandardCharsets.UTF_8));

            StringBuilder sb = new StringBuilder();
            for (byte b : hashInBytes) {
                sb.append(String.format("%02x", b));
            }
            stringHashed = sb.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        return stringHashed;
    }
}
