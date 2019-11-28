package com.miguo.matrix.utils;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.springframework.http.HttpStatus;
import java.io.IOException;

/**
 * @author Hocassian
 */
public class EnumSerializer extends JsonSerializer {

    @Override
    public void serialize(Object object, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        if (object instanceof HttpStatus) {
            HttpStatus httpStatus = (HttpStatus) object;
            jsonGenerator.writeNumber(httpStatus.value());
        } else {
            jsonGenerator.writeString(object.toString());
        }
    }

}
