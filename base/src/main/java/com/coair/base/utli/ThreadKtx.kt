package com.coair.base.utli

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.*
import java.util.concurrent.TimeUnit


/**
 * 延迟任务函数
 *
 * 延迟[l]毫秒后在主线程执行[task]
 */
fun timerOnMain(l: Long, task: () -> Unit) =
    GlobalScope.launch {
        delay(l)
        withContext(Dispatchers.Main) {
            task()
        }
    }

fun ViewModel.onIo(block: () -> Unit) =
    viewModelScope.launch {
        withContext(Dispatchers.IO) {
            block()
        }
    }


fun ViewModel.timerOnMain(
    l: Long,
    block: () -> Unit
) =
    viewModelScope.launch {
        delay(l)
        withContext(Dispatchers.Main) {
            block()

        }
    }


fun timerOnSync(l: Long, task: () -> Unit) =
    GlobalScope.launch {
        withContext(Dispatchers.Default) {
            delay(l)
            if (isActive) {
                task()
            }
        }
    }

fun main() {
    runBlocking {
        try {
            withTimeout(1000L) {
                repeat(10) {
                    delay(500)
                    sayHello()
                }
            }
        } catch (e: TimeoutCancellationException) {
            println(e.message)

        }
    }

    println("main end")
}


fun sayHello() {
    println("hey hey ")
    println(Thread.currentThread())
}

suspend fun doSomethingUsefulOne(): Int {
    delay(1000L) // pretend we are doing something useful here
    return 13
}

suspend fun doSomethingUsefulTwo(): Int {
    delay(1000L) // pretend we are doing something useful here, too
    return 29
}

suspend fun concurrentSum(): Int = coroutineScope {
    val one = async { doSomethingUsefulOne() }
    val two = async { doSomethingUsefulTwo() }
    one.await() + two.await()
}


class SerialPortCommunication(

)