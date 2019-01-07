package com.qishouqing.frame.utils;

import android.Manifest;
import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Debug;
import android.os.Environment;
import android.os.StatFs;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.WindowManager;

import com.qishouqing.frame.base.QApp;
import com.qishouqing.frame.utils.file.FileUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Method;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.List;
import java.util.UUID;


/**
 * system utils.
 */
public class SystemUtils {

    /**
     * 检测网络是否可用
     *
     * @param context
     * @return
     */
    public static boolean isNetWorkConnected(Context context) {
        if (context != null) {
            ConnectivityManager mConnectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
            if (mNetworkInfo != null) {
                return mNetworkInfo.isAvailable();
            }
        }

        return false;
    }

    /**
     * 手机是否root
     *
     * @return
     */
    public static boolean isRoot() {
        boolean bool = false;
        try {
            bool = (!new File("/system/bin/su").exists()) || (!new File("/system/xbin/su").exists());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bool;
    }

    public static int getVersionCode() {
        int ret = 0;
        Context context = QApp.getInstance();
        if (context != null) {
            try {
                String packageName = SystemUtils.getPackageName(context);
//                PackageInfo packageInfo = context.getPackageManager().getPackageInfo("cn.com.dareway.mhsc", PackageManager.GET_CONFIGURATIONS);
                if (packageName == null) {
                    packageName = "cn.com.dareway.mhsc";
                }
                PackageInfo packageInfo = context.getPackageManager().getPackageInfo(packageName, PackageManager.GET_CONFIGURATIONS);
                ret = packageInfo.versionCode;
            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
            }
        }
        return ret;
    }

    public static String getVersionName() {
        String ret = null;
        Context context = QApp.getInstance();
        if (context != null) {
            try {
                String packageName = SystemUtils.getPackageName(context);
                if (packageName == null) {
                    packageName = "cn.com.dareway.mhsc";
                }
//                PackageInfo packageInfo = context.getPackageManager().getPackageInfo("cn.com.dareway.mhsc", PackageManager.GET_CONFIGURATIONS);
                PackageInfo packageInfo = context.getPackageManager().getPackageInfo(packageName, PackageManager.GET_CONFIGURATIONS);
                ret = packageInfo.versionName;
            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
            }
        }
        return ret;
    }

    public static String getVersionName(String packageName) {
        String ret = null;
        Context context = QApp.getInstance();
        if (context != null) {
            try {
                PackageInfo packageInfo = context.getPackageManager().getPackageInfo(packageName, PackageManager.GET_CONFIGURATIONS);
                ret = packageInfo.versionName;
            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
            }
        }
        return ret;
    }

    /**
     * 获取android系统版本号
     */
    public static String getOSVersion() {
        String release = android.os.Build.VERSION.RELEASE; // android系统版本号
        release = "android" + release;
        return release;
    }

    public static String getosVersion() {
        return android.os.Build.VERSION.RELEASE;
    }

    /**
     * 获得android系统sdk版本号
     */
    public static String getOSVersionSDK() {
        return android.os.Build.VERSION.SDK;
    }

    /**
     * 获得android系统sdk版本号
     */
    public static int getOSVersionSDKINT() {
        return android.os.Build.VERSION.SDK_INT;
    }

    /**
     * 获取手机型号
     */
    public static String getDeviceModel() {
        return android.os.Build.MODEL;
    }

    /**
     * 获取设备的IMEI
     */
    public static String getIMEI() {
        Context context = QApp.getInstance();
        if (null == context) {
            return "";
        }
        String imei = "N888888";
        try {
            if (ActivityCompat.checkSelfPermission(QApp.getInstance(), Manifest.permission.READ_SMS) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(QApp.getInstance(), Manifest.permission.READ_PHONE_NUMBERS) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(QApp.getInstance(), Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
                return null;
            }
            TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            imei = tm.getDeviceId();
            if (imei==null){
                //android.provider.Settings;
                imei= Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
            }
        } catch (Exception e) {
            LogUtils.e(e);
        }
        return imei;
    }

    /**
     * 检测手机是否已插入SIM卡
     */
    public static boolean isCheckSimCardAvailable() {
        Context context = QApp.getInstance();
        if (null == context) {
            return false;
        }
        final TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        return tm.getSimState() == TelephonyManager.SIM_STATE_READY;
    }

    /**
     * sim卡是否可读
     */
    public static boolean isCanUseSim() {
        Context context = QApp.getInstance();
        if (null == context) {
            return false;
        }
        try {
            TelephonyManager mgr = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            return TelephonyManager.SIM_STATE_READY == mgr.getSimState();
        } catch (Exception e) {
            LogUtils.e(e);
        }
        return false;
    }

    /**
     * 取得当前sim手机卡的imsi
     */
    public static String getIMSI() {
        Context context = QApp.getInstance();
        if (null == context) {
            return null;
        }
        String imsi = null;
        try {
            if (ActivityCompat.checkSelfPermission(QApp.getInstance(), Manifest.permission.READ_SMS) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(QApp.getInstance(), Manifest.permission.READ_PHONE_NUMBERS) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(QApp.getInstance(), Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
                return null;
            }
            TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            imsi = tm.getSubscriberId();
        } catch (Exception e) {
            LogUtils.e(e);
        }
        return imsi;
    }

    /**
     * 返回本地手机号码，这个号码不一定能获取到
     */
    public static String getNativePhoneNumber() {
        Context context = QApp.getInstance();
        if (null == context) {
            return null;
        }
        TelephonyManager telephonyManager;
        telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        String NativePhoneNumber = null;

        if (ActivityCompat.checkSelfPermission(QApp.getInstance(), Manifest.permission.READ_SMS) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(QApp.getInstance(), Manifest.permission.READ_PHONE_NUMBERS) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(QApp.getInstance(), Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            return null;
        }
        NativePhoneNumber = telephonyManager.getLine1Number();
        return NativePhoneNumber;
    }

    /**
     * 返回手机服务商名字
     */
    public static String getProvidersName() {
        String ProvidersName = null;
        // 返回唯一的用户ID;就是这张卡的编号神马的
        String IMSI = getIMSI();
        // IMSI号前面3位460是国家，紧接着后面2位00 02是中国移动，01是中国联通，03是中国电信。
        if (IMSI.startsWith("46000") || IMSI.startsWith("46002")) {
            ProvidersName = "中国移动";
        } else if (IMSI.startsWith("46001")) {
            ProvidersName = "中国联通";
        } else if (IMSI.startsWith("46003")) {
            ProvidersName = "中国电信";
        } else {
            ProvidersName = "其他服务商:" + IMSI;
        }
        return ProvidersName;
    }

    /**
     * 获取当前设备的SN
     */
    public static String getSimSN() {
        Context context = QApp.getInstance();
        if (null == context) {
            return null;
        }
        String simSN = null;
        try {
            if (ActivityCompat.checkSelfPermission(QApp.getInstance(), Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
                return null;
            }
            TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            simSN = tm.getSimSerialNumber();
        } catch (Exception e) {
            LogUtils.e(e);
        }
        return simSN;
    }

    /**
     * 获取当前设备的MAC地址
     */
    public static String getMacAddress() {
        Application context = QApp.getInstance();
        if (null == context) {
            return "";
        }
        String mac = "";
        try {
            WifiManager wm = (WifiManager) context.getApplicationContext().getSystemService(Context.WIFI_SERVICE);
            WifiInfo info = wm.getConnectionInfo();
            mac = info.getMacAddress();
        } catch (Exception e) {
            LogUtils.e(e);
        }
        return mac;
    }

    /**
     * 获得设备ip地址
     */
    public static String getLocalAddress() {
        try {
            Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces();
            while (en.hasMoreElements()) {
                NetworkInterface intf = en.nextElement();
                Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses();
                while (enumIpAddr.hasMoreElements()) {
                    InetAddress inetAddress = enumIpAddr.nextElement();
                    if (!inetAddress.isLoopbackAddress()) {
                        return inetAddress.getHostAddress();
                    }
                }
            }
        } catch (SocketException e) {
            LogUtils.e(e);
        }
        return "";
    }

    /**
     * 获取屏幕的分辨率
     */
    @SuppressWarnings("deprecation")
    public static int[] getResolution() {
        Context context = QApp.getInstance();
        if (null == context) {
            return null;
        }
        WindowManager windowMgr = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        int[] res = new int[2];
        res[0] = windowMgr.getDefaultDisplay().getWidth();
        res[1] = windowMgr.getDefaultDisplay().getHeight();
        return res;
    }

    public static String getResolutionStr() {
        Context context = QApp.getInstance();
        if (null == context) {
            return null;
        }
        WindowManager windowMgr = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        int[] res = new int[2];
        res[0] = windowMgr.getDefaultDisplay().getWidth();
        res[1] = windowMgr.getDefaultDisplay().getHeight();
        return res[0] + "*" + res[1];
    }

    /**
     * 获得设备的横向dpi
     */
    public static float getWidthDpi() {
        Context context = QApp.getInstance();
        if (null == context) {
            return 0;
        }
        DisplayMetrics dm = null;
        try {
            if (context != null) {
                dm = new DisplayMetrics();
                dm = context.getApplicationContext().getResources().getDisplayMetrics();
            }

            return dm.densityDpi;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * 获得设备的纵向dpi
     */
    public static float getHeightDpi() {
        Context context = QApp.getInstance();
        if (null == context) {
            return 0;
        }
        DisplayMetrics dm = new DisplayMetrics();
        dm = context.getApplicationContext().getResources().getDisplayMetrics();
        return dm.ydpi;
    }

    /**
     * 获取设备信息
     */
    public static String[] getDivceInfo() {
        String str1 = "/proc/cpuinfo";
        String str2 = "";
        String[] cpuInfo = {"", ""};
        String[] arrayOfString;
        try {
            FileReader fr = new FileReader(str1);
            BufferedReader localBufferedReader = new BufferedReader(fr, 8192);
            str2 = localBufferedReader.readLine();
            arrayOfString = str2.split("\\s+");
            for (int i = 2; i < arrayOfString.length; i++) {
                cpuInfo[0] = cpuInfo[0] + arrayOfString[i] + " ";
            }
            str2 = localBufferedReader.readLine();
            arrayOfString = str2.split("\\s+");
            cpuInfo[1] += arrayOfString[2];
            localBufferedReader.close();
        } catch (IOException e) {
            LogUtils.e(e);
        }
        return cpuInfo;
    }

    /**
     * 判断手机CPU是否支持NEON指令集
     */
    public static boolean isNEON() {
        boolean isNEON = false;
        String cupinfo = getCPUInfos();
        if (cupinfo != null) {
            cupinfo = cupinfo.toLowerCase();
            isNEON = cupinfo != null && cupinfo.contains("neon");
        }
        return isNEON;
    }

    /**
     * 读取CPU信息文件，获取CPU信息
     */
    @SuppressWarnings("resource")
    private static String getCPUInfos() {
        String str1 = "/proc/cpuinfo";
        String str2 = "";
        StringBuilder resusl = new StringBuilder();
        String resualStr = null;
        try {
            FileReader fr = new FileReader(str1);
            BufferedReader localBufferedReader = new BufferedReader(fr, 8192);
            while ((str2 = localBufferedReader.readLine()) != null) {
                resusl.append(str2);
                // String cup = str2;
            }
            if (resusl != null) {
                resualStr = resusl.toString();
                return resualStr;
            }
        } catch (IOException e) {
            LogUtils.e(e);
        }
        return resualStr;
    }

    /**
     * 获取当前设备cpu的型号
     */
    public static int getCPUModel() {
        return matchABI(getSystemProperty("ro.product.cpu.abi")) | matchABI(getSystemProperty("ro.product.cpu.abi2"));
    }

    /**
     * 匹配当前设备的cpu型号
     */
    private static int matchABI(String abiString) {
        if (TextUtils.isEmpty(abiString)) {
            return 0;
        }
        if ("armeabi".equals(abiString)) {
            return 1;
        } else if ("armeabi-v7a".equals(abiString)) {
            return 2;
        } else if ("x86".equals(abiString)) {
            return 4;
        } else if ("mips".equals(abiString)) {
            return 8;
        }
        return 0;
    }

    /**
     * 获取CPU核心数
     */
    public static int getCpuCount() {
        return Runtime.getRuntime().availableProcessors();
    }

    /**
     * 获取Rom版本
     */
    public static String getRomversion() {
        String rom = "";
        try {
            String modversion = getSystemProperty("ro.modversion");
            String displayId = getSystemProperty("ro.build.display.id");
            if (modversion != null && !modversion.equals("")) {
                rom = modversion;
            }
            if (displayId != null && !displayId.equals("")) {
                rom = displayId;
            }
        } catch (Exception e) {
            LogUtils.e(e);
        }
        return rom;
    }

    /**
     * 获取系统配置参数
     */
    public static String getSystemProperty(String key) {
        String pValue = null;
        try {
            Class<?> c = Class.forName("android.os.SystemProperties");
            Method m = c.getMethod("get", String.class);
            pValue = m.invoke(null, key).toString();
        } catch (Exception e) {
            LogUtils.e(e);
        }
        return pValue;
    }

    /**
     * 获取系统中的Library包
     */
    public static List<String> getSystemLibs() {
        Context context = QApp.getInstance();
        if (null == context) {
            return null;
        }
        PackageManager pm = context.getPackageManager();
        String[] libNames = pm.getSystemSharedLibraryNames();
        List<String> listLibNames = Arrays.asList(libNames);
        LogUtils.d("SystemLibs: " + listLibNames);
        return listLibNames;
    }

    /**
     * 获取手机外部可用空间大小，单位为byte
     * 外部sd卡大小
     */
    @SuppressWarnings("deprecation")
    public static long getExternalTotalSpace() {
        long totalSpace = -1L;
        if (FileUtils.isSDCardAvailable()) {
            try {
                String path = Environment.getExternalStorageDirectory().getPath();// 获取外部存储目录即 SDCard
                StatFs stat = new StatFs(path);
                long blockSize = stat.getBlockSize();
                long totalBlocks = stat.getBlockCount();
                totalSpace = totalBlocks * blockSize;
            } catch (Exception e) {
                LogUtils.e(e);
            }
        }
        return totalSpace;
    }

    /**
     * 获取外部存储可用空间，单位为byte
     */
    @SuppressWarnings("deprecation")
    public static long getExternalSpace() {
        long availableSpace = -1L;
        if (FileUtils.isSDCardAvailable()) {
            try {
                String path = Environment.getExternalStorageDirectory().getPath();
                StatFs stat = new StatFs(path);
                availableSpace = stat.getAvailableBlocks() * (long) stat.getBlockSize();
            } catch (Exception e) {
                LogUtils.e(e);
            }
        }
        return availableSpace;
    }

    /**
     * 获取手机内部空间大小，单位为byte
     */
    @SuppressWarnings("deprecation")
    public static long getTotalInternalSpace() {
        long totalSpace = -1L;
        try {
            String path = Environment.getDataDirectory().getPath();
            StatFs stat = new StatFs(path);
            long blockSize = stat.getBlockSize();
            long totalBlocks = stat.getBlockCount();// 获取该区域可用的文件系统数
            totalSpace = totalBlocks * blockSize;
        } catch (Exception e) {
            LogUtils.e(e);
        }
        return totalSpace;
    }

    /**
     * 获取手机内部可用空间大小，单位为byte
     */
    @SuppressWarnings("deprecation")
    public static long getAvailableInternalMemorySize() {
        long availableSpace = -1l;
        try {
            String path = Environment.getDataDirectory().getPath();// 获取 Android 数据目录
            StatFs stat = new StatFs(path);// 一个模拟linux的df命令的一个类,获得SD卡和手机内存的使用情况
            long blockSize = stat.getBlockSize();// 返回 Int ，大小，以字节为单位，一个文件系统
            long availableBlocks = stat.getAvailableBlocks();// 返回 Int ，获取当前可用的存储空间
            availableSpace = availableBlocks * blockSize;
        } catch (Exception e) {
            LogUtils.e(e);
        }
        return availableSpace;
    }

    /**
     * 获取单个应用最大分配内存，单位为byte
     */
    public static long getOneAppMaxMemory() {
        Context context = QApp.getInstance();
        if (context == null) {
            return -1;
        }
        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        return activityManager.getMemoryClass() * 1024 * 1024;
    }

    /**
     * 获取指定本应用占用的内存，单位为byte
     */
    public static long getUsedMemory() {
        return getUsedMemory(null);
    }

    /**
     * 获取指定包名应用占用的内存，单位为byte
     */
    public static String getPackageName(Context context) {
        String ret = null;
        if (context != null) {
            ret = context.getPackageName();
        }
        return ret;
    }

    public static long getUsedMemory(String packageName) {
        Context context = QApp.getInstance();
        if (context == null) {
            return -1;
        }
        if (StringUtils.isEmpty(packageName)) {
            packageName = context.getPackageName();
        }
        long size = 0;
        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> runapps = activityManager.getRunningAppProcesses();
        for (ActivityManager.RunningAppProcessInfo runapp : runapps) { // 遍历运行中的程序
            if (packageName.equals(runapp.processName)) {// 得到程序进程名，进程名一般就是包名，但有些程序的进程名并不对应一个包名
                // 返回指定PID程序的内存信息，可以传递多个PID，返回的也是数组型的信息
                Debug.MemoryInfo[] processMemoryInfo = activityManager.getProcessMemoryInfo(new int[]{runapp.pid});
                // 得到内存信息中已使用的内存，单位是K
                size = processMemoryInfo[0].getTotalPrivateDirty() * 1024;
            }
        }
        return size;
    }

    /**
     * 获取手机剩余内存，单位为byte
     */
    public static long getAvailableMemory() {
        Context context = QApp.getInstance();
        if (context == null) {
            return -1;
        }
        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        ActivityManager.MemoryInfo info = new ActivityManager.MemoryInfo();
        activityManager.getMemoryInfo(info);
        return info.availMem;
    }

    /**
     * 获取手机总内存，单位为byte
     */
    public static long getTotalMemory() {
        long size = 0;
        String path = "/proc/meminfo";// 系统内存信息文件
        try {
            String totalMemory = FileUtils.readProperties(path, "MemTotal", null);// 读出来是带单位kb的，并且单位前有空格，所以去掉最后三位
            if (!StringUtils.isEmpty(totalMemory) && totalMemory.length() > 3) {
                size = Long.valueOf(totalMemory.substring(0, totalMemory.length() - 3)) * 1024;
            }
        } catch (Exception e) {
            LogUtils.e(e);
        }
        return size;
    }

    /**
     * 手机低内存运行阀值，单位为byte
     */
    public static long getThresholdMemory() {
        Context context = QApp.getInstance();
        if (context == null) {
            return -1;
        }
        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        ActivityManager.MemoryInfo info = new ActivityManager.MemoryInfo();
        activityManager.getMemoryInfo(info);
        return info.threshold;
    }

    /**
     * 手机是否处于低内存运行
     */
    public static boolean isLowMemory() {
        Context context = QApp.getInstance();
        if (context == null) {
            return false;
        }
        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        ActivityManager.MemoryInfo info = new ActivityManager.MemoryInfo();
        activityManager.getMemoryInfo(info);
        return info.lowMemory;
    }

    /**
     * 判断WIFI是否已经连接
     *
     * @param context
     * @return
     */
    public static boolean isWifiConnected(Context context) {
        ConnectivityManager conMan = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo.State wifi = conMan.getNetworkInfo(ConnectivityManager.TYPE_WIFI)
                .getState();
        if (NetworkInfo.State.CONNECTED == wifi) {
            return true;
        } else {
            return false;
        }
    }

    public static PackageInfo getApkInfo(Context context, String archiveFilePath) {
        PackageManager pm = context.getPackageManager();
        PackageInfo info = pm.getPackageArchiveInfo(archiveFilePath, PackageManager.GET_ACTIVITIES);
        if (info != null) {
            ApplicationInfo appInfo = info.applicationInfo;
            String appName = pm.getApplicationLabel(appInfo).toString();
            String packageName = appInfo.packageName;  //得到安装包名称
            String version = info.versionName;       //得到版本信息
//            Toast.makeText(ChartTestActivity.this, , Toast.LENGTH_LONG).show();
            Drawable icon = pm.getApplicationIcon(appInfo);//得到图标信息
            return info;
        } else {
            return null;
        }
    }

    /**
     * 获取设备的唯一标识
     */
    public static String getDeviceId() {
        String androidId = "N888888", SerialNumber = "N888888", deviceId = "N888888";
        StringBuffer finalId = new StringBuffer();
        /*String cityId = UserInfo.getCityId();
        if(cityId.length()==4){
            cityId+="XX";
        }*/
        try {
            androidId = Settings.System.getString(QApp.getInstance().getContentResolver(), Settings.Secure.ANDROID_ID);
        } catch (Exception e) {
            e.printStackTrace();
            androidId = "N888888";
        }
        try {
            SerialNumber = Build.SERIAL;
        } catch (Exception e) {
            e.printStackTrace();
            SerialNumber = "N888888";

        }

        TelephonyManager telephonyManager = (TelephonyManager) QApp.getInstance().getSystemService(Context.TELEPHONY_SERVICE);
        try {
            if (ActivityCompat.checkSelfPermission(QApp.getInstance(), Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
                return null;
            }
            deviceId = telephonyManager.getDeviceId();
        } catch (Exception e) {
            e.printStackTrace();
            deviceId = "N888888";
        }

        finalId.append(androidId);
        finalId.append(SerialNumber);
        finalId.append(deviceId);

        deviceId = finalId.toString();
        finalId.delete(0, finalId.length());
        finalId.append("and:");
        /*   finalId.append(cityId);*/
        finalId.append(":");
        finalId.append(getUUID(deviceId));


        LogUtils.D("tag_debug_systemUtils", "finalId::" + finalId);
        return finalId.toString();
    }

    /**
     * 生成UUID
     *
     * @param deviceId
     * @return
     */
    public static String getUUID(String deviceId) {
        String uuidStr = null;

        UUID uuid;
        try {
            if (!"9774d56d682e549c".equals(deviceId)) {

                //在Android2.3版本之前才会有这个问题。

                uuid = UUID.nameUUIDFromBytes(deviceId.getBytes("utf8"));

            } else {

                uuid = deviceId != null ? UUID.nameUUIDFromBytes(deviceId.getBytes("utf8")) : UUID.randomUUID();

            }
            uuidStr = uuid.toString();
            LogUtils.D("debug_systemUtils", "final" + uuidStr);
            uuidStr = MD5Util.getMD5(uuidStr);
        } catch (Exception e) {
            e.printStackTrace();

        }

        return uuidStr;
    }

    public static boolean isAPPALive(Context mContext, String packageName) {
        boolean isAPPRunning = false;
        // 获取activity管理对象
        ActivityManager activityManager = (ActivityManager) mContext.getSystemService(Context.ACTIVITY_SERVICE);
        // 获取所有正在运行的app
        List<ActivityManager.RunningAppProcessInfo> appProcessInfoList = activityManager.getRunningAppProcesses();
        // 遍历，进程名即包名
        for (ActivityManager.RunningAppProcessInfo appInfo : appProcessInfoList) {
            if (packageName.equals(appInfo.processName)) {
                isAPPRunning = true;
                break;
            }
        }
        return isAPPRunning;
    }


}
