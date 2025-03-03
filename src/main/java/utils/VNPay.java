package utils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.TimeZone;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import model.Orders;
import org.json.JSONArray;
import org.json.JSONObject;

public class VNPay {

    private static final String vnp_BaseUrl = "https://sandbox.vnpayment.vn/paymentv2/vpcpay.html";
    private static final String vnp_Version = "2.1.0";
    private static final String vnp_Command = "pay";
    private static final String vnp_TmnCode = "AT9WCUDF";
    private static final String vnp_CurrCode = "VND";
    private static final String vnp_IpAddr = "127.0.0.1";
    private static final String vnp_Locale = "vn";
    private static final String vnp_OrderType = "other";
    private static final String vnp_ReturnUrl = "http://localhost:8080/order/verify?orderId=";
    private static final String vnp_HashSecret = "8ZVNS1F9CYTVURGLC6FVY1Q8FB430SUO";

    public static String getPaymentURL(Orders order) throws UnsupportedEncodingException {
        Calendar now = Calendar.getInstance(TimeZone.getTimeZone("Etc/GMT+7"));

        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
        Map vnp_Params = new HashMap<>();

        vnp_Params.put("vnp_Version", vnp_Version);
        vnp_Params.put("vnp_Command", vnp_Command);
        vnp_Params.put("vnp_TmnCode", vnp_TmnCode);
        vnp_Params.put("vnp_Amount", String.valueOf((long) (order.getTotalAmount() * 100)));
        vnp_Params.put("vnp_CreateDate", formatter.format(now.getTime()));
        vnp_Params.put("vnp_CurrCode", vnp_CurrCode);
        vnp_Params.put("vnp_IpAddr", vnp_IpAddr);
        vnp_Params.put("vnp_Locale", vnp_Locale);
        vnp_Params.put("vnp_OrderInfo", "Thanh toan hoa don " + order.getOrderId());
        vnp_Params.put("vnp_OrderType", vnp_OrderType);
        vnp_Params.put("vnp_ReturnUrl", vnp_ReturnUrl + order.getOrderId());
        now.add(Calendar.MINUTE, 30);
        vnp_Params.put("vnp_ExpireDate", formatter.format(now.getTime()));
        vnp_Params.put("vnp_TxnRef", getRandomNumber(8));

        return vnp_BaseUrl + "?" + hashAllFields(vnp_Params);
    }

    private static String hashAllFields(Map fields) throws UnsupportedEncodingException {
        List fieldNames = new ArrayList(fields.keySet());
        Collections.sort(fieldNames);
        StringBuilder sb = new StringBuilder();
        Iterator itr = fieldNames.iterator();
        while (itr.hasNext()) {
            String fieldName = (String) itr.next();
            String fieldValue = (String) fields.get(fieldName);
            if ((fieldValue != null) && (fieldValue.length() > 0)) {
                sb.append(fieldName);
                sb.append("=");
                sb.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII.toString()));
            }
            if (itr.hasNext()) {
                sb.append("&");
            }
        }
        return sb.toString() + "&vnp_SecureHash=" + hmacSHA512(vnp_HashSecret, sb.toString());
    }

    private static String hmacSHA512(String key, String data) {
        try {
            if (key == null || data == null) {
                throw new NullPointerException();
            }
            final Mac hmac512 = Mac.getInstance("HmacSHA512");
            byte[] hmacKeyBytes = key.getBytes();
            final SecretKeySpec secretKey = new SecretKeySpec(hmacKeyBytes, "HmacSHA512");
            hmac512.init(secretKey);
            byte[] dataBytes = data.getBytes(StandardCharsets.UTF_8);
            byte[] result = hmac512.doFinal(dataBytes);
            StringBuilder sb = new StringBuilder(2 * result.length);
            for (byte b : result) {
                sb.append(String.format("%02x", b & 0xff));
            }
            return sb.toString();

        } catch (Exception ex) {
            return "";
        }
    }

    private static String getRandomNumber(int len) {
        Random rnd = new Random();
        String chars = "0123456789";
        StringBuilder sb = new StringBuilder(len);
        for (int i = 0; i < len; i++) {
            sb.append(chars.charAt(rnd.nextInt(chars.length())));
        }
        return sb.toString();
    }
}
