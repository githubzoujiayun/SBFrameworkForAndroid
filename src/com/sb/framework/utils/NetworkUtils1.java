package com.sb.framework.utils;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.text.TextUtils;
//import ch.boye.httpclientandroidlib.conn.util.InetAddressUtils;

public class NetworkUtils1 {
    public static final int NETWORK_TYPE_MOBILE = ConnectivityManager.TYPE_MOBILE;
    public static final int NETWORK_TYPE_WIFI = ConnectivityManager.TYPE_WIFI;
    public static final String INTERFACE_ETH0 = "eth0";
    public static final String INTERFACE_WLAN0 = "wlan0";
    public static final String INTERFACE_DEFAULT = "default";

    public static boolean isNetworkAvaliable(Context ctx) {
        ConnectivityManager connectivityManager = (ConnectivityManager) ctx
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager != null) {
            final NetworkInfo net = connectivityManager.getActiveNetworkInfo();
            if (net != null && net.isAvailable() && net.isConnected()) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    public static boolean isServiceReachable(Context ctx, int hostAddress) {
        ConnectivityManager connectivityManager = (ConnectivityManager) ctx
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager != null) {
            return connectivityManager.requestRouteToHost(connectivityManager
                    .getActiveNetworkInfo().getType(), hostAddress);
        } else {
            return false;
        }
    }

    public static int getNetworkType(Context con) {
        ConnectivityManager cm = (ConnectivityManager) con
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        if (cm == null)
            return NETWORK_TYPE_MOBILE;
        NetworkInfo netinfo = cm.getActiveNetworkInfo();
        if (netinfo != null && netinfo.isAvailable()) {
            if (netinfo.getType() == ConnectivityManager.TYPE_WIFI) {
                return NETWORK_TYPE_WIFI;
            } else {
                return NETWORK_TYPE_MOBILE;
            }
        }
        return NETWORK_TYPE_MOBILE;
    }

    public static boolean isWapNetwork() {
        return !TextUtils.isEmpty(getProxyHost());
    }

    @SuppressWarnings("deprecation")
    public static String getProxyHost() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD) {
            return System.getProperty("http.proxyHost");
        } else {
            return android.net.Proxy.getDefaultHost();
        }
    }

    @SuppressWarnings("deprecation")
    public static int getProxyPort() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD) {
            return Integer.valueOf(System.getProperty("http.proxyPort"));
        } else {
            return Integer.valueOf(android.net.Proxy.getDefaultHost());
        }
    }

    /**
     * Convert byte array to hex string
     * 
     * @param bytes
     * @return
     */
    public static String bytesToHex(byte[] bytes) {
        StringBuilder sbuf = new StringBuilder();
        for (int idx = 0; idx < bytes.length; idx++) {
            int intVal = bytes[idx] & 0xff;
            if (intVal < 0x10)
                sbuf.append("0");
            sbuf.append(Integer.toHexString(intVal).toUpperCase());
        }
        return sbuf.toString();
    }

    /**
     * Get utf8 byte array.
     * 
     * @param str
     * @return array of NULL if error was found
     */
    public static byte[] getUTF8Bytes(String str) {
        try {
            return str.getBytes("UTF-8");
        } catch (Exception ex) {
            return null;
        }
    }

    /**
     * Load UTF8withBOM or any ansi text file.
     * 
     * @param filename
     * @return
     * @throws java.io.IOException
     */
    public static String loadFileAsString(String filename)
            throws java.io.IOException {
        final int BUFLEN = 1024;
        BufferedInputStream is = new BufferedInputStream(new FileInputStream(
                filename), BUFLEN);
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream(BUFLEN);
            byte[] bytes = new byte[BUFLEN];
            boolean isUTF8 = false;
            int read, count = 0;
            while ((read = is.read(bytes)) != -1) {
                if (count == 0 && bytes[0] == (byte) 0xEF
                        && bytes[1] == (byte) 0xBB && bytes[2] == (byte) 0xBF) {
                    isUTF8 = true;
                    baos.write(bytes, 3, read - 3); // drop UTF8 bom marker
                } else {
                    baos.write(bytes, 0, read);
                }
                count += read;
            }
            return isUTF8 ? new String(baos.toByteArray(), "UTF-8")
                    : new String(baos.toByteArray());
        } finally {
            try {
                is.close();
            } catch (Exception ex) {
            }
        }
    }

    /**
     * Returns MAC address of the given interface name.
     * 
     * @param interfaceName
     *            eth0, wlan0 or NULL=use first interface
     * @return mac address or empty string
     */
    @SuppressLint("NewApi")
	public static String getMACAddress(Context ctx, String interfaceName,
            String defaultMac) {
        String macAddress = null;
        try {
            List<NetworkInterface> interfaces = Collections
                    .list(NetworkInterface.getNetworkInterfaces());
            for (NetworkInterface intf : interfaces) {
                if (interfaceName != null) {
                    if (!intf.getName().equalsIgnoreCase(interfaceName))
                        continue;
                }
                byte[] mac = intf.getHardwareAddress();
                macAddress = convertMacAddress(mac);
                break;
            }
        } catch (Exception ex) {
        } // for now eat exceptions

        if (TextUtils.isEmpty(macAddress)
                && INTERFACE_WLAN0.equals(interfaceName)) {
            return getWifiMacAddress(ctx);
        }
        return macAddress;
        /*
         * try { // this is so Linux hack return
         * loadFileAsString("/sys/class/net/" +interfaceName +
         * "/address").toUpperCase().trim(); } catch (IOException ex) { return
         * null; }
         */
    }

    private static String convertMacAddress(byte[] mac) {
        if (mac == null) {
            return null;
        }
        StringBuilder buf = new StringBuilder();
        for (int idx = 0; idx < mac.length; idx++)
            buf.append(String.format("%02X:", mac[idx]));
        if (buf.length() > 0)
            buf.deleteCharAt(buf.length() - 1);

        // FIXME 为什么有些android获取不到mac地址，或者获取到的mac地址位数不对
        if (buf.length() != 12 && buf.length() != 17) {
            return null;
        }
        return buf.toString();
    }

    /**
     * http://www.gubatron.com/blog/2010/09/19/android-programming-how-to-obtain-the-wifis-corresponding-networkinterface/
     * @param ctx
     * @return
     */
    @SuppressLint("NewApi")
	private static String getWifiMacAddress(Context ctx) {
        WifiManager manager = null;
        try {
            manager = (WifiManager) ctx.getSystemService(Context.WIFI_SERVICE);
            if (manager == null || manager.getConnectionInfo() == null) {
                return null;
            }
            Enumeration<NetworkInterface> interfaces = null;
            // the WiFi network interface will be one of these.
            interfaces = NetworkInterface.getNetworkInterfaces();

            // We'll use the WiFiManager's ConnectionInfo IP address and compare
            // it with the ips of the enumerated NetworkInterfaces to find the
            // WiFi
            // NetworkInterface.

            // Wifi manager gets a ConnectionInfo object that has the ipAdress
            // as an int It's endianness could be different as the one on
            // java.net.InetAddress
            // maybe this varies from device to device, the android API has no
            // documentation on this method.
            int wifiIP = manager.getConnectionInfo().getIpAddress();

            // so I keep the same IP number with the reverse endianness
            int reverseWifiIP = Integer.reverseBytes(wifiIP);

            while (interfaces.hasMoreElements()) {
                NetworkInterface iface = interfaces.nextElement();

                // since each interface could have many InetAddresses...
                Enumeration<InetAddress> inetAddresses = iface
                        .getInetAddresses();
                while (inetAddresses.hasMoreElements()) {
                    InetAddress nextElement = inetAddresses.nextElement();
                    int byteArrayToInt = byteArrayToInt(
                            nextElement.getAddress(), 0);

                    // grab that IP in byte[] form and convert it to int, then
                    // compare it to the IP given by the WifiManager's
                    // ConnectionInfo. We compare
                    // in both endianness to make sure we get it.
                    if (byteArrayToInt == wifiIP
                            || byteArrayToInt == reverseWifiIP) {
                        byte[] mac = iface.getHardwareAddress();
                        return convertMacAddress(mac);
                    }
                }
            }
        } catch (Exception e) {
        }

        return null;
    }

    private static final int byteArrayToInt(byte[] arr, int offset) {
        if (arr == null || arr.length - offset < 4)
            return -1;

        int r0 = (arr[offset] & 0xFF) << 24;
        int r1 = (arr[offset + 1] & 0xFF) << 16;
        int r2 = (arr[offset + 2] & 0xFF) << 8;
        int r3 = arr[offset + 3] & 0xFF;
        return r0 + r1 + r2 + r3;
    }

    public static String getIPv4Address() {
        return getIPAddress(true);
    }

    public static String getIPv6Address() {
        return getIPAddress(false);
    }

    /**
     * Get IP address from first non-localhost interface
     * 
     * @param ipv4
     *            true=return ipv4, false=return ipv6
     * @return address or empty string
     */
    public static String getIPAddress(boolean useIPv4) {
        try {
            List<NetworkInterface> interfaces = Collections
                    .list(NetworkInterface.getNetworkInterfaces());
            for (NetworkInterface intf : interfaces) {
                List<InetAddress> addrs = Collections.list(intf
                        .getInetAddresses());
                for (InetAddress addr : addrs) {
                    if (!addr.isLoopbackAddress()) {
                        String sAddr = addr.getHostAddress().toUpperCase();
                        boolean isIPv4 = SBString.isIPAdress(sAddr);
                        if (useIPv4) {
                            if (isIPv4)
                                return sAddr;
                        } else {
                            if (!isIPv4) {
                                int delim = sAddr.indexOf('%'); // drop ip6 port
                                                                // suffix
                                return delim < 0 ? sAddr : sAddr.substring(0,
                                        delim);
                            }
                        }
                    }
                }
            }
        } catch (Exception ex) {
        } // for now eat exceptions
        return "";
    }

    public static String getLocalIpAddress() {
        try {
            for (Enumeration<NetworkInterface> en = NetworkInterface
                    .getNetworkInterfaces(); en.hasMoreElements();) {
                NetworkInterface intf = en.nextElement();
                for (Enumeration<InetAddress> enumIpAddr = intf
                        .getInetAddresses(); enumIpAddr.hasMoreElements();) {
                    InetAddress inetAddress = enumIpAddr.nextElement();
                    if (!inetAddress.isLoopbackAddress()
                            && inetAddress instanceof Inet4Address) {
                        return inetAddress.getHostAddress();
                    }
                }
            }
        } catch (SocketException ex) {
            ex.printStackTrace();
        }
        return null;
    }
}
