package com.dmm.util.cut;

import com.dmm.string.stat.CountCharacter;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 正则表达式匹配两个字符串之间的内容
 * @author dmm
 *
 */
public class RegexUtil {

    /**
     * 正则表达式匹配两个指定字符串中间的内容
     * @param soap
     * @return
     */
    public static List<String> getSubUtil(String soap,String rgex){
        List<String> list = new ArrayList<String>();
        Pattern pattern = Pattern.compile(rgex);
        // 匹配的模式
        Matcher m = pattern.matcher(soap);
        while (m.find()) {
            int i = 1;
            list.add(m.group(i));
            i++;
        }
        return list;
    }
    /**
     * 返回单个字符串，若匹配到多个的话就返回第一个，方法与getSubUtil一样
     * @param soap
     * @param rgex
     * @return
     */
    public static String getSubUtilSimple(String soap,String rgex){
        Pattern pattern = Pattern.compile(rgex);
        // 匹配的模式
        Matcher m = pattern.matcher(soap);
        while(m.find()){
            return m.group(1);
        }
        return "";
    }

    public static Map<String,String> addressResolution138(String address){

        String regex="";

        if(CountCharacter.count(address).get("spaceCharacter")==2){

            if(address.contains("香港") ||address.contains("澳门")){
                //System.out.println(address); //to do
                regex="(?<province>[^省]+省|.+行政区)(?<city>\\s)(?<ew>.*)(\\s)(?<isp>.*)";
            }else{
                regex="(?<province>[^省]+省|.+自治区|.+市)(?<city>[^市]+市|.+自治州|.+地区)(\\s)(?<ew>.*)(\\s)(?<isp>.*)";
            }
        }else {
            //((?<province>[^省]+省|.+自治区)|上海|北京|天津|重庆) 切记
            regex="(?<province>[^省]+省|.+自治区|.+市)(?<city>[^市]+市|.+地区)(\\s)(?<isp>.*)";
        }
        Matcher m=Pattern.compile(regex).matcher(address);

        String province=null,city=null,isp=null;
        Map<String,String> row=null;

        while(m.find()){
            row=new LinkedHashMap<String,String>();
            province=m.group("province");
            row.put("province", province==null?"":province.trim());
            city=m.group("city");
            row.put("city", city==null?"":city.trim());
            isp=m.group("isp");
            row.put("isp", isp==null?"":isp.trim());
        }
        return row;
    }

    public static Map<String,String> addressResolutionIP(String address){

        String regex="";
        //((?<province>[^省]+省|.+自治区)|上海|北京|天津|重庆)  少了一个竖杠 |.+
        if(address.contains("北京市")|| address.contains("天津市") || address.contains("上海市") || address.contains("重庆市") || address.contains("香港") || address.contains("澳门")){
            regex="(?<province>[^省]+省|.+自治区|.+市|.+行政区)(?<city>)?(?<isp>.*)?";
        }else{
            regex="(?<province>[^省]+省|.+自治区|.+市)(?<city>[^市]+市|.+地区|.+区)(\\s)(?<isp>.*)";
        }

        Matcher m=Pattern.compile(regex).matcher(address);

        String province=null,city=null,isp=null;

        Map<String,String> row=null;

        while(m.find()){
            row=new LinkedHashMap<String,String>();
            province=m.group("province");
            System.out.println(province);
            row.put("province", province==null?"":province.trim());
            city=m.group("city");
            if(city==null || city.equals("")){
                city=province;
            }
            row.put("city", city==null?"":city.trim());
            isp=m.group("isp");
            row.put("isp", isp==null?"":isp.trim());
        }
        return row;
    }

    public static Map<String,String> addressResolutionChunZhen(String address){
        String regex="";
        if(address.length()>2){
            if(address.contains("省")){
                //普通省市
                if(address.contains("市")){
                    regex="(?<province>[^省]+省|.+自治区|.+市)(?<city>[^市]+市)";
                }else{
                    regex="(?<province>[^省]+省|.+自治区|.+市)(?<city>)";
                }


            }else if(address.contains("北京")|| address.contains("天津") || address.contains("上海") || address.contains("重庆")){

                //直辖市
                regex="(?<province>[^省]+省|.+自治区|.+市)(?<city>)";
            }else if(address.contains("内蒙古")){
                regex="(?<province>([^.{0}$}][^.{0}$}][^.{0}$}]))(?<city>[^市]+市|.+州)";
            } else {
                //新疆****市
                regex="(?<province>([^.{0}$}][^.{0}$}]))(?<city>[^市]+市|.+州)";
            }
        }else {
            //西藏 香港
            regex="(?<province>(^.{0,2}$))(?<city>)";
        }

        Matcher m=Pattern.compile(regex).matcher(address);

        String province=null,city=null;

        Map<String,String> row=null;

        while(m.find()){
            row=new LinkedHashMap<String,String>();
            province=m.group("province");
//            if(province.contains("省")||province.contains("市")){
//                province=province.substring(0,province.length()-1);
//            }
            row.put("province", province==null?"":province.trim());
            city=m.group("city");
//            if(city.contains("市")|| city.contains("区")){
//                city=city.substring(0,city.length()-1);
//            }
            if(address.contains("北京")|| address.contains("天津") || address.contains("上海") || address.contains("重庆")){
                city=province;
            }
            row.put("city", city==null?"":city.trim());
        }
        return row;
    }
}