package com.recurve.coroutines

import kotlinx.coroutines.*

/**
 * asynchronous tasks
 * [delay] delay time
 * [action] io task
 * @return current function, can be used to monitor execution results.
 *         need to be set before the task is completed.
 *
 * @method: [io]
 * @author create by Tang
 * @date 2019-05-16 22:54
 */

fun<R> io(delay: Long = 0, action: () -> R) : ((result: ( R ) -> Unit) -> Unit ){

    /**
     * coroutine asynchronous tasks
     */
    val ioDeferred= GlobalScope.async(Dispatchers.IO) {
            delay(delay)
            return@async action()
    }

    /**
     * listening callback method settings
     */
    val resultCallback : ((result: ( R ) -> Unit) -> Unit) = {
        GlobalScope.launch(Dispatchers.Main){
            it(ioDeferred.await())
        }
    }

    return {
        resultCallback(it)
    }
}

/**
 * task execution result assisted monitoring method
 *
 * @method: [io]
 * @author create by Tang
 * @createTime: 2020/1/6 7:30 PM
 */
private fun <R> resultListener(action: ( R )-> Unit) : (R) -> Unit{
    return {
        action(it)
    }
}

