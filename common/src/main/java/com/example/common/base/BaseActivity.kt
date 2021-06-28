package com.example.common.base

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.example.common.widget.LoadingDialog
import com.example.common.widget.StatusBar

abstract class BaseActivity<T:ViewDataBinding> : AppCompatActivity() {
    var mBinding: T? = null
    private lateinit var mLoadingDialog: LoadingDialog
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        StatusBar().fitSystemBar(this)
        mLoadingDialog = LoadingDialog(this)
        mBinding = DataBindingUtil.setContentView(this,getLayoutId())
        initData(savedInstanceState)
    }

    abstract fun initData(savedInstanceState: Bundle?)

    abstract fun getLayoutId(): Int


    override fun onDestroy() {
        super.onDestroy()
        mBinding?.unbind()
    }

    /**
     * show 加载中
     */
    fun showLoading() {
        mLoadingDialog.showDialog(this)
    }

    /**
     * dismiss loading dialog
     */
    fun dismissLoading() {
        mLoadingDialog.dismissDialog()
    }


    /**
     * 设置toolbar名称
     */
    protected fun setToolbarTitle(view: TextView, title: String) {
        view.text = title
    }

    /**
     * 设置toolbar返回按键图片
     */
    protected fun setToolbarBackIcon(view: ImageView, id: Int) {
        view.setBackgroundResource(id)
    }

}