package com.lin.glide.config

import android.util.Log
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import java.util.concurrent.TimeUnit

/**
 * @author ：王文彬 on 2020/7/20
 * @email ：wangwenbin29@jd.com
 * @describe ：Glide OkHttp配置
 */
class GlideOkHttpConfig {


  companion object {

    @JvmStatic
    fun getOkHttpClient(): OkHttpClient =
        OkHttpClient.Builder().apply {
          addInterceptor(provideLoggingInterceptor())
          readTimeout(10, TimeUnit.SECONDS)
          connectTimeout(10, TimeUnit.SECONDS)
          callTimeout(10, TimeUnit.SECONDS)
        }.build()

    private fun provideLoggingInterceptor(): HttpLoggingInterceptor =
        HttpLoggingInterceptor(object : HttpLoggingInterceptor.Logger {
          override fun log(message: String) {
            Log.e("-->", message)
          }
        }).apply {
          level = HttpLoggingInterceptor.Level.BODY
        }


  }

}