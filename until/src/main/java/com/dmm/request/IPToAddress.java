package com.dmm.request;

import com.dmm.until.jackson.JacksonUtil;
import com.dmm.util.cut.RegexUtil;
import com.dmm.util.cut.RequestIP;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.util.Map;

/**
 * 请求接口
 */
public class IPToAddress {

    public static Address get138(String url) throws Exception {

        Map<String, String> map=get138ToMap(url);

        if(map==null || map.isEmpty()){
            System.out.println("判断是否为空");
            return null;
        }
        return JacksonUtil.mapToBean(map,Address.class);
    }

    public static Map<String, String> get138ToMap(String url){
        //1.请求 数据
        String str=RequestIP.captureJavascript(url); 
		
		//判断是否为空  有https的证书问题
		if(str==null || str.equals("")){
		    return null;
        }
        //2.转成dom
        Document document=Jsoup.parse(str);
        //3.获取标签内容
        String s=document.select(".result").text();
        String rgex="本站主数据：(.*?) 参考数据一";
        //4.根据正则表达式，去除无用的
        String s2= RegexUtil.getSubUtilSimple(s,rgex);
        System.out.println("138查询字符串---------------------------"+s2);
        //5.得到map类型的json对象
        Map<String,String> data=RegexUtil.addressResolution138(s2);
        System.out.println("138map集合"+data);
        return data;
    }

    public static Address getIP(String url) throws Exception {

        Map<String, String> map=getIPToMap(url);
        //System.out.println(map);
        if(map==null || map.isEmpty()){
            return null;
        }
        return JacksonUtil.mapToBean(map,Address.class);
    }


    public static Map<String, String> getIPToMap(String url){

        //1.请求 数据
        String str=RequestIP.captureJavascript(url);
        //https 处理
        if(str==null || str.equals("")){
            return null;
        }
        //2.转成dom
        Document document=Jsoup.parse(str);
        //3.获取标签内容
        String s=document.select("#result").select(".well").text();
        System.out.println(s+"----------------");
        String rgex="所在地理位置：(.*?) GeoIP";
        //4.根据正则表达式，去除无用的
        String s2= RegexUtil.getSubUtilSimple(s,rgex);
        System.out.println(s2);
        Map<String,String> data=RegexUtil.addressResolutionIP(s2);
        return data;
    }

}
