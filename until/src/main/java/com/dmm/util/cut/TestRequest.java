package com.dmm.util.cut;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.helper.HttpConnection;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.*;
import java.util.HashMap;
import java.util.Map;

/**
 * @author: Dmm
 * @date: 2019/2/21 14:15
 */
public class TestRequest {

    /**
     * @描述：HttpURLConnection 接口调用 GET方式
     * @param strUrl 请求地址
     * @param param 请求参数拼接
     * @return 请求结果集
     */
    public static String httpURLConectionGET(String strUrl, String param) {

        StringBuffer sb = new StringBuffer("");

        BufferedReader br =null;
        HttpURLConnection connection =null;
        try {
            //strUrl = strUrl + "?" + param.trim();
            URL url = new URL(strUrl);
            // 把字符串转换为URL请求地址

            // 实例化本地代理对象
            Proxy proxy= new Proxy(Proxy.Type.HTTP,new InetSocketAddress("test.httpproxy.open.famwifi.com",8108));
            //Authenticator.setDefault(new SimpleAuthenticator(proxyUserName,proxyPassword));

            connection = (HttpURLConnection) url.openConnection(proxy);
            // 打开连接
            connection.setRequestProperty("accept", "*/*");
            connection.setRequestProperty("connection", "Keep-Alive");
            connection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:33.0) Gecko/20100101 Firefox/33.0");

            connection.setRequestProperty("Api-Apikey","10888eccc4020bee4b19b89a");
            connection.setConnectTimeout(60000);
            connection.setDoOutput(true);
            connection.connect();// 连接会话

            if(connection.getResponseCode()==200){
                // 获取输入流
                br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String line;
                while ((line = br.readLine()) != null) {
                    // 循环读取流
                    sb.append(line);
                }
            }else{
                System.out.println("请求失败!HTTP Status："+connection.getResponseCode());
                System.out.println(connection.getErrorStream());
            }

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("失败!");
        }finally{
            try {
                if(br != null){
                    br.close();// 关闭流
                }
                if(connection != null){
                    connection.disconnect();// 断开连接
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return sb.toString();
    }
    //废弃
//    public static String httpURLConectionGET2(String strUrl, String param) {
//
//        HttpClient client = new HttpClient();
//        //设置代理服务器地址和端口
//        //client.getHostConfiguration().setProxy("proxy_host_addr",proxy_port);
//        //使用GET方法，如果服务器需要通过HTTPS连接，那只需要将下面URL中的http换成https
//        HttpMethod method = new GetMethod("http://java.sun.com");
//        //使用POST方法
//        //HttpMethod method = new PostMethod("http://java.sun.com";);
//        client.executeMethod(method);
//        //打印服务器返回的状态
//        System.out.println(method.getStatusLine());
//        //打印返回的信息
//        System.out.println(method.getResponseBodyAsString());
//        //释放连接
//        method.releaseConnection();
//        return null;
//    }

    /**
     * 发送HttpPost请求
     *
     * @param strURL
     *            服务地址
     * @param params
     *            json字符串,例如: "{ \"id\":\"12345\" }" ;其中属性名必须带双引号<br/>
     * @return 成功:返回json字符串<br/>
     */
    public static String httpURLConectionPost(String strURL, String params) {

        System.out.println(strURL);
        System.out.println(params);
        BufferedReader reader = null;
        try {

            // 实例化本地代理对象
            Proxy proxy= new Proxy(Proxy.Type.HTTP,new InetSocketAddress("test.httpproxy.open.famwifi.com",8108));

            URL url = new URL(strURL);
            // 创建连接
            HttpURLConnection connection = (HttpURLConnection) url.openConnection(proxy);

            connection.setRequestProperty("Api-Apikey","10888eccc4020bee4b19b89a");
            connection.setRequestProperty("accept", "*/*");
            connection.setRequestProperty("connection", "Keep-Alive");
            connection.setRequestProperty("user-agent","Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            //post请求必须加的
            connection.setDoOutput(true);
            connection.setDoInput(true);

            connection.setUseCaches(false);
            connection.setInstanceFollowRedirects(true);
            connection.setRequestMethod("POST");
            // 设置请求方式
            // connection.setRequestProperty("Accept", "application/json");
            // 设置接收数据的格式

            connection.setRequestProperty("Content-Type", "application/json;charse=UTF-8");
            // 设置发送数据的格式
            connection.connect();
            //一定要用BufferedReader 来接收响应， 使用字节来接收响应的方法是接收不到内容的
            OutputStreamWriter out = new OutputStreamWriter(connection.getOutputStream(), "UTF-8");
            // utf-8编码 发送json数据
            out.append(params);
            out.flush();
            out.close();
            // 读取响应
            reader = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"));
            String line;
            String res = "";
            while ((line = reader.readLine()) != null) {
                res += line;
            }
            reader.close();

            //如果一定要使用如下方式接收响应数据， 则响应必须为: response.getWriter().print(StringUtils.join("{\"errCode\":\"1\",\"errMsg\":\"", message, "\"}")); 来返回
//            int length = (int) connection.getContentLength();// 获取长度
//            if (length != -1) {
//                byte[] data = new byte[length];
//                byte[] temp = new byte[512];
//                int readLen = 0;
//                int destPos = 0;
//                while ((readLen = is.read(temp)) > 0) {
//                    System.arraycopy(temp, 0, data, destPos, readLen);
//                    destPos += readLen;
//                }
//                String result = new String(data, "UTF-8"); // utf-8编码
//                System.out.println(result);
//                return result;
//            }
            System.out.println(res+"--------------------------");
            return res;
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return "error"; // 自定义错误信息
    }

//    }

    public static void jsoupPost(String json) throws IOException {
        Map<String, String> header = new HashMap<String, String>();
        header.put("Api-Apikey","10888eccc4020bee4b19b89a");

        header.put("User-Agent","Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.67 Safari/537.36");
        header.put("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8");
        header.put("Accept-Language","zh-CN,zh;q=0.9");
        header.put("Accept-Charset","  GB2312,utf-8;q=0.7,*;q=0.7");
        header.put("Accept-Encoding","gzip, deflate");
        header.put("Connection", "keep-alive");
        header.put("Cache-Control","no-cache");
        header.put("Pragma","no-cache");
        header.put("Content-Type","application/json;charset=UTF-8");
        //headers(header)
        //data(header)
        Connection connection = Jsoup.connect("http://test.admin.famwifi.com/api/admin/user/info/login").headers(header).requestBody(json).method(Connection.Method.POST).proxy("test.httpproxy.open.famwifi.com", 8108).ignoreContentType(true).ignoreHttpErrors(true);
        Connection.Response r=connection.execute();
        System.out.println(r.statusCode());
        System.out.println(r.body());

    }
    public static void jsoupGet() throws IOException {
        Map<String, String> header = new HashMap<String, String>();
        header.put("Api-Apikey","10888eccc4020bee4b19b89a");

        header.put("User-Agent","Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.67 Safari/537.36");
        header.put("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8");
        header.put("Accept-Language","zh-CN,zh;q=0.9");
        header.put("Accept-Charset","  GB2312,utf-8;q=0.7,*;q=0.7");
        header.put("Accept-Encoding","gzip, deflate");
        header.put("Connection", "keep-alive");
        header.put("Cache-Control","no-cache");
        header.put("Pragma","no-cache");

        header.put("Upgrade-Insecure-Requests","1");
        //header.put("Host","www.runoob.com");
        //headers(header) http://test.user.famwifi.com/api/test/ping
        //data(header) http://pic.58pic.com/58pic/15/68/59/71X58PICNjx_1024.jpg
        //http://www.cnblogs.com/zrui-xyu/p/5341932.html
        Connection connection = Jsoup.connect("http://test.user.famwifi.com/api/test/ping").headers(header).method(Connection.Method.GET).proxy("test.httpproxy.open.famwifi.com", 8108).ignoreContentType(true).ignoreHttpErrors(true);
        //http://test.user.famwifi.com/api/test/ping

        Connection.Response r=connection.execute();
        System.out.println(r.statusCode());
        System.out.println(r.body());
    }

//    public class JavaPostJson2 {
//        final static String url = "http://localhost:1111/sendData";
//        final static String params = "{\"token\":\"12345\","
//                + "\"appId\":\"20180628173051169c85d965d164ed9ab1281d22ff350ec19\"" + "\"appName\":\"test33\""
//                + "\"classTopic\":\"112\"" + "\"eventTag\":\"22\"" + "}";

//        public static void main(String[] args) {
//
//            String res = post(url, params);
//            System.out.println(res);
//
//        }



}
