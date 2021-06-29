package com.example.kotlinpractice

import android.app.Application
import androidx.multidex.BuildConfig
import com.alibaba.android.arouter.launcher.ARouter
import com.example.common.loadsir.EmptyCallback
import com.example.common.loadsir.ErrorCallback
import com.example.common.loadsir.LoadingCallback
import com.example.common.utils.AppHelper
import com.kingja.loadsir.core.LoadSir

class MainApp:Application() {
    //private val modules = arrayListOf(mo)

    override fun onCreate() {
        super.onCreate()
        initARouter()
        initLoadSir()
        initKoin()
        AppHelper.init(this.applicationContext)
    }

    private fun initKoin() {
        TODO("Not yet implemented")
    }

    private fun initLoadSir() {
        LoadSir.beginBuilder()
            .addCallback(ErrorCallback())
            .addCallback(LoadingCallback())
            .addCallback(EmptyCallback())
            .setDefaultCallback(LoadingCallback::class.java)
            .commit()
    }

    private fun initARouter() {
        ARouter.init(this)
        if (BuildConfig.DEBUG) {
            ARouter.openLog()
            ARouter.openDebug()
        }
    }
}