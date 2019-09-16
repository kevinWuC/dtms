
package com.medical.dtms.common.convert;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;

/**
 * 向前端返回时将Long转成字符串
 * @author xuwentao
 * @version $Id: LongJsonSerializer.java, v 0.1 2019年5月15日 上午11:21:10 xuwentao Exp $
 */
public class LongJsonSerializer extends JsonSerializer<Long> {
    @Override
    public void serialize(Long value, JsonGenerator jsonGenerator,
                          SerializerProvider serializerProvider) throws IOException,
            JsonProcessingException {
        String text = (value == null ? null : String.valueOf(value));
        if (text != null) {
            jsonGenerator.writeString(text);
        }
    }
}
