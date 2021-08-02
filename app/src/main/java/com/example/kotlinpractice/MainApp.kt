package com.example.kotlinpractice

import android.app.Application
import androidx.multidex.BuildConfig
import com.alibaba.android.arouter.launcher.ARouter
import com.example.common.loadsir.EmptyCallback
import com.example.common.loadsir.ErrorCallback
import com.example.common.loadsir.LoadingCallback
import com.example.common.utils.AppHelper
import com.example.home.di.moduleHome
import com.example.login.di.modelLogin
import com.kingja.loadsir.core.LoadSir
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class MainApp:Application() {
    private val modules = arrayListOf(moduleHome,modelLogin)

    override fun onCreate() {
        super.onCreate()
        initARouter()
        initLoadSir()
        initKoin()
        AppHelper.init(this.applicationContext)
    }

    private fun initKoin() {
        startKoin {
            androidLogger()
            androidContext(this@MainApp)
            modules(modules = modules)
        }
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