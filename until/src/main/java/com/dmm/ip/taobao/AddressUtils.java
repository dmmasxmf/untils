package com.dmm.ip.taobao;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;


import com.dmm.until.jackson.JacksonUtil;
import com.fasterxml.jackson.databind.JavaType;
import com.google.gson.JsonParser;

/**
 * 淘宝的ip工具类
 */

public class AddressUtils {

    /**
     *返回类型的对象
     */
    public static <T> IPTaoBaoResult getAddress(String path,String params, String encoding) throws Exception {
        System.out.println(params);
        String str=getAddressToJSON(path,params,encoding);
        System.out.println(str);


        if(str!=null && !str.equals("")){
            int code=new JsonParser().parse(str).getAsJsonObject().get("code").getAsInt();
            System.out.println(code+"状态值");
            if(code==1){
                return null;
            }
            JavaType javaType= JacksonUtil.getCollectionType(IPTaoBaoResult.class,IPTaoBao.class);
            IPTaoBaoResult<IPTaoBao> ipTaoBaoResult=JacksonUtil.JsonToBean(str,javaType);
            System.out.println(ipTaoBaoResult.getData().getCity());
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
    public static String getRs(String path, String params, String encoding) {

        URL url = null;

        HttpURLConnection connection = null;

        try {

            url = new URL(path);

            connection = (HttpURLConnection) url.openConnection();// 新建连接实例

            connection.setConnectTimeout(2000);// 设置连接超时时间，单位毫S?

            connection.setReadTimeout(2000);// 设置读取数据超时时间，单位毫S?

            connection.setDoInput(true);// 是否打开输出S? true|false

            connection.setDoOutput(true);// 是否打开输入流true|false

            connection.setRequestMethod("POST");// 提交方法POST|GET

            connection.setUseCaches(false);// 是否缓存true|false

            connection.connect();// 打开连接端口

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

            e.printStackTrace();

        } finally {

            connection.disconnect();// 关闭连接

        }

        return null;
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
