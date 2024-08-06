package com.tenutz.kiosksim.network.subject

interface ISubject<T> {
    fun registerObserver(observer: T)
    fun unregisterObserver(observer: T)
}