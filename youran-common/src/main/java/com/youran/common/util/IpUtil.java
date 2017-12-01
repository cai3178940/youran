package com.youran.common.util;

import javax.servlet.http.HttpServletRequest;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Title: IP工具
 * Description:
 * Author: cbb
 * Create Time:2017/5/20 12:59
 */
public class IpUtil {

    /**
     * 获取客户端ip
     * @param request request
     * @return
     */
    public static String getIpAddr(HttpServletRequest request) {

        String ip = request.getHeader("x-forwarded-for");

        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }

        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }

        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }

        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }

        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }

        return ip;

    }

    /**
     * 是否是ipv4
     * @param ipAddress
     * @return
     */
    public static boolean isIpv4(String ipAddress) {

        String ip = "^(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|[1-9])\\."
                + "(00?\\d|1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)\\."
                + "(00?\\d|1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)\\."
                + "(00?\\d|1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)$";

        Pattern pattern = Pattern.compile(ip);
        Matcher matcher = pattern.matcher(ipAddress);
        return matcher.matches();

    }

    /**
     * 获取本地ip
     *
     * @return
     */
    public static String getLocalIp() {
        String localip = null;
        String netip = null;
        try {
            Enumeration netInterfaces = NetworkInterface.getNetworkInterfaces();
            InetAddress ip = null;
            boolean finded = false;
            do {
                NetworkInterface ni = (NetworkInterface) netInterfaces.nextElement();
                Enumeration address = ni.getInetAddresses();
                while (address.hasMoreElements()) {
                    ip = (InetAddress) address.nextElement();

                    if ((!ip.isSiteLocalAddress()) && (!ip.isLoopbackAddress()) &&
                            (ip.getHostAddress().indexOf(":") == -1)) {
                        netip = ip.getHostAddress();
                        finded = true;
                        break;
                    }
                    if ((ip.isSiteLocalAddress()) &&
                            (!ip.isLoopbackAddress()) &&
                            (ip.getHostAddress().indexOf(":") == -1))
                        localip = ip.getHostAddress();
                }
                if (!netInterfaces.hasMoreElements()) break;
            } while (!finded);
        } catch (SocketException e) {
            e.printStackTrace();
        }
        if ((netip != null) && (!"".equals(netip))) {
            return netip;
        }
        return localip;
    }
}
