package com.reljicd.util;

import sun.misc.BASE64Decoder;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by Administrator on 2018-07-17.
 */
public class HttpUtils {

    public static String getUserName(HttpServletRequest httpRequest){
        String auth = httpRequest.getHeader("Authorization");
        if ((auth != null) && (auth.length() > 6)) {
            String HeadStr = auth.substring(0, 5).toLowerCase();
            if (HeadStr.compareTo("basic") == 0) {
                auth = auth.substring(6, auth.length());
                String decodedAuth = getFromBASE64(auth);
                if (decodedAuth != null) {
                    String[] UserArray = decodedAuth.split(":");
                    if (UserArray != null && UserArray.length == 2) {
                        return UserArray[0];
                    }
                }
            }
        }
        return null;
    }
    private static String getFromBASE64(String s) {
        if (s == null)
            return null;
        BASE64Decoder decoder = new BASE64Decoder();
        try {
            byte[] b = decoder.decodeBuffer(s);
            return new String(b);
        } catch (Exception e) {
            return null;
        }
    }
}
