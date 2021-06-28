package com.example.common.utils

import android.content.Context
import android.widget.Toast

object ToastUtil {
    private var time:Long = 0
    private var oldMsg: String? = null

    fun show(msg:String){
        if (msg .equals(oldMsg)){
            create(msg)
            time = System.currentTimeMillis()
        }else{
            if (System.currentTimeMillis() - time > 2000){
                create(msg)
                time = System.currentTimeMillis()
            }
        }
        oldMsg = msg
    }

    private fun create(msg: String) {
        val context: Context? = AppUtil.getApplication()?.applicationContext
        val toast = Toast(context)

        toast.duration = Toast.LENGTH_LONG
        toast.setText(msg)
        toast.show()
    }
}


