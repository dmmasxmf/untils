package com.dmm.until.string;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.security.MessageDigest;

/**
 * @author: Dmm
 * @date: 2019/5/7 16:22
 * 字符串工具类
 */
public class StringUtil {

    private static final String[] hexDigits = new String[]{"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "a", "b", "c", "d", "e", "f"};

    public StringUtil() {
    }

    public static final boolean isEmpty(String str) {
        return str == null || "".equals(str);
    }

    private static String byteArrayToHexString(byte[] b) {
        StringBuffer resultSb = new StringBuffer();

        for(int i = 0; i < b.length; ++i) {
            resultSb.append(byteToHexString(b[i]));
        }

        return resultSb.toString();
    }

    public static final String localIp() throws UnknownHostException {
        return InetAddress.getLocalHost().getHostAddress();
    }

    private static String byteToHexString(byte b) {
        int n = b;
        if (b < 0) {
            n = b + 256;
        }

        int d1 = n / 16;
        int d2 = n % 16;

        return hexDigits[d1]+hexDigits[d2];
    }

    public static String MD5Encode(String origin) {
        return MD5Encode(origin, "utf-8");
    }

    public static String MD5Encode(String origin, String charsetname) {
        String resultString = null;

        try {
            resultString = new String(origin);
            MessageDigest md = MessageDigest.getInstance("MD5");
            if (charsetname != null && !"".equals(charsetname)) {
                resultString = byteArrayToHexString(md.digest(resultString.getBytes(charsetname)));
            } else {
                resultString = byteArrayToHexString(md.digest(resultString.getBytes()));
            }
        } catch (Exception e) {
            ;
        }

        return resultString;
    }
}
