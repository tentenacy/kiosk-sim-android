package com.tenutz.kiosksim.data.repository.category

import com.tenutz.kiosksim.data.datasource.api.dto.category.CategoriesDeleteRequest
import com.tenutz.kiosksim.data.datasource.api.dto.category.CategoryPrioritiesChangeRequest
import com.tenutz.kiosksim.data.datasource.api.dto.category.MainCategoriesResponse
import com.tenutz.kiosksim.data.datasource.api.dto.category.MainCategoryCreateRequest
import com.tenutz.kiosksim.data.datasource.api.dto.category.MainCategoryResponse
import com.tenutz.kiosksim.data.datasource.api.dto.category.MainCategoryUpdateRequest
import com.tenutz.kiosksim.data.datasource.api.dto.category.MiddleCategoriesResponse
import com.tenutz.kiosksim.data.datasource.api.dto.category.MiddleCategoryCreateRequest
import com.tenutz.kiosksim.data.datasource.api.dto.category.MiddleCategoryResponse
import com.tenutz.kiosksim.data.datasource.api.dto.category.MiddleCategoryUpdateRequest
import com.tenutz.kiosksim.data.datasource.api.dto.category.SubCategoriesResponse
import com.tenutz.kiosksim.data.datasource.api.dto.category.SubCategoryCreateRequest
import com.tenutz.kiosksim.data.datasource.api.dto.category.SubCategoryResponse
import com.tenutz.kiosksim.data.datasource.api.dto.category.SubCategoryUpdateRequest
import com.tenutz.kiosksim.data.datasource.api.dto.common.CommonCondition
import io.reactivex.rxjava3.core.Single

interface CategoryRepository {

    fun mainCategories(cond: CommonCondition? = null): Single<Result<MainCategoriesResponse>>

    fun mainCategory(
        mainCateCd: String,
    ): Single<Result<MainCategoryResponse>>

    fun createMainCategory(
        request: MainCategoryCreateRequest,
    ): Single<Result<Unit>>

    fun updateMainCategory(
        mainCateCd: String,
        request: MainCategoryUpdateRequest,
    ): Single<Result<Unit>>

    fun deleteMainCategory(
        mainCateCd: String,
    ): Single<Result<Unit>>

    fun deleteMainCategories(
        request: CategoriesDeleteRequest,
    ): Single<Result<Unit>>

    fun changeMainCategoryPriorities(
        request: CategoryPrioritiesChangeRequest,
    ): Single<Result<Unit>>

    fun middleCategories(
        mainCateCd: String,
    ): Single<Result<MiddleCategoriesResponse>>

    fun middleCategory(
        mainCateCd: String = "2000",
        middleCateCd: String = "3000",
    ): Single<Result<MiddleCategoryResponse>>

    fun createMiddleCategory(
        mainCateCd: String,
        request: MiddleCategoryCreateRequest,
    ): Single<Result<Unit>>

    fun updateMiddleCategory(
        mainCateCd: String,
        middleCateCd: String,
        request: MiddleCategoryUpdateRequest,
    ): Single<Result<Unit>>

    fun deleteMiddleCategory(
        mainCateCd: String,
        middleCateCd: String,
    ): Single<Result<Unit>>

    fun deleteMiddleCategories(
        mainCateCd: String,
        request: CategoriesDeleteRequest,
    ): Single<Result<Unit>>

    fun changeMiddleCategoryPriorities(
        mainCateCd: String,
        request: CategoryPrioritiesChangeRequest,
    ): Single<Result<Unit>>

    fun subCategories(
        mainCateCd: String,
        middleCateCd: String,
    ): Single<Result<SubCategoriesResponse>>

    fun subCategory(
        mainCateCd: String,
        middleCateCd: String,
        subCateCd: String,
    ): Single<Result<SubCategoryResponse>>

    fun createSubCategory(
        mainCateCd: String,
        middleCateCd: String,
        request: SubCategoryCreateRequest,
    ): Single<Result<Unit>>

    fun updateSubCategory(
        mainCateCd: String,
        middleCateCd: String,
        subCateCd: String,
        request: SubCategoryUpdateRequest,
    ): Single<Result<Unit>>

    fun deleteSubCategory(
        mainCateCd: String,
        middleCateCd: String,
        subCateCd: String,
    ): Single<Result<Unit>>

    fun deleteSubCategories(
        mainCateCd: String,
        middleCateCd: String,
        request: CategoriesDeleteRequest,
    ): Single<Result<Unit>>

    fun changeSubCategoryPriorities(
        mainCateCd: String,
        middleCateCd: String,
        request: CategoryPrioritiesChangeRequest,
    ): Single<Result<Unit>>
}