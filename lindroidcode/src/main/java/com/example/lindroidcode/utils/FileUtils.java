package com.example.lindroidcode.utils;

import android.content.Context;
import android.net.Uri;
import android.os.Environment;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class FileUtils {
    /**
     * 删除单个文件
     *
     * @param filePath 被删除文件的文件名
     */
    public static boolean deleteSingleFile(String filePath) {
        if (filePath == null) {
            return false;
        }
        File file = new File(filePath);
        if (file.isFile() && file.exists()) {
            return file.delete();
        }
        return false;
    }

    public static File transfer(Context context, Uri contentUri) {
        File file = createVideoFile(context, "VIDEO", "");
        if (file == null) return null;
        InputStream is = null;
        BufferedOutputStream bos = null;
        try {
            is = context.getContentResolver().openInputStream(contentUri);
            if (is == null) return null;

            bos = new BufferedOutputStream(new FileOutputStream(file));
            byte[] bytes = new byte[1024];
            int length;
            while ((length = is.read(bytes)) != -1) {
                bos.write(bytes, 0, length);
            }
            bos.flush();
            return file;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (bos != null) {
                try {
                    bos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static File createVideoFile(Context context, String prefix, String suffix) {
        try {
            String timeStamp = new SimpleDateFormat("yyyyMMdd-HHmmss", Locale.US).format(new Date());
            String videoFileName = prefix + "-" + timeStamp;
            File storageDir = context.getExternalFilesDir(Environment.DIRECTORY_MOVIES);
            return File.createTempFile(videoFileName, suffix, storageDir);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
