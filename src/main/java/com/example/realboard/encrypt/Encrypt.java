package com.example.realboard.encrypt;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

public class Encrypt {

    public static String alg = "AES/CBC/PKCS5Padding";
    private static final String key = "abcdefghabcdefghabcdefghabcdefgh";
    private static String iv = "0123456789abcdef";

    //'plain text  ->  pain bytes  ->  encrypt  ->  encrypted bytes  ->  encrypted base64 test'
    public static String encryptAES256(String text) throws Exception{

        Cipher cipher = Cipher.getInstance(alg);
        SecretKeySpec keySpec = new SecretKeySpec(key.getBytes(),"AES");
        IvParameterSpec ivParameterSpec = new IvParameterSpec(iv.getBytes());
        cipher.init(Cipher.ENCRYPT_MODE, keySpec, ivParameterSpec);

        byte[] encrypted = cipher.doFinal(text.getBytes("UTF-8"));
        return Base64.getEncoder().encodeToString(encrypted);

    }

    public static String decryptAES256(String cipherText) throws Exception{

        Cipher cipher = Cipher.getInstance(alg);
        SecretKeySpec keySpec = new SecretKeySpec(key.getBytes(),"AES");
        IvParameterSpec ivParameterSpec = new IvParameterSpec(iv.getBytes());
        cipher.init(Cipher.DECRYPT_MODE, keySpec, ivParameterSpec);

        byte[] decodedBytes = Base64.getDecoder().decode(cipherText);
        byte[] decrypted = cipher.doFinal(decodedBytes);

        return new String(decrypted,"UTF-8");

    }

}
