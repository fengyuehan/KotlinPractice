package com.example.common.loadsir

import android.content.Context
import android.view.View
import com.example.common.R
import com.kingja.loadsir.callback.Callback

class EmptyCallback: Callback() {
    override fun onCreateView(): Int {
        return R.layout.base_layout_empty
    }

    override fun onReloadEvent(context: Context?, view: View?): Boolean {
        return false
    }
}