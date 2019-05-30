package com.dmm.pojo.change;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author: Dmm
 * @date: 2019/5/20 17:50
 *
 * MAP 转换
 * agent_name :ddd
 * 其他的字段没有
 */
public class MapChange {

    public static Map<String,Object> change(Map<String,Object> objectMap, String attrNames){

        String[] attrs = attrNames.split(",");

        Map<String,Object> map=new HashMap<>();

        //遍历所需的字段
        for(String attr_name: attrs){

            for (Map.Entry<String,Object> entry:objectMap.entrySet()){

                if(entry.getKey().equals(attr_name)){
                    map.put(attr_name,entry.getValue());
                }
            }

        }
        return map;
    }

    public static List<Map<String,Object>> change(List<Map<String,Object>> objectMap, String attrNames){

        List<Map<String,Object>> list=new ArrayList<>();

        for (Map<String,Object> map:objectMap){

            Map<String,Object> map1=change(map,attrNames);

            list.add(map1);
        }
        return list;
    }
}
