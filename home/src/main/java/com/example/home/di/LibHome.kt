package com.example.home.di

import com.example.common.network.RetrofitManager
import com.example.home.api.HomeService
import com.example.home.db.AppDatabase
import com.example.home.repo.HomeRepo
import com.example.home.viewmodel.ArticleViewModel
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val moduleHome = module {
    single {
        RetrofitManager.initRetrofit().getService(HomeService::class.java)
    }

    single {
        AppDatabase.get(androidApplication())
    }

    single {
        HomeRepo(get(),get())
    }

    viewModel { ArticleViewModel(get()) }
}