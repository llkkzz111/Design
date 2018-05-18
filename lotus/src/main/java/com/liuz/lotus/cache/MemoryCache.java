package com.liuz.lotus.cache;

import android.annotation.TargetApi;
import android.app.ActivityManager;
import android.content.Context;
import android.os.Build;
import android.os.Environment;
import android.os.StatFs;
import android.text.TextUtils;
import android.text.format.Formatter;
import android.util.Log;
import android.util.LruCache;

import com.liuz.lotus.utils.JLog;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;


/**
 * @Description: 内存缓存
 * @author: jeasinlee
 * @date: 2016-12-19 15:08
 */
public class MemoryCache implements ICache {
    private LruCache<String, Object> cache;
    private static MemoryCache instance;

    private MemoryCache() {
        int maxMemory = (int) Runtime.getRuntime().maxMemory();
        int cacheSize = maxMemory / 8;
        cache = new LruCache(cacheSize);

    }

    public static MemoryCache getInstance() {
        if (instance == null) {
            synchronized (MemoryCache.class) {
                if (instance == null) {
                    instance = new MemoryCache();
                }
            }
        }
        return instance;
    }

    @Override
    public synchronized void put(String key, Object value) {
        if (TextUtils.isEmpty(key)) return;

        if (cache.get(key) != null) {
            cache.remove(key);
        }
        cache.put(key, value);
    }

    @Override
    public Object get(String key) {
        return cache.get(key);
    }

    public synchronized <T> T get(String key, Class<T> clazz) {
        try {
            return (T) cache.get(key);
        } catch (Exception e) {
            e.printStackTrace();
            JLog.e(e.toString());
        }
        return null;
    }

    @Override
    public void remove(String key) {
        if (cache.get(key) != null) {
            cache.remove(key);
        }
    }

    @Override
    public boolean contains(String key) {
        return cache.get(key) != null;
    }

    @Override
    public void clear() {
        cache.evictAll();
    }

    /**
     *  内存信息
     * Created by Jixiang_Li on 2017/2/5.
     */

    public static class MemoryUtil {

        private static final int ERROR = -1;
        private static final int AVALIABLE_EXTERNAL_MEMORY_SIZE = 50 * 1024 * 1024; // 50MB

        /**
         * Judge whether external momory is available
         *
         * @return
         */
        public static boolean isExternalMemoryAvailable() {
            return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
        }

        /**
         * Judge whether external memory is full
         *
         * @return
         */
        public static boolean isExternalMemoryFull() {
            return getAvailableExternalMemorySize() - AVALIABLE_EXTERNAL_MEMORY_SIZE < 0;
        }

        /**
         * Get available internal memory size
         *
         * @return
         */
        public static long getAvailableInternalMemorySize() {
            File path = Environment.getDataDirectory();
            StatFs stat = new StatFs(path.getPath());
            long blockSize = stat.getBlockSize();
            long availableBlocks = stat.getAvailableBlocks();
            return availableBlocks * blockSize;
        }

        /**
         * Get internal memory size
         *
         * @return
         */
        public static long getTotalInternalMemorySize() {
            File path = Environment.getDataDirectory();
            StatFs stat = new StatFs(path.getPath());
            long blockSize = stat.getBlockSize();
            long totalBlocks = stat.getBlockCount();
            return totalBlocks * blockSize;
        }

        /**
         * Get available external memory size
         *
         * @return
         */
        public static long getAvailableExternalMemorySize() {
            if (isExternalMemoryAvailable()) {
                File path = Environment.getExternalStorageDirectory();
                StatFs stat = new StatFs(path.getPath());
                long blockSize = stat.getBlockSize();
                long availableBlocks = stat.getAvailableBlocks();
                return availableBlocks * blockSize;
            } else {
                return ERROR;
            }
        }

        /**
         * Get external memory size
         *
         * @return
         */
        public static long getTotalExternalMemorySize() {
            if (isExternalMemoryAvailable()) {
                File path = Environment.getExternalStorageDirectory();
                StatFs stat = new StatFs(path.getPath());
                long blockSize = stat.getBlockSize();
                long totalBlocks = stat.getBlockCount();
                return totalBlocks * blockSize;
            } else {
                return ERROR;
            }
        }


        /**
         * Get total memory
         *
         * @param context
         * @return
         */
        public static String getTotalMemory(Context context) {
            String str1 = "/proc/meminfo";
            String str2;
            String[] arrayOfString;
            long initial_memory = 0;
            try {
                FileReader localFileReader = new FileReader(str1);
                BufferedReader localBufferedReader = new BufferedReader(localFileReader, 8192);
                str2 = localBufferedReader.readLine();
                arrayOfString = str2.split("\\s+");
                for (String num : arrayOfString) {
                    Log.i(str2, num + "\t");
                }
                initial_memory = Integer.valueOf(arrayOfString[1]).intValue() * 1024;
                localBufferedReader.close();

            } catch (IOException e) {
                e.printStackTrace();
            }
            return Formatter.formatFileSize(context, initial_memory);// Byte转换为KB或者MB，内存大小规格化
        }

        /**
         * Get available memory
         *
         * @param context
         * @return
         */
        public static String getAvailableMemory(Context context) {
            ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
            ActivityManager.MemoryInfo mi = new ActivityManager.MemoryInfo();
            am.getMemoryInfo(mi);
            return Formatter.formatFileSize(context, mi.availMem);
        }

        /**
         * Get all memory
         *
         * @param context
         * @return
         */
        @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
        public static String getAllMemory(Context context) {
            ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
            ActivityManager.MemoryInfo mi = new ActivityManager.MemoryInfo();
            am.getMemoryInfo(mi);
            return Formatter.formatFileSize(context, mi.totalMem);
        }

        /**
         * Get format size
         *
         * @param size
         * @return
         */
        public static String getFormatSize(double size) {
            double kiloByte = size / 1024;
            if (kiloByte < 1) {
                return size + "Byte";
            }

            double megaByte = kiloByte / 1024;
            if (megaByte < 1) {
                BigDecimal result1 = new BigDecimal(Double.toString(kiloByte));
                return result1.setScale(2, BigDecimal.ROUND_HALF_UP)
                        .toPlainString() + "KB";
            }

            double gigaByte = megaByte / 1024;
            if (gigaByte < 1) {
                BigDecimal result2 = new BigDecimal(Double.toString(megaByte));
                return result2.setScale(2, BigDecimal.ROUND_HALF_UP)
                        .toPlainString() + "MB";
            }

            double teraBytes = gigaByte / 1024;
            if (teraBytes < 1) {
                BigDecimal result3 = new BigDecimal(Double.toString(gigaByte));
                return result3.setScale(2, BigDecimal.ROUND_HALF_UP)
                        .toPlainString() + "GB";
            }
            BigDecimal result4 = new BigDecimal(teraBytes);
            return result4.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString()
                    + "TB";
        }

    }
}
