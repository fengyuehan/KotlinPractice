package com.example.common.loadsir

import android.content.Context
import android.view.View
import com.example.common.R
import com.kingja.loadsir.callback.Callback

class LoadingCallback :Callback() {
    override fun onCreateView(): Int {
        return R.layout.base_layout_loading
    }

    override fun getSuccessVisible(): Boolean {
        return true
    }

    override fun onAttach(context: Context?, view: View?) {
        super.onAttach(context, view)
    }


    override fun onDetach() {
        super.onDetach()
    }

    override fun onReloadEvent(context: Context?, view: View?): Boolean {
        return false
    }
}