/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package utils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 *
 * @author a
 */
public class GoogleAuth {
    private String clientId;
    private String clientSecret;
    private String redirectUri;

    public GoogleAuth(String clientId, String clientSecret, String redirectUri) {
        this.clientId = clientId;
        this.clientSecret = clientSecret;
        this.redirectUri = redirectUri;
    }

    public String buildAuthorizationUrl() {
        try {
            return "https://accounts.google.com/o/oauth2/auth?" +
                   "client_id=" + URLEncoder.encode(clientId, "UTF-8") +
                   "&redirect_uri=" + URLEncoder.encode(redirectUri, "UTF-8") +
                   "&response_type=code" +
                   "&scope=" + URLEncoder.encode("email profile", "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return null;
        }
    }
}
