package com.tenutz.kiosksim.network.observer

interface TokenExpirationObserver {
    fun onRefreshTokenExpired()
}