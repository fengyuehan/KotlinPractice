package com.example.common.utils

import android.content.Context
import java.security.AccessControlContext

object AppHelper {

    lateinit var mContext: Context
    fun init(context: Context){
        this.mContext = context
    }
}