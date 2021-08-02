package com.example.login.di

import com.example.common.network.RetrofitManager
import com.example.login.repo.LoginApi
import com.example.login.repo.LoginResp
import com.example.login.viewmoduel.LoginViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val modelLogin = module {
    single {
        RetrofitManager.initRetrofit().getService(LoginApi::class.java)
    }
    single {
        LoginResp(get())
    }

    viewModel {
        LoginViewModel(get())
    }
}