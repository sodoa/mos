package request;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;

public class JSONUtils {
	
	public static SerializerFeature[] features = {
			SerializerFeature.WriteMapNullValue,
			SerializerFeature.WriteEnumUsingToString,
			SerializerFeature.WriteNullListAsEmpty,
			SerializerFeature.WriteNullStringAsEmpty,
			SerializerFeature.WriteTabAsSpecial };

	public static <T> T parseObject(String text, Class<T> pojoClass) {
		return JSON.parseObject(text, pojoClass);
	}
	
	public static JSONObject parseObject(String text) {
		return JSON.parseObject(text);
	}
	
	public static JSONArray parseArray(String text) {
		return JSON.parseArray(text);
	}

	public static String toJSONString(Object pojo) {
		return JSON.toJSONString(pojo, features);
	}

	public static final Object parse(String text) {
		return JSON.parse(text);
	}
	
	
}
