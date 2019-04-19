package com.dmm.version.cut;

/**
 * @author: Dmm
 * @date: 2019/4/17 19:26
 */
public class ApiVersionCut {


    /**
     * 若请求版本大于当前最大版本，返回true
     * @param newVersion
     * @param oldVersion
     * @return
     */
    public static boolean versionCutUtil(String newVersion,String oldVersion){

        String[] newArr=newVersion.split("\\.");
        String[] oldArr=oldVersion.split("\\.");

        boolean versionStatus=false;

        for (int i=0;i<newArr.length;i++){

            Integer newI=Integer.valueOf(newArr[i]);

            Integer oldI=Integer.valueOf(oldArr[i]);

            if(newI>oldI){
                versionStatus=true;
                break;
            }
        }

        return versionStatus;
    }

}
