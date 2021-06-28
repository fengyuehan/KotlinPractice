package com.example.common.utils

import android.app.Application
import java.lang.reflect.InvocationTargetException

object AppUtil {
    private var sApp: Application? = null

    fun getApplication(): Application? {
        if (sApp == null){
            try {
                /**
                 * Class.forName("android.app.ActivityThread")相当于拿到ActivityThread类
                 * .getMethod("currentApplication")通过类拿到currentApplication方法
                 * 因为是静态方法，invoke的第一参数为null，currentApplication（）没有参数，所以invoke(null,null as Array<Any?>?)也是为null
                 */
                sApp = Class.forName("android.app.ActivityThread")
                    .getMethod("currentApplication")
                    .invoke(null,null as Array<Any?>?) as Application
            } catch (e: IllegalAccessException) {
                e.printStackTrace()
            } catch (e: InvocationTargetException) {
                e.printStackTrace()
            } catch (e: NoSuchMethodException) {
                e.printStackTrace()
            } catch (e: ClassNotFoundException) {
                e.printStackTrace()
            }
        }
        return sApp
    }
}