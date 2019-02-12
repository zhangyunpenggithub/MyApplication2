package com.example.myapplication;

import android.app.Activity;
import android.app.Instrumentation;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;

import java.lang.reflect.Method;

/**
 * +----------------------------------------------------------------------
 * | 59197696@qq.com
 * +----------------------------------------------------------------------
 * | 功能描述:
 * +----------------------------------------------------------------------
 * | 时　　间: 2019/2/12.
 * +----------------------------------------------------------------------
 * | 代码创建: 张云鹏
 * +----------------------------------------------------------------------
 * | 版本信息: V1.0.0
 * +----------------------------------------------------------------------
 **/
public class EvilInstrumentation extends Instrumentation {


    Instrumentation mOrigin;


    public EvilInstrumentation(Instrumentation origin) {
        this.mOrigin = origin;
    }

    public ActivityResult execStartActivity(
            Context who, IBinder contextThread, IBinder token, Activity target,
            Intent intent, int requestCode, Bundle options) {
        Log.e("zhangyunpeng", "我要跳转了");
        try {
            Class aClass = Class.forName("android.app.Instrumentation");
            Method execStartActivity = aClass.getMethod("execStartActivity", Context.class, IBinder.class, IBinder.class, Activity.class,
                    Intent.class, int.class, Bundle.class);

            //偷偷修改要跳转到activity
            intent.setComponent(new ComponentName("com.example.myapplication", "com.example.myapplication.MainActivity3"));

            return (ActivityResult) execStartActivity.invoke(mOrigin, who, contextThread, token, target, intent, requestCode, options);
        } catch (Exception e) {
            Log.e("zhangyunpeng", "手机厂商修改了rom");
            e.printStackTrace();

        }
        return null;
    }
}
