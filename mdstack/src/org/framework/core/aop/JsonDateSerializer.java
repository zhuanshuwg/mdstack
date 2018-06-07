package org.framework.core.aop;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.JsonSerializer;
import org.codehaus.jackson.map.SerializerProvider;
import org.springframework.stereotype.Component;

@Component
public class JsonDateSerializer extends JsonSerializer<Date> {

	@Override
	public void serialize(Date date, JsonGenerator jg, SerializerProvider sp)
			throws IOException, JsonProcessingException {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		String formattedDate = formatter.format(date);

		jg.writeString(formattedDate);
	}

}
