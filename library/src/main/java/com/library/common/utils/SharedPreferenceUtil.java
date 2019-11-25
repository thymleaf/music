package com.library.common.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Base64;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * Creator: thymejoe@gmail.com <br>
 * Date: 2017/8/18 10:16 <br>
 */
public class SharedPreferenceUtil
{
    private static SharedPreferences mSharedPreferences;
    public static final String SP_NAME = "config";


    private SharedPreferenceUtil()
    {
    }

    /**
     * 存储重要信息到sharedPreferences；
     *
     * @param key
     * @param value
     */
    public static void setString(Context context, String key, String value)
    {
        if (mSharedPreferences == null)
        {
            mSharedPreferences = context.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
        }
        mSharedPreferences.edit()
                          .putString(key, value)
                          .apply();
    }

    public static boolean getBoolean(Context context, String key, boolean defValue)
    {
        if (mSharedPreferences == null)
        {
            mSharedPreferences = context.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
        }

        return  mSharedPreferences.getBoolean(key,defValue);
    }


    public static void setBoolean(Context context, String key, boolean value)
    {
        if (mSharedPreferences == null)
        {
            mSharedPreferences = context.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
        }
        mSharedPreferences.edit()
                          .putBoolean(key, value)
                          .apply();
    }

    /**
     * 返回存在sharedPreferences的信息
     *
     * @param key
     * @return
     */
    public static String getString(Context context, String key)
    {
        if (mSharedPreferences == null)
        {
            mSharedPreferences = context.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
        }
        return mSharedPreferences.getString(key, "");
    }

    /**
     * 存储重要信息到sharedPreferences；
     *
     * @param key
     * @param value
     */
    public static void setInterger(Context context, String key, int value)
    {
        if (mSharedPreferences == null)
        {
            mSharedPreferences = context.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
        }
        mSharedPreferences.edit()
                          .putInt(key, value)
                          .apply();
    }

    /**
     * 返回存在sharedPreferences的信息
     *
     * @param key
     * @return
     */
    public static int getInterger(Context context, String key)
    {
        if (mSharedPreferences == null)
        {
            mSharedPreferences = context.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
        }
        return mSharedPreferences.getInt(key, -1);
    }

    /**
     * 清除某个内容
     */
    public static void removeSF(Context context, String key)
    {
        if (mSharedPreferences == null)
        {
            mSharedPreferences = context.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
        }
        mSharedPreferences.edit()
                          .remove(key)
                          .apply();
    }

    /**
     * 清除Shareprefrence
     */
    public static void clearShareprefrence(Context context)
    {
        if (mSharedPreferences == null)
        {
            mSharedPreferences = context.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
        }
        mSharedPreferences.edit()
                          .clear()
                          .apply();
    }

    /**
     * 将对象储存到sharepreference
     *
     * @param key
     * @param device
     * @param <T>
     */
    public static <T> boolean saveDeviceData(Context context, String key, T device)
    {
        if (mSharedPreferences == null)
        {
            mSharedPreferences = context.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
        }
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try
        {   //Device为自定义类
            // 创建对象输出流，并封装字节流
            ObjectOutputStream oos = new ObjectOutputStream(baos);
            // 将对象写入字节流
            oos.writeObject(device);
            // 将字节流编码成base64的字符串
            String oAuth_Base64 = new String(Base64.encode(baos.toByteArray(), Base64.DEFAULT));
            mSharedPreferences.edit()
                              .putString(key, oAuth_Base64)
                              .apply();
            return true;
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 将对象从shareprerence中取出来
     *
     * @param key
     * @param <T>
     * @return
     */
    public static <T> T getDeviceData(Context context, String key)
    {
        if (mSharedPreferences == null)
        {
            mSharedPreferences = context.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
        }
        T device = null;
        String productBase64 = mSharedPreferences.getString(key, null);

        if (productBase64 == null)
        {
            return null;
        }
        // 读取字节
        byte[] base64 = Base64.decode(productBase64.getBytes(), Base64.DEFAULT);

        // 封装到字节流
        ByteArrayInputStream bais = new ByteArrayInputStream(base64);
        try
        {
            // 再次封装
            ObjectInputStream bis = new ObjectInputStream(bais);

            // 读取对象
            device = (T) bis.readObject();

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return device;
    }


}
