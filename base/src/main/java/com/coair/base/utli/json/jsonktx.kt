package com.coair.base.utli.json

import com.blankj.utilcode.util.GsonUtils

fun Any.gson() = GsonUtils.toJson(this)


inline fun <reified T> String.to(): T {
    return GsonUtils.fromJson(this, T::class.java)
}


inline fun <reified T> byGson(json: String): T {
    return GsonUtils.fromJson(json, T::class.java)
}