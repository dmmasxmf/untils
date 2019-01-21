package com.dmm.until.jackson;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.StringWriter;
import java.util.Map;

/**
 * jackson 的工具类
 */

public class JacksonUtil {

    /**
     * 转化器
     */

    private static ObjectMapper mapper = new ObjectMapper();
    /**
     *对象转json
     */

    public static String BeanToJson(Object obj) throws IOException {
        StringWriter sw = new StringWriter();
        JsonGenerator gen = new JsonFactory().createGenerator(sw);
        mapper.writeValue(gen, obj);
        gen.close();
        return sw.toString();
    }
    /**
     * json转对象
     */

    public static <T> T JsonToBean(String jsonStr, Class<T> objClass) throws JsonParseException, JsonMappingException, IOException {

        return mapper.readValue(jsonStr, objClass);
    }
    /**
     * 类型转化
     */

    public static JavaType getCollectionType(Class<?> collectionClass, Class<?>... elementClasses) {
        return mapper.getTypeFactory().constructParametricType(collectionClass, elementClasses);
    }

    /**
     * json转对象 复杂类型
     */

    public static <T> T JsonToBean(String jsonStr, JavaType javaType) throws JsonParseException, JsonMappingException, IOException {

        return mapper.readValue(jsonStr, javaType);
    }

    /**
     * map转对象
     */
    public static <T> T mapToBean(Map map,Class<T> objClass) throws IOException {

        String s=mapper.writeValueAsString(map);

        return JsonToBean(s,objClass);
    }

}
