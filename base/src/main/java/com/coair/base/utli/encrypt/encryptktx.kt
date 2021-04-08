package com.coair.base.utli.encrypt

fun String.SMS4() = SMS4.encrypt(this, "UTF-8")

fun String.bySMS4() = SMS4.decrypt(this, "UTF-8")