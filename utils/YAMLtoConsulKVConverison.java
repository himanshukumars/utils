package com.imaginea.appscore;

import java.io.FileReader;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.commons.codec.binary.Base64;
import org.json.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import com.github.wnameless.json.flattener.JsonFlattener;

public class YAMLtoConsulKVConverison {

	public static void main(String[] args) {
		JSONParser parser = new JSONParser();
		try {

			JSONObject jsonObject = (JSONObject) parser.parse(new FileReader("test"));
			JSONArray props = new JSONArray();

			String basePath = "/config/app/";

			Map<String, Object> flattenedJsonMap = JsonFlattener.flattenAsMap(jsonObject.toString());

			flattenedJsonMap.forEach((k, v) -> {

				String value = "";
				String key;

				HashMap<String, Object> kvMap = new LinkedHashMap<>();

				key = basePath + k.replace("[\\\"", ".").replace("\\\"]", "").replace(".", "/");

				if (v != null) {
					value = new String(Base64.encodeBase64(v.toString().getBytes()));
					value = value.replace("\"", "");
				}

				kvMap.put("key", key);
				kvMap.put("value", value);

				props.put(kvMap);

			});

			props.forEach((l) -> System.out.println(l));

		} catch (Exception e) {

		}
	}
}
