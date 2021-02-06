package com.merlin.patient.util;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonUtil {

	private final static Logger LOGGER = LoggerFactory.getLogger(JsonUtil.class);

	private final static ObjectMapper mapper = new ObjectMapper();

	public static String toJson(Object object) {
		String json = null;
		try {
			json = mapper.writeValueAsString(object);
		} catch (JsonProcessingException e) {
			LOGGER.error("Error while converting object to json string :" + e.getMessage(), e);
		}
		return json;
	}

	public static <T> T fromJson(String jsonString, Class<T> object) {
		try {
			return mapper.readValue(jsonString, object);
		} catch (JsonParseException e) {
			LOGGER.error("Error while converting json to object :" + e.getMessage(), e);
		} catch (JsonMappingException e) {
			LOGGER.error("Error while converting json to object :" + e.getMessage(), e);
		} catch (IOException e) {
			LOGGER.error("Error while converting json to object :" + e.getMessage(), e);
		}
		return null;
	}

}
