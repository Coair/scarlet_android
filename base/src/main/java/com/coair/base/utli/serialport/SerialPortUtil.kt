package com.coair.base.utli.serialport

import android.content.Context
import android.util.Log
import android_serialport_api.SerialPort
import com.blankj.utilcode.util.ToastUtils
import com.coair.base.R
import com.coair.base.utli.event.bus

import java.io.File
import java.io.IOException
import java.io.InputStream
import java.io.OutputStream
import java.security.InvalidParameterException

/**
 * @author fanming
 *
 * 防火墙权限会导致串口权限无法获取
 * cd dev ==> setenforce 0
 * 串口工具类
 */
class SerialPortUtil(
    private val mContext: Context,
    var path: String,//串口地址
    var baudrate: Int//波特率
) {
    //打开串口的输入输出
    private var mOutputStreamBox: OutputStream? = null
    private var mInputStreamBox: InputStream? = null

    //等待串口数据线程
    private var mReadBoxThread: ReadBoxThread? = null

    //串口
    private var serialPort: SerialPort? = null

//    //息屏开屏监听
//    private val serialReceiver: SerialPortReceiver? = null
//    //是否第一次初始化串口
//    private val firstRegisterBox = true

    //串口的打开关闭状态
    private var portOpenFlag = false

    /**
     * 发送参数
     * @author chensiqi
     * @time 2019/3/26
     */
    fun sendParams(cmd: String) {
        if (mOutputStreamBox == null) {
            return
        }
        try {
            if (cmd.length == 1) {
                mOutputStreamBox!!.write(cmd.toByteArray())
            } else if (cmd.startsWith("nls") || cmd.startsWith("NLS")) {
                mOutputStreamBox!!.write(cmd.toByteArray())
            } else {
                mOutputStreamBox!!.write(decode(cmd))
            }
        } catch (e: Exception) {
            e.printStackTrace()
            bus.post(SerialPortMsg.DataSendError("串口指令发送失败！"))
        }

    }

    /**
     * 字符串转成字节流
     */
    private fun decode(src: String): ByteArray {
        var m = 0
        var n = 0
        val byteLen = src.length / 2// 每两个字符描述一个字节
        val ret = ByteArray(byteLen)
        for (i in 0 until byteLen) {
            m = i * 2
            n = m + 1
            val first = src.substring(m, n)
            val second = src.substring(n, n + 1)
            val intVal = Integer.decode("0x$first$second")
            ret[i] = intVal.toByte()
        }
        return ret
//            .also { bytes -> Log.e("准备写入串口", BytesUtil.bytes2hex01(bytes)) }
    }


    /**
     * 打开串口
     */
    fun openPort() {
        try {
            /* Check parameters */
            if (path.isEmpty() || baudrate == -1) {
                throw InvalidParameterException()
            }
            /* Open the serial port */
            Log.e("尝试打开串口", "path: $path  ,波特率 ： $baudrate")
            serialPort = SerialPort(File(path), baudrate, 0)

            mOutputStreamBox = serialPort!!.outputStream
            mInputStreamBox = serialPort!!.inputStream

            /* Create a serial rec buf  thread */
            mReadBoxThread = ReadBoxThread()
            portOpenFlag = true
            mReadBoxThread!!.start()
            bus.post(SerialPortMsg.PortOpen())
        } catch (e: SecurityException) {
            e.printStackTrace()
            val errorMsg = path + "\n" + mContext.getString(R.string.error_security)
            bus.post(SerialPortMsg.PortOpenError(errorMsg))
            ToastUtils.showLong(errorMsg)
        } catch (e: IOException) {
            e.printStackTrace()
            val errorMsg = path + "\n" + mContext.getString(R.string.error_unknown)
            bus.post(SerialPortMsg.PortOpenError(errorMsg))
            ToastUtils.showLong(path + "\n" + mContext.getString(R.string.error_unknown))
        } catch (e: InvalidParameterException) {
            e.printStackTrace()
            val errorMsg = path + "\n" + mContext.getString(R.string.error_configuration)
            bus.post(SerialPortMsg.PortOpenError(errorMsg))
            ToastUtils.showLong(path + "\n" + mContext.getString(R.string.error_configuration))
        } catch (e: Exception) {
            bus.post(SerialPortMsg.PortOpenError("$path 未知异常"))
            ToastUtils.showLong("$path 未知异常")
        }

    }


    /**
     * 串口关闭
     */
    fun closeSerialPort() {
        if (serialPort != null) {
            //先控制线程flag，循环结束
            portOpenFlag = false
            serialPort!!.close()
            bus.post(SerialPortMsg.PortClose())
            serialPort = null
        } else {
            bus.post(SerialPortMsg.PortCloseError("串口未初始化！"))
        }
    }

    /**
     * 读串口数据的子线程
     *
     * @author fanming
     */
    private inner class ReadBoxThread : Thread() {
        override fun run() {
            while (portOpenFlag) {
                val size: Int
                try {
                    val buffer = ByteArray(4096)
                    if (mInputStreamBox == null) return
                    /* read会一直等待数据，如果要判断是否接受完成，只有设置结束标识，或作其他特殊的处理 */
                    size = mInputStreamBox!!.read(buffer)
                    if (size > 0) {
                        bus.post(SerialDataPacket(buffer, size, path))
                        Log.i(
                            "ReadBoxThread",
                            "read[$size]bytes :${BytesUtil.bytes2hex03(buffer, size)}"
                        )
                    }
                } catch (e: IOException) {
                    e.printStackTrace()
                    return
                }
            }
            Log.i("ReadBoxThread", "---ReadBoxThread--- 读取结束")
        }
    }
}


sealed class SerialPortMsg(val msg: String) {
    class DataSendError(s: String) : SerialPortMsg(s)
    class PortOpen() : SerialPortMsg("打开了")
    class PortOpenError(s: String) : SerialPortMsg(s)
    class PortClose() : SerialPortMsg("关闭了")
    class PortCloseError(s: String) : SerialPortMsg(s)
}

interface PortInterface {

    /**
     *
     * @param buffer  返回的字节数据
     * @param size    返回的字节长度
     * @param path    串口名，如果有多个串口需要识别是哪个串口返回的数据（传或不传可以根据自己的编码习惯）
     */
    fun onSerialDataReceived(buffer: ByteArray, size: Int, path: String)
}

class SerialDataPacket(
    val buffer: ByteArray,
    val size: Int,
    val path: String
)

val emptySerialDataPacket = SerialDataPacket(byteArrayOf(), 0, "")

operator fun SerialDataPacket.plus(that: SerialDataPacket?): SerialDataPacket {
    return if (that != null) {
        if (path != that.path && this != emptySerialDataPacket) {
            throw Exception("Not the same path，Can't plus")
        }
        val newSize = size + that.size
        val newBuffer = buffer + that.buffer
        SerialDataPacket(newBuffer, newSize, that.path)
    } else {
        this
    }
}