package com.dmm.pojo.change;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * @author: Dmm
 * @date: 2019/5/20 17:51
 *
 * Object 转换
 * agentName：ddd
 * agentId:null
 */
public class ObjectChange {

    /**
     * 描述: - 解决Mybatis逆向工程不能查特定字段的问题
     *      - 去掉不需要的字段的值
     * 返回: 新的pojo，只含要求字段
     */
    //返回有特定字段的类(SysUser)。输入有全部参数的实例(sysUser)，和需要的属性名字"name"

    public static Object convertToPojoByAddAttr(Object old_pojo, String attrNames) throws Exception {

        Class clazz = old_pojo.getClass();
        //创建新pojo //取得无参构造函数
        Constructor c = clazz.getConstructor(  new  Class[ 0 ]);
        //创建对象
        Object new_pojo =  c.newInstance(new Object[]{});

        String[] attrs = attrNames.split(",");
        //attr是每一个字段的名字
        for(String attr_name: attrs){
            //name变getName
            String methodName = toMethodName("get",attr_name);
            //get方法
            Method method = clazz.getMethod(methodName);
            //得到属性的值
            Object attr_value = method.invoke(old_pojo);
            //属性参数类型
            Class<?> returnType = method.getReturnType();

            //获得set方法 //name变setName
            methodName = toMethodName("set",attr_name);
            //set方法
            method = clazz.getMethod(methodName,returnType);

            //执行set方法给新pojo注值
            method.invoke(new_pojo,attr_value);

        }

        return new_pojo;

    }

    /**
     *
     * 同上,只是针对查询结果为List的情况
     */
    public static List<?> convertToPojoByAddAttr(List<?> old_pojo_list, String attrNames) throws Exception{

        List<Object> new_pojo_list = new ArrayList<>();
        for(Object old_pojo: old_pojo_list){
            Object new_pojo = convertToPojoByAddAttr(old_pojo,attrNames);
            new_pojo_list.add(new_pojo);
        }
        return new_pojo_list;
    }

    /**
     * name 变成 getName/setName   toMethodName("set","name");
     */

    private static String toMethodName(String method,String attr){

        char first_char = Character.toUpperCase(attr.charAt(0));
        String rest_char = attr.substring(1);
        StringBuilder stringBuilder = new StringBuilder().append(method).append(first_char).append(rest_char);

        return stringBuilder.toString();
    }

}
