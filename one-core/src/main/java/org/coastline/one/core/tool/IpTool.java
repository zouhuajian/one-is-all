package org.coastline.one.core.tool;

/**
 * @author Jay.H.Zou
 * @date 2021/8/27
 */
public class IpTool {

    private IpTool() {
    }

    /**
     * ip => long
     * @param ip
     * @return
     */
    public static long ipToLong(String ip) {
        String[] segments = ip.split("\\.");
        return (Long.parseLong(segments[0]) << 24) + (Long.parseLong(segments[1]) << 16)
                + (Long.parseLong(segments[2]) << 8) + Long.parseLong(segments[3]);
    }

    /**
     * long => ip
     * @param ipLong
     * @return
     */
    public static String  longToIp(long ipLong) {
        return (ipLong >>> 24) + "." +
                ((ipLong >>> 16) & 0xFF) + "." +
                ((ipLong >>> 8) & 0xFF) + "." +
                (ipLong & 0xFF);
    }

}
