package com.medical.dtms.logclient.http;


import com.alibaba.fastjson.JSON;
import com.google.gson.Gson;
import com.medical.dtms.logclient.config.ObjectLoggerConfig;
import com.medical.dtms.logclient.model.OperationModel;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@Slf4j
public class HttpUtil {

    @Autowired(required = false)
    private ObjectLoggerConfig objectLoggerConfig;

    public synchronized void sendLog(OperationModel model) {
        try {
            List<NameValuePair> paramsList = new ArrayList<>();
            paramsList.add(new BasicNameValuePair("logJsonString", new Gson().toJson(model)));
            String ip = model.getExtraWords();
            String port = objectLoggerConfig.getServerAddress().substring(objectLoggerConfig.getServerAddress().lastIndexOf(":")).replace(":", "");
            sendPost("http://" + ip + ":" + port + "/syslog/addSysLog", paramsList);
        } catch (Exception ex) {
            log.error("sendLog error!", ex);
        }
    }

    private synchronized String sendPost(String url, List<NameValuePair> nameValuePairList) throws Exception {
        CloseableHttpResponse response = null;
        try (CloseableHttpClient client = HttpClients.createDefault()) {
            HttpPost post = new HttpPost(url);
            StringEntity entity = new UrlEncodedFormEntity(nameValuePairList, "UTF-8");
            post.setEntity(entity);
            post.setHeader(new BasicHeader("Content-Type", "application/x-www-form-urlencoded; charset=utf-8"));
            post.setHeader(new BasicHeader("Accept", "text/plain;charset=utf-8"));
            post.setHeader(new BasicHeader("menuUrl", "/syslog/addSysLog"));
            response = client.execute(post);
            int statusCode = response.getStatusLine().getStatusCode();
            if (200 == statusCode) {
                return EntityUtils.toString(response.getEntity(), "UTF-8");
            }
        } catch (Exception e) {
            log.error("send post error", e);
        } finally {
            if (response != null) {
                response.close();
            }
        }
        return null;
    }
}
