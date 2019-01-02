package com.dmm.util.cut;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import com.dmm.string.stat.CountCharacter;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * Jsoup解析html标签时类似于JQuery的一些符号
 *
 * @author dmm
 *
 */
public class HtmlParse {

    protected List<List<String>> data = new LinkedList<List<String>>();

    /**
     * 获取value值
     * @param e
     * @return
     */
    public static String getValue(Element e) {
        return e.attr("value");
    }

    /**
     * 获取<tr>和</tr>之间的文本
     * @param e
     * @return
     */
    public static String getText(Element e) {
        return e.text();
    }

    /**
     * 识别属性id的标签,一般一个html页面id唯一
     * @param body
     * @param id
     * @return
     */
    public static Element getID(String body, String id) {
        Document doc = Jsoup.parse(body);
        // 所有#id的标签
        Elements elements = doc.select("#" + id);
        // 返回第一个
        return elements.first();
    }

    /**
     * 识别属性class的标签
     *
     * @param body
     * @param classTag
     * @return
     */
    public static Elements getClassTag(String body, String classTag) {
        Document doc = Jsoup.parse(body);
        // 所有#id的标签
        return doc.select("." + classTag);
    }

    /**
     * 获取tr标签元素组
     *
     * @param e
     * @return
     */
    public static Elements getTR(Element e) {
        return e.getElementsByTag("tr");
    }

    /**
     * 获取td标签元素组
     *
     * @param e
     * @return
     */
    public static Elements getTD(Element e) {
        return e.getElementsByTag("td");
    }
    /**
     * 获取表元组
     * @param table
     * @return
     */
    public static List<List<String>> getTables(Element table){
        List<List<String>> data = new ArrayList<>();

        for (Element etr : table.select("tr")) {
            List<String> list = new ArrayList<>();
            for (Element etd : etr.select("td")) {
                String temp = etd.text();
                //增加一行中的一列
                list.add(temp);
            }
            //增加一行
            data.add(list);
        }
        return data;
    }
    /**
     * 读html文件
     * @param fileName
     * @return
     */
    public static String readHtml(String fileName){
        FileInputStream fis = null;
        StringBuffer sb = new StringBuffer();
        try {
            fis = new FileInputStream(fileName);
            byte[] bytes = new byte[1024];
            while (-1 != fis.read(bytes)) {
                sb.append(new String(bytes));
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                fis.close();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
        return sb.toString();
    }
//main方法
//    public static void main(String[] args) throws Exception {
//        //182.61.126.255 203.93.3.255
//        Document doc = Jsoup.parse(RequestIP.captureJavascript("http://m.ip138.com/ip.asp?ip=101.247.154.102"));
//
//        String s=doc.select(".result").text();
//
//        String s2="本站主数据：(.*?) 参考数据一";
//
//        String s3=RegexUtil.getSubUtilSimple(s, s2);
//
//
////        if(CountCharacter.count(s3).get("spaceCharacter")==2){
////            System.out.println(RegexUtil.addressResolution1382(s3));
////        }
//
//        System.out.println(s);
//        System.out.println(s3);
//
//        System.out.println(RegexUtil.addressResolution138(s3));

//
//        //47.98.51.82  203.93.3.255 182.61.31.103 182.61.126.255 203.90.238.0 101.248.0.0
//        Document doc = Jsoup.parse(RequestIP.captureJavascript("https://ip.cn/index.php?ip=101.247.116.165"));
//
//        String s=doc.select("#result").select(".well").text();
//
//        String s2="所在地理位置：(.*?) GeoIP";
//
//        String s3=RegexUtil.getSubUtilSimple(s, s2);


//        if(CountCharacter.count(s3).get("spaceCharacter")==2){
//            System.out.println(RegexUtil.addressResolution1382(s3));
//        }

//        System.out.println(s);
//        System.out.println(s3);
//
//        System.out.println(RegexUtil.addressResolutionIP(s3));
//
//    }

}
