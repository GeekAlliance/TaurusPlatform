package com.geekalliance.taurus.toolkit.utils;

/**
 * IP地址转换工具
 *
 * @author geekeeper
 * @version V1.0.0.0
 * @date 2021-01-13 20:43
 */
public class IpTransformUtils {

    /**
     * 数字转IP字符串
     * @param ip 数字IP
     * @return IP字符串
     */
    public static String ipTransferToString(Long ip) {
        return ((ip >> 24) & 0xff) + "." + ((ip >> 16) & 0xff) + "." + ((ip >> 8) & 0xff) + "." + (ip & 0xff);
    }

    /**
     * IP字符串转数字
     *
     * @param ip IP字符串
     * @return 数字IP
     */
    public static Long ipTransferToLong(String ip) {
        String[] split = ip.split("\\.");
        return ((Long.parseLong(split[0]) << 24) + (Long.parseLong(split[1]) << 16) + (Long.parseLong(split[2]) << 8) + (Long.parseLong(split[3])));
    }
}
