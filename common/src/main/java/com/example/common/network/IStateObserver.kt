package com.example.common.network

import android.net.ParseException
import android.view.View
import androidx.lifecycle.Observer
import com.example.common.loadsir.EmptyCallback
import com.example.common.loadsir.ErrorCallback
import com.example.common.loadsir.LoadingCallback
import com.google.gson.JsonParseException
import com.kingja.loadsir.callback.Callback
import com.kingja.loadsir.callback.SuccessCallback
import com.kingja.loadsir.core.Convertor
import com.kingja.loadsir.core.LoadService
import com.kingja.loadsir.core.LoadSir
import org.json.JSONException
import retrofit2.HttpException
import java.io.InterruptedIOException
import java.net.ConnectException

abstract class IStateObserver<T>(view:View?):Observer<BaseResp<T>>,Callback.OnReloadListener{

    private var mLoadService:LoadService<Any>? = null

    init {
        if (view != null){
            mLoadService = LoadSir.getDefault().register(view,this, Convertor<BaseResp<T>> {
                t ->
                var resultCode:Class<out Callback> = SuccessCallback::class.java
                when(t.dataState){
                    //数据刚开始请求，loading
                    DataState.STATE_CREATE, DataState.STATE_LOADING -> resultCode = LoadingCallback::class.java
                    //请求成功
                    DataState.STATE_SUCCESS -> resultCode = SuccessCallback::class.java
                    //数据为空
                    DataState.STATE_EMPTY -> resultCode = EmptyCallback::class.java

                    DataState.STATE_FAILED, DataState.STATE_ERROR -> {
                        val error: Throwable? = t.error
                        onError(error)
                        //可以根据不同的错误类型，设置错误界面时的UI
                        if (error is HttpException) {
                            //网络错误
                        } else if (error is ConnectException) {
                            //无网络连接
                        } else if (error is InterruptedIOException) {
                            //连接超时
                        } else if (error is JsonParseException
                            || error is JSONException
                            || error is ParseException
                        ) {
                            //解析错误
                        } else {
                            //未知错误
                        }
                        resultCode = ErrorCallback::class.java
                    }
                    DataState.STATE_COMPLETED, DataState.STATE_UNKNOWN -> {
                    }
                    else -> {
                    }
                }
                resultCode

            })
        }
    }

    abstract fun onError(error: Throwable?)

    override fun onChanged(t: BaseResp<T>) {
        when(t.dataState){
            DataState.STATE_SUCCESS -> {
                //请求成功，数据不为null
                onDataChange(t.data)
            }

            DataState.STATE_EMPTY -> {
                //数据为空
                onDataEmpty()
            }

            DataState.STATE_FAILED, DataState.STATE_ERROR -> {
                //请求错误
                t.error?.let { onError(it) }
            }
            else -> {
            }
        }
    }

    abstract fun onDataEmpty()

    abstract fun onDataChange(data: T?)

}