package com.example.kotlinpractice

import android.content.Intent
import android.os.Bundle
import com.example.common.base.BaseActivity
import com.example.kotlinpractice.databinding.ActivitySplashBinding

class SplashActivity: BaseActivity<ActivitySplashBinding>() {
    override fun initData(savedInstanceState: Bundle?) {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_splash
    }
}