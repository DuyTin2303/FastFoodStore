/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 *
 * @author a
 */
public class MD5 {
    public static String getMd5(String input) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(input.getBytes());
            byte[] digest = md.digest();
            StringBuilder sb = new StringBuilder();
            for (byte b : digest) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }
    /**
     * Hashes a password using MD5.
     * 
     * @param password The password to hash
     * @return The hashed password
     */
    public static String hashPassword(String password) {
        return getMd5(password);
    }
    
    /**
     * Verifies if a password matches a stored hash.
     * 
     * @param password The password to verify
     * @param storedHash The stored hash to compare against
     * @return true if the password matches the hash, false otherwise
     */
    public static boolean verifyPassword(String password, String storedHash) {
        String hashedPassword = getMd5(password);
        return hashedPassword != null && hashedPassword.equals(storedHash);
    }
}