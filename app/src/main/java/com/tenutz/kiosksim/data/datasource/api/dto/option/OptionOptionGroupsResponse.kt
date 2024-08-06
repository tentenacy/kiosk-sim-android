package com.tenutz.kiosksim.data.datasource.api.dto.option

data class OptionOptionGroupsResponse(
    val optionOptionGroups: List<OptionOptionGroup>,
) {
    data class OptionOptionGroup(
        val optionGroupCode: String,
        val optionName: String?,
        val toggleSelect: Boolean,
        val required: Boolean,
    )
}
