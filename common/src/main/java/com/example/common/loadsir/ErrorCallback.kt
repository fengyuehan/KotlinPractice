package com.example.common.loadsir

import android.content.Context
import android.view.View
import com.example.common.R
import com.kingja.loadsir.callback.Callback

class ErrorCallback:Callback() {
    override fun onCreateView(): Int {
        return R.layout.base_layout_error
    }

    override fun onReloadEvent(context: Context?, view: View?): Boolean {
        return false
    }
}