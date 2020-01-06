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

    var rListener: ((R) -> Unit)? = null

    /**
     * listening to coroutines execution results
     */
    val taskResult: (R) -> Unit = { r ->
        rListener?.invoke(r)
    }

    /**
     * listening callback method settings
     */

    val resultCallback : ((result: ( R ) -> Unit) -> Unit) = {
        rListener = resultListener(it)
    }

    val result: ((result: ( R ) -> Unit) -> Unit ) = {
        resultCallback(it)
    }

    /**
     * coroutine asynchronous tasks
     */
    runBlocking {
        GlobalScope.launch(Dispatchers.Main) {
            val r = GlobalScope.async(Dispatchers.IO) {
                delay(delay)
                return@async action()
            }.await()
            taskResult(r)
        }
    }
    return result
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

