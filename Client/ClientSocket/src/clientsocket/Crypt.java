/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clientsocket;

import java.security.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 *
 * @author Ro7Rinke
 */
public class Crypt {
    
    private PrivateKey privateKey;
    private PublicKey publicKey;
    private byte salt[];
    private SecretKey key;
    
    public void startKeys (String algorithm, int size) {
        switch(algorithm){
            case "rsa":
                try {
                    this.generateKeysRSA(size);
                } catch (NoSuchAlgorithmException ex) {
                    Logger.getLogger(Crypt.class.getName()).log(Level.SEVERE, null, ex);
                }
                break;
            case "aes":
                try {
                    this.generateKeyAES(size, this.randomString(12));
                } catch (NoSuchAlgorithmException ex) {
                    Logger.getLogger(Crypt.class.getName()).log(Level.SEVERE, null, ex);
                }
                break;
        }
    }
    
    public byte[] selectEncrypt (String algorithm, String data){
        byte[] result = null;
        switch(algorithm){
            case "rsa":
                try {
                    result = this.encryptRSA(data);
                } catch (BadPaddingException | IllegalBlockSizeException | InvalidKeyException | NoSuchPaddingException | NoSuchAlgorithmException ex) {
                    Logger.getLogger(Crypt.class.getName()).log(Level.SEVERE, null, ex);
                }
                break;
            case "aes":
                try {
                    result = this.encryptAES(data);
                } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | IllegalBlockSizeException | BadPaddingException | InvalidAlgorithmParameterException ex) {
                    Logger.getLogger(Crypt.class.getName()).log(Level.SEVERE, null, ex);
                }
                break;
        }
        return result;
    }
    
    public String selectDecrypt (String algorithm, byte[] data){
        String result = null;
        switch(algorithm){
            case "rsa":
                try{
                    result = this.decryptRSA(data);
                } catch (Exception ex) {
                    Logger.getLogger(Crypt.class.getName()).log(Level.SEVERE, null, ex);
                }
                break;
            case "aes":
                try {
                    result = this.decryptAES(data);
                } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | IllegalBlockSizeException | BadPaddingException | InvalidAlgorithmParameterException ex) {
                    Logger.getLogger(Crypt.class.getName()).log(Level.SEVERE, null, ex);
                }
                break;
        }
        return result;
    }
    
    public String randomString( int len ){
        String database = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
        SecureRandom rnd = new SecureRandom();
        StringBuilder sb = new StringBuilder( len );
        for( int i = 0; i < len; i++ ) 
           sb.append( database.charAt( rnd.nextInt(database.length()) ) );
        return sb.toString();
     }
    
    public void generateKeysRSA (int size) throws NoSuchAlgorithmException {
        KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");
        keyGen.initialize(size);
        KeyPair keys = keyGen.generateKeyPair();
        this.privateKey = keys.getPrivate();
        this.publicKey = keys.getPublic();
    }
    
    public PrivateKey getPrivateKey() {
        return privateKey;
    }

    public PublicKey getPublicKey() {
        return publicKey;
    }
    
    public byte[] encryptRSA(String data) throws BadPaddingException, IllegalBlockSizeException, InvalidKeyException, NoSuchPaddingException, NoSuchAlgorithmException {
	Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
	cipher.init(Cipher.ENCRYPT_MODE, this.publicKey);
	return cipher.doFinal(data.getBytes());
    }

    public String decryptRSA(byte[] data) throws Exception {
        Cipher decriptCipher = Cipher.getInstance("RSA");
        decriptCipher.init(Cipher.DECRYPT_MODE, this.privateKey);

        return new String(decriptCipher.doFinal(data));
    }
    
    public void generateKeyAES(int size, String password) throws NoSuchAlgorithmException {
        SecureRandom secRnd = SecureRandom.getInstanceStrong();
        this.salt = new byte[16];
        secRnd.nextBytes(salt);
        //PBEKeySpec spec = new PBEKeySpec(password.toCharArray(), this.salt, 1000, size*8);
        //this.key = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1").generateSecret(spec);
        KeyGenerator keyGen = KeyGenerator.getInstance("AES");
        keyGen.init(size);
        this.key = keyGen.generateKey();
    }
    
    public byte[] encryptAES(String data) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException, InvalidAlgorithmParameterException{
        Cipher aes = Cipher.getInstance("AES/CBC/PKCS5Padding");
        aes.init(Cipher.ENCRYPT_MODE, this.key, new IvParameterSpec(this.salt));
        return aes.doFinal(data.getBytes());
    }
    
    public String decryptAES(byte[] data) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException, InvalidAlgorithmParameterException{
        Cipher aes = Cipher.getInstance("AES/CBC/PKCS5Padding");
        aes.init(Cipher.DECRYPT_MODE, new SecretKeySpec(this.key.getEncoded(), "AES"), new IvParameterSpec(this.salt));
        return new String(aes.doFinal(data));
    }
    
}
