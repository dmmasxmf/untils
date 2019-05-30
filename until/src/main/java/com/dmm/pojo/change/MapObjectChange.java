package com.dmm.pojo.change;

import org.springframework.cglib.beans.BeanMap;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author: Dmm
 * @date: 2019/5/21 10:17
 */
public class MapObjectChange {


    /**
     * 将对象装换为map
     *
     * @param bean
     * @return
     */
    public static <T> Map<String, Object> beanToMap(T bean) {
        Map<String, Object> map = new HashMap<>();
                //Maps.newHashMap();
        if (bean != null) {
            BeanMap beanMap = BeanMap.create(bean);
            for (Object key : beanMap.keySet()) {
                map.put(key + "", beanMap.get(key));
            }
        }
        return map;
    }

    /**
     * 将map装换为javabean对象
     *
     * @param map
     * @param bean
     * @return
     */
    public static <T> T mapToBean(Map<String, Object> map, T bean) {
        BeanMap beanMap = BeanMap.create(bean);
        beanMap.putAll(map);
        return bean;
    }

    /**
     * 将List<T>转换为List<Map<String, Object>>
     *
     * @param objList
     * @return
     */
    public static <T> List<Map<String, Object>> objectsToMaps(List<T> objList) {
        List<Map<String, Object>> list = new ArrayList<>();
                //Lists.newArrayList();

        if (objList != null && objList.size() > 0) {
            Map<String, Object> map = null;
            T bean = null;
            for (int i = 0, size = objList.size(); i < size; i++) {
                bean = objList.get(i);
                map = beanToMap(bean);
                list.add(map);
            }
        }
        return list;
    }

    /**
     * 将List<Map<String,Object>>转换为List<T>
     *
     * @param maps
     * @param clazz
     * @return
     * @throws InstantiationException
     * @throws IllegalAccessException
     */
    public static <T> List<T> mapsToObjects(List<Map<String, Object>> maps, Class<T> clazz)
            throws InstantiationException, IllegalAccessException {
        List<T> list = new ArrayList<>();
        if (maps != null && maps.size() > 0) {
            Map<String, Object> map = null;
            T bean = null;
            for (int i = 0, size = maps.size(); i < size; i++) {
                map = maps.get(i);
                bean = clazz.newInstance();
                mapToBean(map, bean);
                list.add(bean);
            }
        }
        return list;
    }


    public static Object map2Object(Map<String, Object> map, Class<?> clazz) {

        if (map == null) {
            return null;
        }

        Object obj = null;
        try {
            obj = clazz.newInstance();

            Field[] fields = obj.getClass().getDeclaredFields();
            for (Field field : fields) {
                int mod = field.getModifiers();
                if (Modifier.isStatic(mod) || Modifier.isFinal(mod)) {
                    continue;
                }
                String fieldName = field.getName();
                System.out.println(fieldName+"------------");
//                if(!Character.isLowerCase(fieldName.charAt(0))) {
//                    fieldName = (new StringBuilder()).append(Character.toLowerCase(fieldName.charAt(0))).append(fieldName.substring(1)).toString();
//                }
                for (Map.Entry<String,Object> entry:map.entrySet()){

                    String[] strings=entry.getKey().split("_");

//                    if(entry.getKey().equals(attr_name)){
//                        map.put(attr_name,entry.getValue());
//                    }

                    StringBuilder stringBuilder=new StringBuilder();

                    for(int i=0;i<strings.length;i++){
                        stringBuilder.append(strings[i]);
                    }

                    boolean b=stringBuilder.toString().equalsIgnoreCase(fieldName);

                    System.out.println(entry.getKey()+"+++++++++++++");
                    if(b){
                        field.set(obj, map.get(entry.getKey()));
                        System.out.println("---------------------------");
                        break;
                    }

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return obj;
    }

    public static <T> List<T> mapsToObjects2(List<Map<String, Object>> maps, Class<T> clazz)
            throws InstantiationException, IllegalAccessException {

        List<T> list = new ArrayList<>();
        if (maps != null && maps.size() > 0) {
            Map<String, Object> map = null;
            T bean = null;
            for (int i = 0, size = maps.size(); i < size; i++) {
                map = maps.get(i);
                bean = clazz.newInstance();
                map2Object(map, bean.getClass());
                list.add(bean);
            }
        }
        return list;
    }


}
