package com.coair.base.utli.serialport

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.blankj.utilcode.util.LogUtils
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.math.BigDecimal

class TemperatureViewModel : SerialPortViewModel() {
    val receiveLivaData = MutableLiveData<Float>()
    private val temperatureList = mutableListOf<TemperatureResult>()


    private val queryTimes = 3
    private var inQueryFlag = false
    fun queryTemperature() {
        if (!inQueryFlag) {
            LogUtils.e("开始 $queryTimes 次查询")
            inQueryFlag = true
            viewModelScope.launch {
                repeat(queryTimes) {
                    sendParams(QUERY_TEMPERATURE)
                    delay(250)
                }
                LogUtils.e("结束 $queryTimes 次查询")
                val temper = BigDecimal(parseTempList(temperatureList).toDouble()).setScale(
                    2,
                    BigDecimal.ROUND_HALF_UP
                ).toFloat()
                receiveLivaData.postValue(temper)
                temperatureList.clear()
                inQueryFlag = false
            }

        }
    }

    override fun parseSerialDataPacket(bytes: List<Byte>) {
        TemperatureResult().apply {
            status = bytes[1].toInt()
            temper = (bytes[2].toUByte().toInt() + bytes[3].toUByte().toInt() * 16 * 16) / 100f
        }
            .also {
                temperatureList.add(it)
            }
    }


    private fun parseTempList(temperatureList: List<TemperatureResult>): Float {
        val validData = temperatureList
            .filter {
                it.status == 0
            }

        val listOk = validData
            .filter {
                rightTemperature(it.temper)
            }

        val listNotOk = validData
            .filter {
                !rightTemperature(it.temper)
            }
        return if (listOk.size >= listNotOk.size)
            listOk.average { temper }
        else
            listNotOk.average { temper }
    }

    fun rightTemperature(temperature: Float): Boolean {
        return temperature <= 37.2F
    }


}

inline fun <T> Collection<T>.average(transformations: T.() -> Float): Float {
    val sum = sumByDouble {
        it.transformations().toDouble()
    }
    return (sum / size).toFloat()
}

data class TemperatureResult(
    var status: Int = 0,
    var temper: Float = 0.0f
)


data class SerialPortCommand(
    val writeString: String,
    val resultLength: Int
)

val QUERY_TEMPERATURE = SerialPortCommand("0707aa55", 9)

fun main() {
    val b: Byte = 0xee.toByte()
    println(b)
    println(b.toInt())
    println(b.toUInt())
    println(b.toUByte().toInt())
}