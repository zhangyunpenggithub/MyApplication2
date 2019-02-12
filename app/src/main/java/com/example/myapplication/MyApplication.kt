package com.example.myapplication

import android.app.Application
import android.app.Instrumentation
import android.content.Context





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
        // 先获取到当前的ActivityThread对象
        val activityThreadClass = Class.forName("android.app.ActivityThread")
        val currentActivityThreadMethod = activityThreadClass.getDeclaredMethod("currentActivityThread")
        currentActivityThreadMethod.isAccessible = true
        val currentActivityThread = currentActivityThreadMethod.invoke(null)

        // 拿到原始的 mInstrumentation字段
        val mInstrumentationField = activityThreadClass.getDeclaredField("mInstrumentation")
        mInstrumentationField.isAccessible = true
        val mInstrumentation = mInstrumentationField.get(currentActivityThread) as Instrumentation

        // 创建代理对象
        val evilInstrumentation = EvilInstrumentation(mInstrumentation)

        // 偷梁换柱
        mInstrumentationField.set(currentActivityThread, evilInstrumentation)
    }
}