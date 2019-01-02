package com.dmm.string.stat;

import java.util.HashMap;
import java.util.Map;

public class CountCharacter {

    /**中文字符 */
    private static int chCharacter = 0;

    /**英文字符 */
    private static Integer enCharacter = 0;

    /**空格 */
    private static Integer spaceCharacter = 0;

    /**数字 */
    private static Integer numberCharacter = 0;

    /**其他字符 */
    private static Integer otherCharacter = 0;



    /***
     * 统计字符串中中文，英文，数字，空格等字符个数
     * @param str 需要统计的字符串
     */
    public static Map<String,Integer> count(String str) {

        Map<String,Integer> map=new HashMap<>();


        if (null == str || str.equals("")) {
            System.out.println("字符串为空");
            return null;
        }

        for (int i = 0; i < str.length(); i++) {
            char tmp = str.charAt(i);
            if ((tmp >= 'A' && tmp <= 'Z') || (tmp >= 'a' && tmp <= 'z')) {
                enCharacter ++;
            } else if ((tmp >= '0') && (tmp <= '9')) {
                numberCharacter ++;
            } else if (tmp ==' ') {
                spaceCharacter ++;
            } else if (isChinese(tmp)) {
                chCharacter ++;
            } else {
                otherCharacter ++;
            }
        }
//        System.out.println("字符串:" + str + "");
//        System.out.println("中文字符有:" + chCharacter);
//        System.out.println("英文字符有:" + enCharacter);
//        System.out.println("数字有:" + numberCharacter);
//        System.out.println("空格有:" + spaceCharacter);
//        System.out.println("其他字符有:" + otherCharacter);
        map.put("chCharacter",chCharacter);
        map.put("enCharacter",enCharacter);
        map.put("spaceCharacter",spaceCharacter);
        map.put("numberCharacter",numberCharacter);
        map.put("otherCharacter",otherCharacter);

        return map;
    }

    /***
     * 判断字符是否为中文
     * @param ch 需要判断的字符
     * @return 中文返回true，非中文返回false
     */
    private static boolean isChinese(char ch) {
        //获取此字符的UniCodeBlock
        Character.UnicodeBlock ub = Character.UnicodeBlock.of(ch);
        //  GENERAL_PUNCTUATION 判断中文的“号
        //  CJK_SYMBOLS_AND_PUNCTUATION 判断中文的。号
        //  HALFWIDTH_AND_FULLWIDTH_FORMS 判断中文的，号
        if (ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS || ub == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS
                || ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A || ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_B
                || ub == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION || ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS
                || ub == Character.UnicodeBlock.GENERAL_PUNCTUATION) {
            //System.out.println(ch + " 是中文");
            return true;
        }
        return false;
    }

//    public static void main(String[] args) {
//        String str = "北京市北京市 航数宽网 电信";
//        //CountCharacter countCharacter = new CountCharacter();
//
//
//        System.out.println(CountCharacter.count(str));
//        //countCharacter.count(str);
//
//    }

}
