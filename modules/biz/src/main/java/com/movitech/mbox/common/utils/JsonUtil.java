package com.movitech.mbox.common.utils;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import net.sf.json.JSONObject;

/**
 * @author 作者 felix.jin:
 * @version 创建时间：2017年8月18日 下午3:12:13 类说明
 */
public class JsonUtil {

	public static String toJson(Object obj) {
		return JSONObject.fromObject(obj).toString();
	}

	@SuppressWarnings("rawtypes")
	public static Map<String, String> parseToMap(String json) {
		Map<String, String> map = new HashMap<String, String>();
		JSONObject jsonMap = JSONObject.fromObject(json);
		Iterator it = jsonMap.keys();
		while (it.hasNext()) {
			String key = (String) it.next();
			String val = (String) jsonMap.get(key);
			map.put(key, val);
		}
		return map;
	}

	

}
