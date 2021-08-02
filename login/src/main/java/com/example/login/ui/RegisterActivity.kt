package com.example.login.ui

import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.view.View
import androidx.lifecycle.Observer
import com.alibaba.android.arouter.facade.annotation.Route
import com.blankj.utilcode.util.ToastUtils
import com.example.common.Constants
import com.example.common.base.BaseVmActivity
import com.example.common.network.IStateObserver
import com.example.login.R
import com.example.login.databinding.ActivityRegisterBinding
import com.example.login.viewmoduel.LoginViewModel
import com.example.service.UserInfo
import kotlinx.android.synthetic.main.activity_register.*
import org.koin.androidx.viewmodel.ext.android.viewModel

@Route(path = Constants.PATH_REGISTER)
class RegisterActivity:BaseVmActivity<ActivityRegisterBinding>() {
    /**
     * 不能同名，会报错
     */
    private val mViewModel:LoginViewModel by viewModel()

    override fun initData() {
        initToolBar()

        mBinding?.run {
            etUserName.addTextChangedListener(object :TextWatcher{
                override fun afterTextChanged(s: Editable?) {
                    if (TextUtils.isEmpty(s.toString())){
                        ToastUtils.showLong("用户名不能为空")
                        return
                    }
                }

                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {
                    TODO("Not yet implemented")
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    TODO("Not yet implemented")
                }

            })
            etPassword.addTextChangedListener(object :TextWatcher{
                override fun afterTextChanged(s: Editable?) {
                    if (TextUtils.isEmpty(s.toString())){
                        ToastUtils.showLong("密码不能为空")
                        return
                    }
                }

                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {
                    TODO("Not yet implemented")
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    TODO("Not yet implemented")
                }

            })
            etIvPasswordSure.addTextChangedListener(object :TextWatcher{
                override fun afterTextChanged(s: Editable?) {
                    if (TextUtils.isEmpty(s.toString())){
                        ToastUtils.showLong("密码不能为空")
                        return
                    }
                }

                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {
                    TODO("Not yet implemented")
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    TODO("Not yet implemented")
                }

            })
            btRegister?.setOnClickListener {
                mViewModel.register(etUserName.text.toString(),etPassword.text.toString(),etIvPasswordSure.text.toString())
            }
        }

        mViewModel.registerLiveData.observe(this,Observer{
            ToastUtils.showLong("注册成功")
            finish()
        })
    }

    private fun initToolBar() {
        mBinding?.run {
            setToolbarTitle(llToolbarLogin?.tvTitle,"注册")
            setToolbarBackIcon(llToolbarLogin?.ivBack,R.drawable.ic_back_clear)
            llToolbarLogin.ivBack.setOnClickListener {
                finish()
            }
        }
    }

    override fun getLayoutId(): Int  = R.layout.activity_register
}