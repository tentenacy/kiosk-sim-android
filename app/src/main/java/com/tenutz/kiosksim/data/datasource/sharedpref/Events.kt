package com.tenutz.kiosksim.data.datasource.sharedpref

import androidx.lifecycle.MutableLiveData

object Events {
    val existsNewNotification: MutableLiveData<Boolean> by lazy {
        MutableLiveData<Boolean>(false)
    }
}