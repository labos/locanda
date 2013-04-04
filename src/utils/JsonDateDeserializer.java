package utils;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.log4j.Logger;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.DeserializationContext;
import org.codehaus.jackson.map.JsonDeserializer;

public class JsonDateDeserializer extends JsonDeserializer<Date> {
	private static Logger logger = Logger.getLogger(Logger.class);

	 @Override
	    public Date deserialize(JsonParser jsonparser,
	            DeserializationContext deserializationcontext) throws IOException, JsonProcessingException {
	        //SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
	        String date = jsonparser.getText();
	        logger.info("***** BIRTHDATE:*" + date);
	        return new Date(Long.valueOf(date));


	    }
	
	

}
