
package com.medical.dtms.common.convert;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.DecimalFormat;

/**
 * Double转String  保留两位小数
 * 
 * @author shenqifeng
 * @version $Id: DoubleJsonDeserializer.java, v 0.1 2019年7月24日 下午8:07:19 BYHZ Exp $
 */

public class DoubleJsonDeserializer extends JsonSerializer<Double> {
    private static final DecimalFormat df = new DecimalFormat("0.00");

    @Override
    public void serialize(Double value, JsonGenerator gen,
                          SerializerProvider serializers) throws IOException {
        if (value != null) {
            BigDecimal bigDecimal = new BigDecimal(value.toString());
            //参考该方法 第二个参数是几就保留几位小数 第三个参数 参考 RoundingMode.java
            gen.writeString(df.format(value));
        }
    }

}
