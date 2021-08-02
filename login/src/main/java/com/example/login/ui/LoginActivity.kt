package com.example.login.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.view.View
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.blankj.utilcode.util.ToastUtils
import com.example.common.Constants
import com.example.common.base.BaseVmActivity
import com.example.common.network.IStateObserver
import com.example.common.utils.SpUtils
import com.example.common.widget.LiveDataBus
import com.example.login.R
import com.example.login.databinding.ActivityLoginBinding
import com.example.login.repo.LoginResp
import com.example.login.viewmoduel.LoginViewModel
import com.example.service.UserInfo
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_login.et_password
import kotlinx.android.synthetic.main.activity_login.et_user_name
import kotlinx.android.synthetic.main.activity_register.*
import org.koin.androidx.viewmodel.ext.android.viewModel
@Route(path = Constants.PATH_LOGIN)
class LoginActivity : BaseVmActivity<ActivityLoginBinding>() {
    private val mViewModel:LoginViewModel by viewModel()
    override fun initData() {
        initToolBar()
        initListener()

    }

    private fun initListener() {
        mViewModel.loginLiveData.observe(this,object :IStateObserver<UserInfo>(null){
            override fun onError(error: Throwable?) {
                TODO("Not yet implemented")
            }

            override fun onDataEmpty() {
                TODO("Not yet implemented")
            }

            override fun onReload(v: View?) {
                TODO("Not yet implemented")
            }

            override fun onDataChange(data: UserInfo?) {
                ToastUtils.showLong("登录成功")
                SpUtils.put(Constants.SP_KEY_USER_INFO_NAME,data?.nickname)
                LiveDataBus.get().with(Constants.KEY_LIVEDATA_BUS_LOGIN).postValue(data)
                finish()
            }

        })
        et_password.addTextChangedListener(object :TextWatcher{
            override fun afterTextChanged(s: Editable?) {
                if (TextUtils.isEmpty(s.toString())){
                    ToastUtils.showLong("用户名不能为空")
                    return
                }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                TODO("Not yet implemented")
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                TODO("Not yet implemented")
            }

        })
        et_user_name.addTextChangedListener(object :TextWatcher{
            override fun afterTextChanged(s: Editable?) {
                if (TextUtils.isEmpty(s.toString())){
                    ToastUtils.showLong("密码不能为空")
                    return
                }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                TODO("Not yet implemented")
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                TODO("Not yet implemented")
            }

        })
        mBinding?.run {
            btLogin?.setOnClickListener {
                mViewModel.login(etUserName.toString().trim(), etPassword.toString().trim())
            }
            tvToRegister?.setOnClickListener {
                ARouter.getInstance().build(Constants.PATH_REGISTER).navigation()
            }
        }

    }

    private fun initToolBar() {
        mBinding?.run {
            setToolbarBackIcon(llToolbarLogin?.ivBack,R.drawable.ic_back_clear)
            setToolbarTitle(llToolbarLogin.tvTitle,"登录")
            llToolbarLogin.ivBack.setOnClickListener {
                finish()
            }
        }
    }

    override fun getLayoutId(): Int = R.layout.activity_login


}
