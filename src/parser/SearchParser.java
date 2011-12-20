package parser;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

public class SearchParser<T> {
	private Class<T> clazz;
	
	public SearchParser(Class<T> model){
		this.clazz = model;
		
	}
	
	public Map<String, String> parse(String term){
		Map<String, String> ret = null;
		Field[] fields = null;
		String key = null;
		String value;
		int start = 0;
		int end = 0;
		
		ret = new HashMap<String, String>();
		
		fields = this.getClazz().getDeclaredFields();
		for(Integer i=0; i<fields.length;i++){
			key = fields[i].getName();			
			//System.out.println("key:" + key);
			start = term.indexOf(key + ":(");
			//System.out.println("start:" + start);
			if(start>-1){
				end = term.indexOf(")", start);
				if(end>-1){
					//System.out.println("end: " + end);
					value = term.substring(start + key.length() + ":(".length(), end);
					
					value = value.trim();
					if(value.length()>0){
						ret.put(key, value);
					}	
					
				}
										
			}		
		}
		return ret;
	}

	public Class<T> getClazz() {
		return clazz;
	}

	public void setClazz(Class<T> clazz) {
		this.clazz = clazz;
	}
}
