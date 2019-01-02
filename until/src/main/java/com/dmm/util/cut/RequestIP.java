package com.dmm.util.cut;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * ip.cn的页面请求和
 */
public class RequestIP {

    public static String captureJavascript(String getIP){

        URL url = null;

        HttpURLConnection connection = null;

        try {

            url = new URL(getIP);

            connection = (HttpURLConnection) url.openConnection();

            connection.setRequestProperty("User-agent", "	Mozilla/5.0 (Windows NT 6.1; WOW64; rv:33.0) Gecko/20100101 Firefox/33.0");

            InputStreamReader input = new InputStreamReader(connection.getInputStream(), "utf-8");

            BufferedReader bufferedReader = new BufferedReader(input);

            String line = "";

            StringBuilder stringBuilder = new StringBuilder();

            while ((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line);
            }
            System.out.println("captureJavascript()的结果 待解决：\n" + stringBuilder.toString());

            bufferedReader.close();

            return stringBuilder.toString();
        }catch (Exception e){
			
            e.printStackTrace();
        }finally {
            connection.disconnect();
        }

        return null;
    }

}
