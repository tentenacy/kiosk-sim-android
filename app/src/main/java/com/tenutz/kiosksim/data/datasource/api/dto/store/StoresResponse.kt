package com.tenutz.kiosksim.data.datasource.api.dto.store

data class StoresResponse(
    val stores: List<Store>,
) {
    data class Store(
        val storeCode: String?,
        val storeName: String?,
    )
}
