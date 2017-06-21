package hr;

import java.io.File;

import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonParser {

	public Input parseFile(String fileName) {
		ObjectMapper mapper = new ObjectMapper();
		try {
			Input input = mapper.readValue(new File(fileName), Input.class);
			return input;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			throw new RuntimeException("Error in json", e);
		}
	}
	
	public <T> T parseString(String s, Class<T> klass) {
		ObjectMapper mapper = new ObjectMapper();
		try {
			T t = mapper.readValue(s, klass);
			return t;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			throw new RuntimeException("Error in json", e);
		}
	}

	public String getJson(Object o) {
		ObjectMapper mapper = new ObjectMapper();
		try {
			String res = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(o);
			return res;
		} catch (Exception e) {
			throw new RuntimeException("Error in json", e);
		}
	}

}
