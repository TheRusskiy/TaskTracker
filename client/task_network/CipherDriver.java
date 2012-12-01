package task_network;

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by IntelliJ IDEA.
 * User: TheRusskiy
 * Date: 21.11.12
 * Time: 12:28
 * To change this template use File | Settings | File Templates.
 */
public class CipherDriver {

    //feature do not store like this...
    private static String sharedKey="NetCrackerFTW";

    public static String getSharedKey() {
        return sharedKey;
    }

    public static void setSharedKey(String sharedKey) {
        CipherDriver.sharedKey = sharedKey;
    }

    public static String getSHA1(String text) {
        try {
            final MessageDigest md = MessageDigest.getInstance("SHA-1");
            final byte[] digestOfText = md.digest(text.getBytes("utf-8"));
            StringBuilder result = new StringBuilder("");
            for (int i = 0; i < digestOfText.length; i++) {
                result.append((char) digestOfText[i]);
            }
            return result.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        throw new RuntimeException("Error generating SHA-1");
    }

    public static byte[] encrypt(String message, String keyText) {
        final byte[] initialKeyBytes = keyText.getBytes();
        final byte[] keyBytes=new byte[24];


        for(int i=0; i<24; i++){
            if (i<initialKeyBytes.length){
                keyBytes[i]=initialKeyBytes[i];
            }
            else{
                keyBytes[i]='_';
            }
        }
        final byte[] cipherText;
        try {
            final SecretKey key = new SecretKeySpec(keyBytes, "DESede");
            final IvParameterSpec iv = new IvParameterSpec(new byte[8]);
            final Cipher cipher = Cipher.getInstance("DESede/CBC/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, key, iv);

            final byte[] plainTextBytes = message.getBytes("utf-8");
            cipherText = cipher.doFinal(plainTextBytes);
            return cipherText;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (InvalidAlgorithmParameterException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        }
        throw new RuntimeException("Error encrypting!");
    }

    public static String decrypt(byte[] message, String keyText){
        try {
            final byte[] initialKeyBytes = keyText.getBytes();
            final byte[] keyBytes=new byte[24];
            for(int i=0; i<24; i++){
                if (i<initialKeyBytes.length){
                    keyBytes[i]=initialKeyBytes[i];
                }
                else{
                    keyBytes[i]='_';
                }
            }
            final SecretKey key = new SecretKeySpec(keyBytes, "DESede");
            final IvParameterSpec iv = new IvParameterSpec(new byte[8]);
            final Cipher decipher = Cipher.getInstance("DESede/CBC/PKCS5Padding");
            decipher.init(Cipher.DECRYPT_MODE, key, iv);
            final byte[] plainText = decipher.doFinal(message);

            return new String(plainText, "UTF-8");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (InvalidAlgorithmParameterException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        throw new RuntimeException("Error decrypting!");
    }

}
