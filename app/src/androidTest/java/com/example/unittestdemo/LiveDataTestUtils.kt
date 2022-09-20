package com.example.unittestdemo

import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit
import java.util.concurrent.TimeoutException

fun <T> getOrAwaitValue(liveData: LiveData<T>): T {
    var data: T? = null
    val latch = CountDownLatch(1)
    val observer = object : Observer<T> {
        override fun onChanged(t: T) {
            data = t
            liveData.removeObserver(this)
            latch.countDown()
        }
    }

    liveData.observeForever(observer)

    try {
        if (!latch.await(2, TimeUnit.SECONDS)) {
            throw TimeoutException("Live Data never gets its value")
        }
    } finally {
        liveData.removeObserver(observer)
    }

    return data as T
}

