package android.contentsensor;

import android.app.Activity;
import android.app.AppGlobals;
import android.app.Application;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Bundle;
import android.os.StrictMode;
import android.os.StrictMode.ThreadPolicy;
import android.text.TextUtils;
import android.util.Log;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public class ContentSensorManagerFactory {
    private static final String SENSOR_MANAGER_CLASS_NAME = "com.huawei.contentsensor.agent.ContentSensorManager";
    private static final String SENSOR_MANAGER_PACKAGE_NAME = "com.huawei.contentsensor";
    private static final String TAG = "ContentSensorFactory";
    private static Class<IContentSensorManager> sContentSensorClz = null;
    private static volatile PackageInfo sPackageInfo = null;

    static class DefaultContentSensorManager implements IContentSensorManager {
        DefaultContentSensorManager() {
        }

        public void updateToken(int token, Activity activity) {
        }

        public void copyNode(Bundle data) {
        }

        public void processImageAndWebView(Bundle data) {
        }
    }

    static class LogUtil {
        public static final String TAG = "ContentSensorFactory";
        private static boolean mIsDLogCanPrint;
        private static boolean mIsELogCanPrint;
        private static boolean mIsILogCanPrint;
        private static boolean mIsVLogCanPrint;
        private static boolean mIsWLogCanPrint;

        LogUtil() {
        }

        static {
            mIsVLogCanPrint = false;
            mIsDLogCanPrint = true;
            mIsILogCanPrint = true;
            mIsWLogCanPrint = true;
            mIsELogCanPrint = true;
            mIsVLogCanPrint = isNormalLogCanPrint("ContentSensorFactory", 2);
            mIsDLogCanPrint = isNormalLogCanPrint("ContentSensorFactory", 3);
            mIsILogCanPrint = isNormalLogCanPrint("ContentSensorFactory", 4);
            mIsWLogCanPrint = isNormalLogCanPrint("ContentSensorFactory", 5);
            mIsELogCanPrint = isNormalLogCanPrint("ContentSensorFactory", 6);
        }

        private static boolean isNormalLogCanPrint(String tag, int level) {
            return Log.isLoggable(tag, level);
        }

        public static void v(String className, String msg) {
            if (mIsVLogCanPrint) {
                Log.v("ContentSensorFactory", className + ": " + msg);
            }
        }

        public static void d(String className, String msg) {
            if (mIsDLogCanPrint) {
                Log.d("ContentSensorFactory", className + ": " + msg);
            }
        }

        public static void i(String className, String msg) {
            if (mIsILogCanPrint) {
                Log.i("ContentSensorFactory", className + ": " + msg);
            }
        }

        public static void w(String className, String msg) {
            if (mIsWLogCanPrint) {
                Log.w("ContentSensorFactory", className + ": " + msg);
            }
        }

        public static void e(String className, String msg) {
            if (mIsELogCanPrint) {
                Log.e("ContentSensorFactory", className + ": " + msg);
            }
        }

        public static void logException(String className, String msg, Exception e) {
            if (!mIsELogCanPrint) {
                return;
            }
            if (TextUtils.isEmpty(msg)) {
                if (e != null) {
                    Log.e("ContentSensorFactory", className + ": " + msg);
                }
            } else if (e != null) {
                Log.e("ContentSensorFactory", className + ": " + msg + e.getMessage(), e);
            } else {
                Log.e("ContentSensorFactory", className + ": " + msg);
            }
        }
    }

    public static IContentSensorManager createContentSensorManager(int token, Activity activity) {
        long currentTime = System.currentTimeMillis();
        IContentSensorManager sensor = null;
        ThreadPolicy oldPolicy = StrictMode.allowThreadDiskReads();
        try {
            Class<IContentSensorManager> clz = getContentSensorManagerClass();
            if (clz != null) {
                Constructor constructor = clz.getConstructor(new Class[]{Integer.TYPE, Activity.class});
                constructor.setAccessible(true);
                sensor = (IContentSensorManager) constructor.newInstance(new Object[]{Integer.valueOf(token), activity});
            }
            StrictMode.setThreadPolicy(oldPolicy);
        } catch (ClassNotFoundException e) {
            LogUtil.e("ContentSensorFactory", "ClassNotFoundExceptione:" + e);
            StrictMode.setThreadPolicy(oldPolicy);
        } catch (NoSuchMethodException e2) {
            LogUtil.e("ContentSensorFactory", "NoSuchMethodExceptione:" + e2);
            StrictMode.setThreadPolicy(oldPolicy);
        } catch (InstantiationException e3) {
            LogUtil.e("ContentSensorFactory", "InstantiationExceptione:" + e3);
            StrictMode.setThreadPolicy(oldPolicy);
        } catch (IllegalAccessException e4) {
            LogUtil.e("ContentSensorFactory", "IllegalAccessExceptione:" + e4);
            StrictMode.setThreadPolicy(oldPolicy);
        } catch (InvocationTargetException e5) {
            LogUtil.e("ContentSensorFactory", "InvocationTargetExceptione:" + e5);
            StrictMode.setThreadPolicy(oldPolicy);
        } catch (Throwable th) {
            StrictMode.setThreadPolicy(oldPolicy);
            throw th;
        }
        if (sensor != null) {
            return sensor;
        }
        LogUtil.w("ContentSensorFactory", "DefaultContentSensorManager is created");
        return new DefaultContentSensorManager();
    }

    private static synchronized Class<IContentSensorManager> getContentSensorManagerClass() throws ClassNotFoundException {
        synchronized (ContentSensorManagerFactory.class) {
            Class<IContentSensorManager> cls;
            if (sContentSensorClz != null) {
                cls = sContentSensorClz;
                return cls;
            }
            PackageInfo packageInfo = fetchPackageInfo();
            if (packageInfo == null) {
                return null;
            }
            Application initialApplication = AppGlobals.getInitialApplication();
            try {
                Context sensorContext = initialApplication.createPackageContext(packageInfo.packageName, 3);
                initialApplication.getAssets().addAssetPath(sensorContext.getApplicationInfo().sourceDir);
                sContentSensorClz = Class.forName(SENSOR_MANAGER_CLASS_NAME, true, sensorContext.getClassLoader());
                cls = sContentSensorClz;
                return cls;
            } catch (NameNotFoundException e) {
                LogUtil.e("ContentSensorFactory", "can not find class com.huawei.contentsensor.agent.ContentSensorManager");
                return null;
            }
        }
    }

    private static PackageInfo fetchPackageInfo() {
        if (sPackageInfo != null) {
            return sPackageInfo;
        }
        Application app = AppGlobals.getInitialApplication();
        if (app == null) {
            return null;
        }
        PackageManager pm = app.getPackageManager();
        if (pm == null) {
            return null;
        }
        try {
            sPackageInfo = pm.getPackageInfo(SENSOR_MANAGER_PACKAGE_NAME, 128);
        } catch (NameNotFoundException e) {
            LogUtil.e("ContentSensorFactory", "can not find package com.huawei.contentsensor");
        }
        return sPackageInfo;
    }
}
