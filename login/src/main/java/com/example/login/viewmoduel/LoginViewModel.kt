package com.example.login.viewmoduel

import androidx.lifecycle.viewModelScope
import com.example.common.base.BaseViewModel
import com.example.common.network.StateLiveData
import com.example.login.repo.LoginResp
import com.example.service.UserInfo
import kotlinx.coroutines.launch

class LoginViewModel(private val repo:LoginResp):BaseViewModel(){
    val loginLiveData = StateLiveData<UserInfo>()
    val registerLiveData = StateLiveData<UserInfo>()

    fun login(userName:String,password:String){
        viewModelScope.launch {
            repo.login(userName,password,loginLiveData)
        }
    }

    fun register(userName: String,password: String,rePassword:String){
        viewModelScope.launch {
            repo.register(userName,password,rePassword,registerLiveData)
        }
    }
}