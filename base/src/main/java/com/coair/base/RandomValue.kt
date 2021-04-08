package com.coair.base

fun getRandomName(): String {
    return lastNames.random() + firstNames.random()
}

val address by lazy {
    listOf("光谷1路", "光谷2路", "光谷3路", "光谷4路", "光谷5路")
}
val lastNames by lazy {
    listOf("张", "李", "王", "赵", "杨")
}
val firstNames by lazy {
    listOf("一", "二", "三", "四", "五")
}