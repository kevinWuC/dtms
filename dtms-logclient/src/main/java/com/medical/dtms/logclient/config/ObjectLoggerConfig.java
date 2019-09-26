package com.medical.dtms.logclient.config;

import org.springframework.stereotype.Component;

@Component
public class ObjectLoggerConfig {
    /**
     * 业务模块名
     */
//    @Value("${logClient.moduleName}")
    private String businessAppName = "entry-web";
    /**
     * 业务模块所在的服务器地址 和 端口号
     */
//    @Value("${logClient.serverAddr}")
    private String serverAddress = "127.0.0.1:9009";
    /**
     * 是否自动比较属性值的变化
     */
//    @Value("${logClient.autoLogAttr}")
    private String autoLogAttributes = "false";

    public String getBusinessAppName() {
        return businessAppName;
    }

    public void setBusinessAppName(String businessAppName) {
        this.businessAppName = businessAppName;
    }

    public String getServerAddress() {
        return serverAddress;
    }

    public void setServerAddress(String serverAddress) {
        this.serverAddress = serverAddress;
    }

    public String getAutoLogAttributes() {
        return autoLogAttributes;
    }

    public void setAutoLogAttributes(String autoLogAttributes) {
        this.autoLogAttributes = autoLogAttributes;
    }
}
