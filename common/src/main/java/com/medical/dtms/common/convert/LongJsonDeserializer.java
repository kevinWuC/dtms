
package com.medical.dtms.common.convert;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * 将接收的前端字符串类型转换成Long类型
 * @author xuwentao
 * @version $Id: LongJsonDeserializer.java, v 0.1 2019年5月15日 上午11:22:37 xuwentao Exp $
 */
public class LongJsonDeserializer extends JsonDeserializer<Long> {

    private static final Logger logger = LoggerFactory.getLogger(LongJsonDeserializer.class);

    @Override
    public Long deserialize(JsonParser jsonParser,
                            DeserializationContext deserializationContext) throws IOException {
        String value = jsonParser.getText();
        try {
            return value == null ? null : Long.parseLong(value);
        } catch (NumberFormatException e) {
            logger.error("数据转换异常", e);
            return null;
        }
    }
}
