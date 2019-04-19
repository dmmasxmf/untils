package com.dmm.version.cut;

/**
 * @author: Dmm
 * @date: 2019/4/19 14:26
 */
public class FirmwareVersionCut {


    public static boolean versionJudge(String oldVersion,String newVersion){

        String[] oldVersionS=oldVersion.split("^[A-Za-z]")[1].split("\\.");

        String[] newVersionS=newVersion.split("^[A-Za-z]")[1].split("\\.");

        // 新版本号大于老版本号 true  小于或者等于为false
        boolean versionStatus=false;


        for(int i=0,len=newVersionS.length;i<len;i++){

            int newI=Integer.parseInt(newVersionS[i]);
            int oldI=Integer.parseInt(oldVersionS[i]);

            if(newI>=oldI){

                if(newI==oldI){

                    if(i==len-1){
                        versionStatus=false;

                    }
                    continue;
                }

                versionStatus=true;
            }
            break;
        }

        return versionStatus;
    }

}
