package com.tenutz.kiosksim.data.datasource.api.dto.common

data class OptionGroupPrioritiesChangeRequest(
    val optionGroups: List<OptionGroup>,
) {
    data class OptionGroup(
        val optionGroupCode: String,
        val priority: Int,
    )
}
