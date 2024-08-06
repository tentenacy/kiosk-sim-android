package com.tenutz.kiosksim.data.repository.sharing

import android.os.Parcelable
import kotlin.reflect.KClass

interface SharedRepository {

    fun putParcelable(value: Parcelable)
    fun <T: Parcelable>getParcelable(key: KClass<T>): T?
}