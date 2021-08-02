package com.example.login.repo

import com.example.common.base.BaseRepository
import com.example.common.network.StateLiveData
import com.example.service.UserInfo

class LoginResp(private val service:LoginApi) : BaseRepository() {
    suspend fun login(userName:String,passwprd:String,stateLiveData: StateLiveData<UserInfo>){
        executeResp({service.login(userName,passwprd)},stateLiveData)
    }

    suspend fun register(
        userName: String,
        passwprd: String,
        rePassword:String,
        stateLiveData: StateLiveData<UserInfo>
    ){
        executeResp({service.register(userName,passwprd,rePassword)},stateLiveData)
    }
}