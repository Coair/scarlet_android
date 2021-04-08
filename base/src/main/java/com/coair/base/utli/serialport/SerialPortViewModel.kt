package com.coair.base.utli.serialport

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.blankj.utilcode.util.LogUtils
import com.blankj.utilcode.util.Utils
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.math.BigDecimal

abstract class SerialPortViewModel : ViewModel() {
    var comPath = "/dev/ttyS3"

    //    var baudrate = 115200
    var baudrate = 9600

    private val serialPortUtil: SerialPortUtil by lazy {
        SerialPortUtil(Utils.getApp(), comPath, baudrate)
    }

    fun openPort() {
        serialPortUtil.baudrate = baudrate
        serialPortUtil.path = comPath
        serialPortUtil.openPort()
    }

    fun closePort() {
        serialPortUtil.closeSerialPort()
    }

    protected var expectedLength = 0
    fun sendParams(cmd: SerialPortCommand) {
        expectedLength = cmd.resultLength
        serialPortUtil.sendParams(cmd.writeString)
    }


    fun parseSerialDataPacket(serialDataPacket: SerialDataPacket) {
        mergeSerialDataPacket(serialDataPacket)
            .isWholePacket(expectedLength)
            ?.run {
                val ret = BytesUtil.bytes2hex03(buffer, size)
                LogUtils.eTag("串口回调hex：", ret + ",now time:${System.currentTimeMillis()}")
                val bytes = buffer.sliceArray(0 until expectedLength).toList()
                parseSerialDataPacket(bytes)
            }

    }

    abstract fun parseSerialDataPacket(bytes: List<Byte>)



    //处理分包问题
    var buffer: SerialDataPacket = emptySerialDataPacket
    private fun mergeSerialDataPacket(
        new: SerialDataPacket
    ): SerialDataPacket {
        return if (buffer == emptySerialDataPacket) {
            new
        } else {
            buffer + new
        }
    }

    private fun SerialDataPacket.isWholePacket(expectedLength: Int): SerialDataPacket? {
        if (size == expectedLength) {
            this@SerialPortViewModel.buffer = emptySerialDataPacket
            return this
        } else
            return null
    }

    override fun onCleared() {
        super.onCleared()
        closePort()
    }
}


//val QUERY_CENTER_TEMPERATURE = SerialPortCommand("eeb3fffcfdff", 24)
//val  QUERY_CENTER_TEMPERATURE =SerialPortCommand("eeb3fffcfdff",2_055)
