package com.dmm.ip.taobao;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;


import com.dmm.ip.util.LogFactory;
import com.dmm.until.jackson.JacksonUtil;
import com.fasterxml.jackson.databind.JavaType;
import com.google.gson.JsonParser;
import org.apache.log4j.Level;

/**
 * 淘宝的ip工具类
 */

public class AddressUtils {

    /**
     *返回类型的对象
     */
    public static <T> IpTaoBaoResult getAddress(String path, String params, String encoding) throws Exception {
		//ip

        String str=getAddressToJSON(path,params,encoding);
		//返回json 字符串

        if(str!=null && !str.equals("")){
            int code=new JsonParser().parse(str).getAsJsonObject().get("code").getAsInt();
            if(code==1){
                return null;
            }
            JavaType javaType= JacksonUtil.getCollectionType(IpTaoBaoResult.class, IpTaoBao.class);
            IpTaoBaoResult<IpTaoBao> ipTaoBaoResult=JacksonUtil.JsonToBean(str,javaType);
            return ipTaoBaoResult;
        }

        return null;
    }

    /**
     * 获取地址 返回json
     *@param path http://ip.taobao.com/service/getIpInfo.php
     * @param params ip=
     * @param encoding utf-8
     * @return
     * @throws Exception
     */
    public static String getAddressToJSON(String path,String params, String encoding) throws Exception {

        //String path = "http://ip.taobao.com/service/getIpInfo.php";

        String returnStr = getRs(path, "ip="+params, encoding);
        //System.out.println(returnStr);
        return returnStr;

    }

    /**
     * 从url获取结果
     *
     * @param path
     * @param params
     * @param encoding
     * @return
     */
    public static String getRs(String path, String params, String encoding) throws Exception{

        URL url = null;

        HttpURLConnection connection = null;

        try {

            url = new URL(path);
            // 新建连接实例
            connection = (HttpURLConnection) url.openConnection();
            // 设置连接超时时间，单位毫S?
            connection.setConnectTimeout(2000);
            // 设置读取数据超时时间，单位毫S?
            connection.setReadTimeout(2000);
            // 是否打开输出S? true|false
            connection.setDoInput(true);
            // 是否打开输入流true|false
            connection.setDoOutput(true);
            // 提交方法POST|GET
            connection.setRequestMethod("POST");
            // 是否缓存true|false
            connection.setUseCaches(false);
            // 打开连接端口
            connection.connect();

            DataOutputStream out = new DataOutputStream(connection.getOutputStream());

            out.writeBytes(params);

            out.flush();

            out.close();

            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream(), encoding));

            StringBuffer buffer = new StringBuffer();

            String line = "";

            while ((line = reader.readLine()) != null) {

                buffer.append(line);
            }

            reader.close();
            return buffer.toString();
        } catch (Exception e) {
            LogFactory.log("次数限制---> {} {}", Level.DEBUG,e);
            throw e;
        } finally {
            connection.disconnect();// 关闭连接
        }

    }

    /**
     * 字符转码
     *
     * @param theString
     * @return
     */
    public static String decodeUnicode(String theString) {

        char aChar;

        int len = theString.length();

        StringBuffer buffer = new StringBuffer(len);

        for (int i = 0; i < len;) {

            aChar = theString.charAt(i++);

            if (aChar == '\\') {

                aChar = theString.charAt(i++);

                if (aChar == 'u') {

                    int val = 0;

                    for (int j = 0; j < 4; j++) {

                        aChar = theString.charAt(i++);

                        switch (aChar) {

                            case '0':

                            case '1':

                            case '2':

                            case '3':

                            case '4':

                            case '5':

                            case '6':

                            case '7':

                            case '8':

                            case '9':

                                val = (val << 4) + aChar - '0';

                                break;

                            case 'a':

                            case 'b':

                            case 'c':

                            case 'd':

                            case 'e':

                            case 'f':

                                val = (val << 4) + 10 + aChar - 'a';

                                break;

                            case 'A':

                            case 'B':

                            case 'C':

                            case 'D':

                            case 'E':

                            case 'F':

                                val = (val << 4) + 10 + aChar - 'A';

                                break;

                            default:

                                throw new IllegalArgumentException(

                                        "Malformed      encoding.");
                        }

                    }

                    buffer.append((char) val);

                } else {

                    if (aChar == 't') {

                        aChar = '\t';
                    }

                    if (aChar == 'r') {

                        aChar = '\r';
                    }

                    if (aChar == 'n') {

                        aChar = '\n';
                    }

                    if (aChar == 'f') {

                        aChar = '\f';

                    }

                    buffer.append(aChar);
                }

            } else {

                buffer.append(aChar);

            }

        }

        return buffer.toString();

    }

}
