package com.example.lindroidcode.utils;

import java.io.File;

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
}
