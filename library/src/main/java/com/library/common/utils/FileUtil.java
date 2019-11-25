package com.library.common.utils;

import android.content.Context;
import android.os.Environment;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import okhttp3.ResponseBody;

/**
 * Creator: thymejoe@gmail.com <br>
 * Date: 2017/8/18 11:12 <br>
 */

public class FileUtil
{
    private FileUtil()
    {
    }

    /**
     * 返回缓存文件夹
     */
    public static File getCacheFile(Context context)
    {
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED))
        {
            File file = null;
            file = context.getExternalCacheDir();//获取系统管理的sd卡缓存文件
            if (file == null)
            {//如果获取的文件为空,就使用自己定义的缓存文件夹做缓存路径
                file = new File(getCacheFilePath(context));
                makeDirs(file);
            }
            return file;
        }
        else
        {
            return context.getCacheDir();
        }
    }

    /**
     * 获取自定义缓存文件地址
     *
     * @param context
     * @return
     */
    public static String getCacheFilePath(Context context)
    {
        String packageName = context.getPackageName();
        return "/mnt/sdcard/" + packageName;
    }


    /**
     * 创建未存在的文件夹
     *
     * @param file
     * @return
     */
    public static File makeDirs(File file)
    {
        if (!file.exists())
        {
            file.mkdirs();
        }
        return file;
    }

    /**
     * 使用递归获取目录文件大小
     *
     * @param dir
     * @return
     */
    public static long getDirSize(File dir)
    {
        if (dir == null)
        {
            return 0;
        }
        if (!dir.isDirectory())
        {
            return 0;
        }
        long dirSize = 0;
        File[] files = dir.listFiles();
        for (File file : files)
        {
            if (file.isFile())
            {
                dirSize += file.length();
            }
            else if (file.isDirectory())
            {
                dirSize += file.length();
                dirSize += getDirSize(file); // 递归调用继续统计
            }
        }
        return dirSize;
    }

    /**
     * 使用递归删除文件夹
     *
     * @param dir
     * @return
     */
    public static boolean deleteDir(File dir)
    {
        if (dir == null)
        {
            return false;
        }
        if (!dir.isDirectory())
        {
            return false;
        }
        File[] files = dir.listFiles();
        for (File file : files)
        {
            if (file.isFile())
            {
                file.delete();
            }
            else if (file.isDirectory())
            {
                deleteDir(file); // 递归调用继续删除
            }
        }
        return true;
    }


    public static String bytyToString(InputStream in) throws IOException
    {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        byte[] buf = new byte[1024];
        int num = 0;
        while ((num = in.read(buf)) != -1)
        {
            out.write(buf, 0, buf.length);
        }
        String result = out.toString();
        out.close();
        return result;
    }

    //保存图片到本地
    public static boolean writeResponseBodyToDisk(Context context, ResponseBody body, String fileName) {
        try {
            File futureStudioIconFile = new File(context.getExternalFilesDir(null) + File.separator + fileName);
            InputStream inputStream = null;
            OutputStream outputStream = null;
            try {
                byte[] fileReader = new byte[4096];
                long fileSize = body.contentLength();
                long fileSizeDownloaded = 0;
                inputStream = body.byteStream();
                outputStream = new FileOutputStream(futureStudioIconFile);
                while (true) {
                    int read = inputStream.read(fileReader);
                    if (read == -1) {
                        break;
                    }
                    outputStream.write(fileReader, 0, read);
                    fileSizeDownloaded += read;
                }
                outputStream.flush();

                return true;
            } catch (IOException e) {
                return false;
            } finally {
                if (inputStream != null) {
                    inputStream.close();
                }
                if (outputStream != null) {
                    outputStream.close();
                }
            }
        } catch (IOException e) {
            return false;
        }
    }
}
