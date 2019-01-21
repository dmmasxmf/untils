package com.dmm.ip.parse;

import com.dmm.request.Address;
import com.dmm.until.jackson.JacksonUtil;
import com.dmm.util.cut.RegexUtil;

import java.io.IOException;
import java.util.Map;

/**
 * @author dmm
 */
public class ChunZhenToAddress {

    /**
     *
     * @param fileName 文件名
     * @param dir 路径名
     * @param ip ip地址
     * @return
     */
    public static Map<String,String> getToMapChunZhen(String fileName,String dir,String ip){

        IPSeeker ipSeeker=new IPSeeker(fileName,dir);

        String country=ipSeeker.getIPLocation(ip).getCountry();
        String area=ipSeeker.getIPLocation(ip).getArea();

        Map<String,String> row=RegexUtil.addressResolutionChunZhen(country);
		if(row==null || row.isEmpty()){
			return null;
		}
        row.put("isp",area);
        return row;
    }

    public static Address getToChunZhen(String fileName, String dir, String ip) throws IOException {

        Map<String,String> map=getToMapChunZhen(fileName,dir,ip);
        if(map==null || map.isEmpty()){
            return null;
        }
        return JacksonUtil.mapToBean(map,Address.class);
    }

//main 方法
//    public static void main(String[] args) throws IOException {
//        //101.248.0.0 47.98.51.82 49.112.0.0 111.126.218.12 110.167.189.230
//        //System.out.println(getToChunZhen("qqwry.dat","F:/ip","49.112.0.0").getCity()+"++++++");
//        //System.out.println(getToChunZhen("qqwry.dat","F:/ip","202.46.48.0").getProvince());
//        System.out.println(getToChunZhen("qqwry.dat","F:/ip","110.167.189.230").getProvince());
//    }
}
