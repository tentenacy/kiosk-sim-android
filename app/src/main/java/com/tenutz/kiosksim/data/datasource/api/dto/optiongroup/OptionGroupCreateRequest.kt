package com.tenutz.kiosksim.data.datasource.api.dto.optiongroup

data class OptionGroupCreateRequest(
    val optionGroupCode: String,
    val optionGroupName: String,
    val toggleSelect: Boolean,
    val required: Boolean,
)
