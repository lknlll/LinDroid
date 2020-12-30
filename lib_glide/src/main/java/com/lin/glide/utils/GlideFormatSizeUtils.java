package com.lin.glide.utils;

import java.math.BigDecimal;

/**
 * @author ：王文彬 on 2020-07-13 14：10
 * @describe： 格式化文件的大小
 * @email：wangwenbin29@jd.com
 */
public class GlideFormatSizeUtils {

  /**
   * 根据文件大小，获取格式化后的文件大小,并向上取整
   *
   * @param saveNum 保留几位小数
   * @param size    文件大小
   */
  public static String getFormatSize(double size, int saveNum) {
    return getFormatSize(size, saveNum, BigDecimal.ROUND_HALF_UP);
  }

  /**
   * @param size    需要格式化文件的大小
   * @param saveNum 保留的小数点
   * @param type    小数点后几位处理方式
   */
  public static String getFormatSize(double size, int saveNum, int type) {

    double kiloByte = size / 1024;
    if (kiloByte < 1) {
      return size + "Byte";
    }
    double megaByte = kiloByte / 1024;
    if (megaByte < 1) {
      BigDecimal result1 = new BigDecimal(Double.toString(kiloByte));
      return result1.setScale(saveNum, type).toPlainString() + "KB";
    }
    double gigaByte = megaByte / 1024;
    if (gigaByte < 1) {
      BigDecimal result2 = new BigDecimal(Double.toString(megaByte));
      return result2.setScale(saveNum, type).toPlainString() + "MB";
    }
    double teraBytes = gigaByte / 1024;
    if (teraBytes < 1) {
      BigDecimal result3 = new BigDecimal(Double.toString(gigaByte));
      return result3.setScale(saveNum, type).toPlainString() + "GB";
    }
    BigDecimal result4 = new BigDecimal(teraBytes);
    return result4.setScale(saveNum, type).toPlainString() + "TB";

  }
}
