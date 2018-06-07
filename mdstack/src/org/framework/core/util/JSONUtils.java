package org.framework.core.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;



/**
 * 
 * @ProjectNmae：util.
 * @ClassName：JSONUtils
 * @Description： (JSON数据格式转换工具类)
 * @author：wangguan
 * @editor：
 * @editTime：
 * @editDescription：
 * @version 1.0.0
 */
public class JSONUtils {

    /**
     * 
     * @jsonStringToObject(Json字符串转换为java对象).
     * 
     * @author： chl
     * @createTime：2013-5-16 下午2:22:36
     * @param type
     *            java对象类型 如:Object.class
     * @param jsonStr
     *            json字符串
     * @throws JsonParseException
     * @throws JsonMappingException
     * @throws IOException
     * @return T
     */
    public static <T> T jsonStringToObject(Class<T> type, String jsonStr) throws JsonParseException, JsonMappingException, IOException{
        return new ObjectMapper().readValue(jsonStr, type);
    }

    /**
     * 
     * @objectToJSONString(java对象转换为Json字符串).
     * @author： chl
     * @createTime：2013-5-17 上午10:12:17
     * @param object
     *            java对象 如：javaBean对象、Map集合、List集合
     * @return String
     * @throws IOException 
     */
    public static <T> String objectToJSONString(T object) throws IOException {
        return new ObjectMapper().writeValueAsString(object);
    }

    /**
     * 
     * jsonStringsToObjectArray(将多个同类型json的字符串转换为java对象数组).
     * 
     * @author： chl
     * @createTime：2013-5-17 上午11:09:04
     * @param type
     *            java对象数组类型 如：Object[].class
     * @param jsonStr
     *            多个同类型json的字符串 如："[jsonString1,jsonString2,...]"
     * @throws JsonParseException
     * @throws JsonMappingException
     * @throws IOException
     * @return T[]
     */
    public static <T> T[] jsonStringsToObjectArray(TypeReference<T[]> type, String jsonStr) throws JsonParseException, JsonMappingException, IOException {
        return new ObjectMapper().readValue(jsonStr, type);
    }

    /**
     * 
     * @jsonStringsToObjectList(将多个同类型json的字符串转换为java对象List).
     * @author： chl
     * @createTime：2013-5-20 上午11:30:38
     * @param type
     *            java对象数组类型 如：Object[].class
     * @param jsonStr
     *            多个同类型json的字符串 如："[jsonString1,jsonString2,...]"
     * @throws JsonParseException
     * @throws JsonMappingException
     * @throws IOException
     * @return List<T>
     */
    public static <T> List<T> jsonStringsToObjectList(TypeReference<List<T>> type, String jsonStr) throws JsonParseException, JsonMappingException, IOException {
        List<T> list = new ArrayList<T>(0);
        list = new ObjectMapper().readValue(jsonStr, type);
        return list;
    }

}
