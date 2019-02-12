package com.example.myapplication

import android.app.Application
import android.app.Instrumentation
import android.content.Context
import android.util.Log


/**
 * +----------------------------------------------------------------------
 * | 59197696@qq.com
 * +----------------------------------------------------------------------
 * | 功能描述:
 * +----------------------------------------------------------------------
* | 时　　间: 2019/2/11.
* +----------------------------------------------------------------------
* | 代码创建: 张云鹏
* +----------------------------------------------------------------------
* | 版本信息: V1.0.0
* +----------------------------------------------------------------------
**/
class MyApplication : Application() {

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        hookStartActivity()
    }

    /**
     * 跳转activity之前打印一行日志
     */
    private fun hookStartActivity() {
        //拿到原来的Instrumentation
        try {
            val clazz = Class.forName("android.app.ActivityThread")
            val currentThreadField = clazz.getDeclaredField("sCurrentActivityThread")
            currentThreadField.isAccessible = true
            val currentThread = currentThreadField.get(null)
            val mInstrumentationDeclaredField = clazz.getDeclaredField("mInstrumentation")
            mInstrumentationDeclaredField.isAccessible = true
            val evilInstrumentation = EvilInstrumentation(mInstrumentationDeclaredField.get(currentThread) as Instrumentation)
            mInstrumentationDeclaredField.set(currentThread, evilInstrumentation)
        } catch (e: Exception) {
            Log.e("zhangyunpeng", "application有错误")
        }


    }
}