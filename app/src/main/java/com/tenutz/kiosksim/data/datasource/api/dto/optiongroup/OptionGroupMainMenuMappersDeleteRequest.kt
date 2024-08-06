package com.tenutz.kiosksim.data.datasource.api.dto.optiongroup

data class OptionGroupMainMenuMappersDeleteRequest(
    val optionGroupMainMenus: List<OptionGroupMainMenuMapperDelete>,
) {
    data class OptionGroupMainMenuMapperDelete(
        val mainCategoryCode: String,
        val middleCategoryCode: String,
        val subCategoryCode: String,
        val menuCode: String,
    )
}
