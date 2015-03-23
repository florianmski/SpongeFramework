package com.florianmski.spongeframework.utils;

import android.content.Context;
import android.content.res.AssetManager;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import timber.log.Timber;

import static android.content.Context.MODE_PRIVATE;

public class FileUtils
{
    private static void copyFile(InputStream src, OutputStream dst) throws IOException
    {
        try
        {
            byte[] buffer = new byte[1024];
            int length;
            while ((length = src.read(buffer)) > 0)
                dst.write(buffer, 0, length);
        }
        finally
        {
            src.close();
            dst.close();
        }
    }

    public static void copyFile(File src, File dst) throws IOException
    {
        copyFile(new FileInputStream(src), new FileOutputStream(dst));
    }

    public static void copyFile(InputStream src, File dst) throws IOException
    {
        copyFile(src, new FileOutputStream(dst));
    }

    public static void copyFileToSD(Context context, File src, String fileName) throws IOException
    {
        File file = fromExternalDir(context, fileName);
        boolean created = file.createNewFile();
        if(!created)
            Timber.d("%s hasn't been created!", fileName);

        copyFile(src, file);
    }

    public static void copyFileToSD(Context context, File src) throws IOException
    {
        copyFileToSD(context, src, src.getName());
    }

    public static InputStream fromAssets(Context context, String fileName) throws IOException
    {
        AssetManager am = context.getAssets();
        return am.open(fileName);
    }

    public static File fromInternalDir(Context context, String dirName, String fileName) throws IOException
    {
        return new File(context.getDir(dirName, MODE_PRIVATE), fileName);
    }

    public static File fromExternalDir(Context context, String dirName, String fileName) throws IOException
    {
        return new File(context.getExternalFilesDir(dirName), fileName);
    }

    public static File fromExternalDir(Context context, String fileName) throws IOException
    {
        return fromExternalDir(context, null, fileName);
    }
}
