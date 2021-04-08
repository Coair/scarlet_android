package com.coair.base.utli.mmkv

import android.os.Parcelable
import android.util.Log
import com.tencent.mmkv.MMKV
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

/**
 * 只允许使用在 object 中的 property 上，持久化是有性能消耗的，谨慎使用
 */
//inline fun <reified R : Any, reified T> persistence(default: T) =
//    object : ReadWriteProperty<R, T> {
//        private val Tag = "MMKV持久化"
//        private val kv = MMKV.defaultMMKV()
//
//        override fun getValue(thisRef: R, property: KProperty<*>): T {
//            val key = "${thisRef::class.simpleName}.${property.name}"
//           val  value = when (T::class) {
//                Int::class -> kv.decodeInt(key, default as Int) as T
//                Boolean::class -> kv.decodeBool(key, default as Boolean) as T
//                Long::class -> kv.decodeLong(key, default as Long) as T
//                Float::class -> kv.decodeFloat(key, default as Float) as T
//                Double::class -> kv.decodeDouble(key, default as Double) as T
//                String::class -> kv.decodeString(key, default as String) as T
//                else -> {
//                    throw IllegalStateException("Only basic types are supported")
//                }
//            }
//
//            return value
//            //                    .also { Log.i(Tag, "取出 $key ： $it") }
//        }
//
//        override fun setValue(thisRef: R, property: KProperty<*>, value: T) {
//            val key = "${thisRef::class.simpleName}.${property.name}"
//                .also { Log.i(Tag, "保存 $it ： $value") }
//            when (value) {
//                is Int -> kv.encode(key, value)
//                is Boolean -> kv.encode(key, value)
//                is Long -> kv.encode(key, value)
//                is Float -> kv.encode(key, value)
//                is Double -> kv.encode(key, value)
//                is String -> kv.encode(key, value)
//                else -> {
//                    throw IllegalStateException("Only basic types are supported")
//                }
//            }
//        }
//    }


/**
 * Int类型的MMKV持久化委托
 * @Author coair
 * @Date 2021/1/29
 */
inline fun <reified R : Any> persistence(default: Int) =
    object : ReadWriteProperty<R,Int> {
        private val Tag = "MMKV持久化"
        private val kv = MMKV.defaultMMKV()

        override fun getValue(thisRef: R, property: KProperty<*>): Int {
            val key = "${thisRef::class.simpleName}.${property.name}"
            return kv.decodeInt(key, default)
//                .also { Log.i(Tag, "取出 $key ： $it") }
        }

        override fun setValue(thisRef: R, property: KProperty<*>, value: Int) {
            val key = "${thisRef::class.simpleName}.${property.name}"
                .also { Log.i(Tag, "保存 $it ： $value") }
            kv.encode(key, value)
        }
    }

/**
 * Boolean类型的MMKV持久化委托
 * @Author coair
 * @Date 2021/1/29
 */
inline fun <reified R : Any> persistence(default: Boolean) =
    object : ReadWriteProperty<R,Boolean> {
        private val Tag = "MMKV持久化"
        private val kv = MMKV.defaultMMKV()

        override fun getValue(thisRef: R, property: KProperty<*>): Boolean {
            val key = "${thisRef::class.simpleName}.${property.name}"
            return kv.decodeBool(key, default)
//                .also { Log.i(Tag, "取出 $key ： $it") }
        }

        override fun setValue(thisRef: R, property: KProperty<*>, value: Boolean) {
            val key = "${thisRef::class.simpleName}.${property.name}"
                .also { Log.i(Tag, "保存 $it ： $value") }
            kv.encode(key, value)
        }
    }

/**
 * String类型的MMKV持久化委托
 * @Author coair
 * @Date 2021/1/29
 */
inline fun <reified R : Any> persistence(default: String) =
    object : ReadWriteProperty<R,String> {
        private val Tag = "MMKV持久化"
        private val kv = MMKV.defaultMMKV()

        override fun getValue(thisRef: R, property: KProperty<*>): String {
            val key = "${thisRef::class.simpleName}.${property.name}"
            return kv.decodeString(key, default)
//                .also { Log.i(Tag, "取出 $key ： $it") }
        }

        override fun setValue(thisRef: R, property: KProperty<*>, value: String) {
            val key = "${thisRef::class.simpleName}.${property.name}"
                .also { Log.i(Tag, "保存 $it ： $value") }
            kv.encode(key, value)
        }
    }


/**
 * Float类型的MMKV持久化委托
 * @Author coair
 * @Date 2021/1/29
 */
inline fun <reified R : Any> persistence(default: Float) =
    object : ReadWriteProperty<R,Float> {
        private val Tag = "MMKV持久化"
        private val kv = MMKV.defaultMMKV()

        override fun getValue(thisRef: R, property: KProperty<*>): Float {
            val key = "${thisRef::class.simpleName}.${property.name}"
            return kv.decodeFloat(key, default)
//                .also { Log.i(Tag, "取出 $key ： $it") }
        }

        override fun setValue(thisRef: R, property: KProperty<*>, value: Float) {
            val key = "${thisRef::class.simpleName}.${property.name}"
                .also { Log.i(Tag, "保存 $it ： $value") }
            kv.encode(key, value)
        }
    }
/**
 * Double类型的MMKV持久化委托
 * @Author coair
 * @Date 2021/1/29
 */
inline fun <reified R : Any> persistence(default: Double) =
    object : ReadWriteProperty<R,Double> {
        private val Tag = "MMKV持久化"
        private val kv = MMKV.defaultMMKV()

        override fun getValue(thisRef: R, property: KProperty<*>): Double {
            val key = "${thisRef::class.simpleName}.${property.name}"
            return kv.decodeDouble(key, default)
//                .also { Log.i(Tag, "取出 $key ： $it") }
        }

        override fun setValue(thisRef: R, property: KProperty<*>, value: Double) {
            val key = "${thisRef::class.simpleName}.${property.name}"
                .also { Log.i(Tag, "保存 $it ： $value") }
            kv.encode(key, value)
        }
    }

/**
 * Long类型的MMKV持久化委托
 * @Author coair
 * @Date 2021/1/29
 */
inline fun <reified R : Any> persistence(default: Long) =
    object : ReadWriteProperty<R,Long> {
        private val Tag = "MMKV持久化"
        private val kv = MMKV.defaultMMKV()

        override fun getValue(thisRef: R, property: KProperty<*>): Long {
            val key = "${thisRef::class.simpleName}.${property.name}"
            return kv.decodeLong(key, default)
//                .also { Log.i(Tag, "取出 $key ： $it") }
        }

        override fun setValue(thisRef: R, property: KProperty<*>, value: Long) {
            val key = "${thisRef::class.simpleName}.${property.name}"
                .also { Log.i(Tag, "保存 $it ： $value") }
            kv.encode(key, value)
        }
    }

/**
 * 每次读取都会占用资源，尽量减少读写次数，如下例
 *
 * val book=AppConfig.mainBook
 *
 * book.run {
 * doSomeWork()
 * }
 *
 * AppConfig.mainBook=book
 */
inline fun <reified R : Any, reified T : Parcelable> persistence(default: T) =
    object : ReadWriteProperty<R, T> {
        private val Tag = "MMKV持久化"

        private val kv = MMKV.defaultMMKV()

        private var value = default

        override fun getValue(thisRef: R, property: KProperty<*>): T {
            val key = "${thisRef::class.simpleName}.${property.name}"
            val persistenceValue = kv.decodeParcelable(key, T::class.java, default)

            if (persistenceValue == null) {
                setValue(thisRef, property, default)
                    .also { Log.i(Tag, "初始化 $key ： $it") }
            } else {
                value = persistenceValue
//                    .also { Log.i(Tag, "取出 $key ： $it") }
            }

            return value
        }

        override fun setValue(thisRef: R, property: KProperty<*>, value: T) {
            val key = "${thisRef::class.simpleName}.${property.name}"
                .also { Log.i(Tag, "保存 $it ： $value") }
            kv.encode(key, value)
        }
    }