package com.medical.dtms.logclient.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Data
public class ObjectLoggerConfig {
    /**
     * 业务模块名
     */
    @Value("${yeecode.objectLogger.businessAppName}")
    private String businessAppName;
    /**
     * 业务模块所在的服务器地址 和 端口号
     */
    @Value("${yeecode.objectLogger.serverAddress}")
    private String serverAddress;
    /**
     * 是否自动比较属性值的变化
     */
    @Value("${yeecode.objectLogger.autoLogAttributes}")
    private String autoLogAttributes;
}
