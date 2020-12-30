package com.lin.glide.config;

/**
 * @author ：王文彬 on 2020-07-13 11：26
 * @describe ：基础配置
 * @email ：wangwenbin29@jd.com
 */
public class GlideConfig {

  /**
   * Glide缓存大小：20M
   */
  private static final int MEMORY_CACHE_SIZE = 1024 * 1024 * 20;


  /**
   * 图片缓存子目录名称
   */
  private static final String MEMORY_CACHE_PATH = "memory_cache_path";


  public static int getMemoryCacheSize() {
    return MEMORY_CACHE_SIZE;
  }

  public static String getMemoryCachePath() {
    return MEMORY_CACHE_PATH;
  }
}
