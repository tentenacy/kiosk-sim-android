package com.tenutz.kiosksim.data.repository.menu

import com.tenutz.kiosksim.data.datasource.api.dto.common.CommonCondition
import com.tenutz.kiosksim.data.datasource.api.dto.common.OptionGroupPrioritiesChangeRequest
import com.tenutz.kiosksim.data.datasource.api.dto.common.OptionGroupsDeleteRequest
import com.tenutz.kiosksim.data.datasource.api.dto.common.OptionGroupsMappedByRequest
import com.tenutz.kiosksim.data.datasource.api.dto.kiosk.menu.KioskMenusResponse
import com.tenutz.kiosksim.data.datasource.api.dto.kiosk.menu.KioskReviewMenusResponse
import com.tenutz.kiosksim.data.datasource.api.dto.kiosk.review.KioskMenuReviewCreateRequest
import com.tenutz.kiosksim.data.datasource.api.dto.menu.*
import io.reactivex.rxjava3.core.Single

interface MenuRepository {

    fun kioskMenus(): Single<Result<KioskMenusResponse>>

    fun mainMenus(
        mainCateCd: String,
        middleCateCd: String,
        subCateCd: String,
        commonCond: CommonCondition? = null
    ): Single<Result<MainMenusResponse>>

    fun mainMenu(
        mainCateCd: String,
        middleCateCd: String,
        subCateCd: String,
        mainMenuCd: String
    ): Single<Result<MainMenuResponse>>

    fun createMainMenu(
        mainCateCd: String,
        middleCateCd: String,
        subCateCd: String,
        request: MainMenuCreateRequest,
    ): Single<Result<Unit>>

    fun updateMainMenu(
        mainCateCd: String,
        middleCateCd: String,
        subCateCd: String,
        mainMenuCd: String,
        request: MainMenuUpdateRequest,
    ): Single<Result<Unit>>

    fun deleteMainMenu(
        mainCateCd: String,
        middleCateCd: String,
        subCateCd: String,
        mainMenuCd: String
    ): Single<Result<Unit>>

    fun deleteMainMenus(
        mainCateCd: String,
        middleCateCd: String,
        subCateCd: String,
        request: MenusDeleteRequest
    ): Single<Result<Unit>>

    fun changeMainMenuPriorities(
        mainCateCd: String,
        middleCateCd: String,
        subCateCd: String,
        request: MenuPrioritiesChangeRequest
    ): Single<Result<Unit>>

    fun mainMenuOptionGroups(
        mainCateCd: String,
        middleCateCd: String,
        subCateCd: String,
        mainMenuCd: String,
        commonCond: CommonCondition? = null,
    ): Single<Result<MainMenuOptionGroupsResponse>>

    fun mainMenuMappers(
        mainCateCd: String,
        middleCateCd: String,
        subCateCd: String,
        mainMenuCd: String
    ): Single<Result<MainMenuMappersResponse>>

    fun mapToOptionGroups(
        mainCateCd: String,
        middleCateCd: String,
        subCateCd: String,
        mainMenuCd: String,
        request: OptionGroupsMappedByRequest
    ): Single<Result<Unit>>

    fun deleteMainMenuMappers(
        mainCateCd: String,
        middleCateCd: String,
        subCateCd: String,
        mainMenuCd: String,
        request: OptionGroupsDeleteRequest,
    ): Single<Result<Unit>>

    fun changeMainMenuMapperPriorities(
        mainCateCd: String,
        middleCateCd: String,
        subCateCd: String,
        mainMenuCd: String,
        request: OptionGroupPrioritiesChangeRequest,
    ): Single<Result<Unit>>

    fun orderMenus(
        callNumber: String,
    ): Single<Result<KioskReviewMenusResponse>>

    fun createMenuReview(
        request: KioskMenuReviewCreateRequest,
    ): Single<Result<Unit>>
}