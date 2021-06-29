package com.example.common.base

import android.util.Log
import com.example.common.network.BaseResp
import com.example.common.network.DataState
import com.example.common.network.ResState
import com.example.common.network.StateLiveData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.*
import java.io.IOException

class BaseRepository {
    //默认是public
    companion object{
        private const val TAG = "BaseRepository"
    }


    /**
     * 方式一
     * repo 请求数据的公共方法，
     * 在不同状态下先设置 baseResp.dataState的值，最后将dataState 的状态通知给UI
     * @param block api的请求方法
     * @param stateLiveData 每个请求传入相应的LiveData，主要负责网络状态的监听
     */
    suspend fun <T:Any> executeResp(
      block:suspend() -> BaseResp<T>,
      stateLiveData: StateLiveData<T>
    ){
        var baseResp = BaseResp<T>()
        try {
            baseResp.dataState = DataState.STATE_LOADING
            val invoke = block.invoke()
            baseResp = invoke
            if (baseResp.errorCode == 0){
                if (baseResp.errorCode == null || baseResp.data is List<*> && (baseResp.data as List<*>).size == 0){
                    baseResp.dataState = DataState.STATE_EMPTY
                }else{
                    baseResp.dataState = DataState.STATE_SUCCESS
                }
            }else{
                baseResp.dataState = DataState.STATE_FAILED
            }
        }catch (e:Exception){
            baseResp.dataState = DataState.STATE_ERROR
            baseResp.error = e
        }finally {
            stateLiveData.postValue(baseResp)
        }
    }

    /**
     * 方式二：结合Flow请求数据。
     * 根据Flow的不同请求状态，如onStart、onEmpty、onCompletion等设置baseResp.dataState状态值，
     * 最后通过stateLiveData分发给UI层。
     *
     * @param block api的请求方法
     * @param stateLiveData 每个请求传入相应的LiveData，主要负责网络状态的监听
     */
    suspend fun <T:Any> executeReqWithFlow(
        block:suspend() -> BaseResp<T>,
        stateLiveData: StateLiveData<T>
    ){
        var baseResp =  BaseResp<T>()
        flow {
            val resp = block.invoke()
            baseResp = resp
            baseResp.dataState = DataState.STATE_SUCCESS
            stateLiveData.postValue(baseResp)
            emit(resp)
        }
            .flowOn(Dispatchers.IO)
            .onStart {
                baseResp.dataState = DataState.STATE_LOADING
                stateLiveData.postValue(baseResp)
            }
            .onEmpty {
                baseResp.dataState = DataState.STATE_EMPTY
                stateLiveData.postValue(baseResp)
            }
            .catch {
                exception ->
                run{
                    exception.printStackTrace()
                    baseResp.dataState = DataState.STATE_ERROR
                    baseResp.error = exception
                    stateLiveData.postValue(baseResp)
                }
            }
            .collect{
                stateLiveData.postValue(baseResp)
            }
    }

    /**
     * @deprecated Use {@link executeResp} instead.
     */
    suspend fun <T : Any> executeResp(
        resp: BaseResp<T>,
        successBlock: (suspend CoroutineScope.() -> Unit)? = null,
        errorBlock: (suspend CoroutineScope.() -> Unit)? = null
    ): ResState<T> {
        return coroutineScope {
            if (resp.errorCode == 0) {
                successBlock?.let { it() }
                ResState.Success(resp.data!!)
            } else {
                Log.d(TAG, "executeResp: error")
                errorBlock?.let { it() }
                ResState.Error(IOException(resp.errorMsg))
            }
        }
    }

}