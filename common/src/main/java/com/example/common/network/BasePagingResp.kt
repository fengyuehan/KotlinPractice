package com.example.common.network

/**
 * 必须要有一个参数的构造函数
 */
data class BasePagingResp<T>(
    var curPage: Int,
    var offset: Int,
    var over: Boolean,
    var pageCount: Int,
    var size: Int,
    var total: Int,
    var datas: T
)