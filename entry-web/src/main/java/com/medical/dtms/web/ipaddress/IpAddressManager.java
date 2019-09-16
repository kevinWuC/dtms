package com.medical.dtms.web.ipaddress;

import com.maxmind.geoip2.DatabaseReader;
import com.maxmind.geoip2.exception.GeoIp2Exception;
import com.maxmind.geoip2.model.CityResponse;
import com.medical.dtms.common.constants.HostConstants;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.net.*;

/**
 * @version： IpAdressManager.java v 1.0, 2019年08月19日 15:44 wuxuelin Exp$
 * @Description 通过 ip 获取城市
 **/
@Service
@Slf4j
public class IpAddressManager {

    /**
     * GeoLite2 数据库
     */
    private static final String DBName = "GeoLite2-City.mmdb";

    private static DatabaseReader reader;

    @Autowired
    private Environment ev;

    /**
     * 初始化服务
     */
    @PostConstruct
    public void initEnv() {
        try {
            String path = ev.getProperty("geolite2.dbdir");
            File file = new File(path, DBName);
            reader = new DatabaseReader.Builder(file).build();
        } catch (IOException e) {
            log.error("IP服务初始化失败", e);
        }
    }

    /**
     * 获取 ip
     */
    public String getUserAddress(HttpServletRequest request) {

//        try {
//            // 遍历网络接口
//            Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
//            while (interfaces.hasMoreElements()) {
//                NetworkInterface anInterface = interfaces.nextElement();
//
//                // 遍历所有接口下的网络 ip
//                Enumeration<InetAddress> inetAddresses = anInterface.getInetAddresses();
//                while (inetAddresses.hasMoreElements()) {
//                    InetAddress address = inetAddresses.nextElement();
//                    // 本机
//                    if (!address.isLoopbackAddress()) {
//                        String addr = request.getRemoteAddr();
//                        log.error("addr:" + addr);
//                        return HostConstants.LOCAL_HOST_Name;
//                    }
//                    // 局域网
//                    if (address.isSiteLocalAddress()) {
//                        return HostConstants.PART_HOST_NAME;
//                    }
//
//                    // 普通 ip
//                    if (address.getHostAddress().indexOf(":") == -1) {
//                        if (address != null && address instanceof Inet4Address) {
//                            return address.toString();
//                        }
//                    }
//                }
//            }
//        } catch (Exception e) {
//            log.error("获取网络接口/ip失败", e);
//            return null;
//        }

        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteHost();
        }

        System.out.println("-- - - - - - - host:" + request.getRemoteHost());

        return ip;
    }


    /**
     * 根据 ip 获取城市
     */
    public String getCityAddress(HttpServletRequest request) {
        try {
            String address = request.getRemoteHost();
            if (address.startsWith("127.")) {
                return HostConstants.LOCAL_HOST_Name;
            }
            if (address.startsWith("10.") | address.startsWith("192.") | address.startsWith("172.")) {
                return HostConstants.PART_HOST_NAME;
            }

            CityResponse response = reader.city(InetAddress.getByName(address));
            // 获取国家
            String country = response.getCountry().getNames().get("zh-CN");
            // 获取省份
            String province = response.getMostSpecificSubdivision().getNames().get("zh-CN");
            // 获取城市
            String city = response.getCity().getNames().get("zh-CN");
            StringBuilder sb = new StringBuilder();
            if (StringUtils.isNotBlank(country)) {
                sb.append(country);
            }
            if (StringUtils.isNotBlank(province)) {
                sb.append(province);
            }
            if (StringUtils.isNotBlank(city)) {
                sb.append(city);
            }

            return sb.toString();
        } catch (IOException e) {
            log.error("根据 ip 获取城市失败", e);
        } catch (GeoIp2Exception e) {
            log.error("GeoIp2 异常", e);
        }
        return null;
    }


}
