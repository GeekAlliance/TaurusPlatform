package com.geekalliance.taurus.toolkit.utils;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.net.*;
import java.util.Enumeration;
import java.util.Set;
import java.util.TreeSet;

/**
 * @author maxuqiang
 */
@Data
@Slf4j
public class InetUtils {
    public final static String INVALID_ADDRESS = "0.0.0.0";

    public static boolean hasBindIpAddress(String ip) {
        if (StringUtils.isNotBlank(ip)) {
            Set<String> allIps = new TreeSet<>();
            try {
                for (Enumeration<NetworkInterface> nics = NetworkInterface
                        .getNetworkInterfaces(); nics.hasMoreElements(); ) {
                    NetworkInterface ifc = nics.nextElement();
                    if (ifc.isUp()) {
                        log.debug("testing interface: " + ifc.getDisplayName());
                        for (Enumeration<InetAddress> addrs = ifc.getInetAddresses(); addrs.hasMoreElements(); ) {
                            InetAddress address = addrs.nextElement();
                            if (address instanceof Inet4Address) {
                                allIps.add(address.getHostAddress());
                            }
                        }
                    }
                }
            } catch (SocketException e) {
                log.error("has bind ip address {} error {}", ip, e.getMessage());
                return false;
            }
            return allIps.contains(ip);
        } else {
            throw new RuntimeException("has bind ip not null");
        }
    }

    public static boolean testSocketConnect(String ip, int port) {
        Socket s = new Socket();
        SocketAddress add = new InetSocketAddress(ip, port);
        try {
            s.connect(add, 3000);
            return true;
        } catch (IOException e) {
            return false;
        } finally {
            try {
                s.close();
            } catch (IOException e) {
            }
        }
    }

}
