package cn.yueying0083.pediyforum.utils;

import java.security.MessageDigest;

/**
 * Created by luoj@huoli.com on 2016/10/11.
 */

public class EncryptUtils {
    private static final String hexDigits[] = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "A", "B", "C", "D", "E", "F"};

    public static String md5(String origin) {
        return encodeToHex(origin, "UTF-8", "MD5");
    }

    public static String sha1(String origin) {
        return encodeToHex(origin, "UTF-8", "SHA1");
    }

    private static String encodeToHex(String origin, String charsetname, String type) {
        String resultString = null;
        try {
            resultString = (origin);
            MessageDigest md = MessageDigest.getInstance(type);
            if (charsetname == null || "".equals(charsetname)) {
                resultString = byteArrayToHexString(md.digest(resultString.getBytes("UTF-8")));
            } else {
                resultString = byteArrayToHexString(md.digest(resultString.getBytes(charsetname)));
            }
        } catch (Exception exception) {
            return null;
        }
        return resultString;
    }

    private static String byteArrayToHexString(byte b[]) {
        StringBuilder resultSb = new StringBuilder();
        for (byte aB : b) {
            int n = aB;
            if (n < 0) {
                n += 256;
            }
            int d1 = n / 16;
            int d2 = n % 16;
            resultSb.append(hexDigits[d1]).append(hexDigits[d2]);
        }
        return resultSb.toString();
    }

}
