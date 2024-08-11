package com.tenutz.kiosksim.di.module

import com.tenutz.kiosksim.data.datasource.api.SMSApi
import com.tenutz.kiosksim.data.datasource.paging.repository.*
import com.tenutz.kiosksim.data.datasource.paging.source.mapper.PushAlarmsMapper
import com.tenutz.kiosksim.data.datasource.paging.source.mapper.ReviewsMapper
import com.tenutz.kiosksim.data.datasource.paging.source.mapper.SalesMapper
import com.tenutz.kiosksim.data.repository.category.CategoryRepository
import com.tenutz.kiosksim.data.repository.category.CategoryRepositoryImpl
import com.tenutz.kiosksim.data.repository.help.HelpRepository
import com.tenutz.kiosksim.data.repository.help.HelpRepositoryImpl
import com.tenutz.kiosksim.data.repository.menu.MenuRepository
import com.tenutz.kiosksim.data.repository.menu.MenuRepositoryImpl
import com.tenutz.kiosksim.data.repository.option.OptionRepository
import com.tenutz.kiosksim.data.repository.option.OptionRepositoryImpl
import com.tenutz.kiosksim.data.repository.optiongroup.OptionGroupRepository
import com.tenutz.kiosksim.data.repository.optiongroup.OptionGroupRepositoryImpl
import com.tenutz.kiosksim.data.repository.payment.PaymentRepository
import com.tenutz.kiosksim.data.repository.payment.PaymentRepositoryImpl
import com.tenutz.kiosksim.data.repository.sharing.SharedRepository
import com.tenutz.kiosksim.data.repository.sharing.SharedRepositoryImpl
import com.tenutz.kiosksim.data.repository.store.StoreRepository
import com.tenutz.kiosksim.data.repository.store.StoreRepositoryImpl
import com.tenutz.kiosksim.data.repository.terms.TermsRepository
import com.tenutz.kiosksim.data.repository.terms.TermsRepositoryImpl
import com.tenutz.kiosksim.data.repository.user.UserRepository
import com.tenutz.kiosksim.data.repository.user.UserRepositoryImpl
import com.tenutz.kiosksim.di.qualifier.NavigationGraphReference
import com.tenutz.kiosksim.di.qualifier.NavigationGraphs
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
abstract class RepositoryModule {

    companion object {

        @Singleton
        @Provides
        fun provideReviewPagingRepository(
            SMSApi: SMSApi,
            mapper: ReviewsMapper,
        ): ReviewPagingRepository {
            return ReviewPagingRepositoryImpl(SMSApi, mapper)
        }

        @Singleton
        @Provides
        fun provideSalesPagingRepository(
            SMSApi: SMSApi,
            mapper: SalesMapper,
        ): SalesPagingRepository {
            return SalesPagingRepositoryImpl(SMSApi, mapper)
        }

        @Singleton
        @Provides
        fun providePushAlarmPagingRepository(
            SMSApi: SMSApi,
            mapper: PushAlarmsMapper,
        ): PushAlarmPagingRepository {
            return PushAlarmPagingRepositoryImpl(SMSApi, mapper)
        }
    }

    @Binds
    abstract fun provideCategoryRepository(
        repository: CategoryRepositoryImpl
    ): CategoryRepository

    @Binds
    abstract fun provideMenuRepository(
        repository: MenuRepositoryImpl
    ): MenuRepository

    @Binds
    abstract fun provideOptionRepository(
        repository: OptionRepositoryImpl
    ): OptionRepository

    @Binds
    abstract fun provideOptionGroupRepository(
        repository: OptionGroupRepositoryImpl
    ): OptionGroupRepository

    @Binds
    abstract fun provideStoreRepository(
        repository: StoreRepositoryImpl
    ): StoreRepository

    @Binds
    abstract fun provideUserRepository(
        repository: UserRepositoryImpl
    ): UserRepository

    @Binds
    abstract fun provideHelpRepository(
        repository: HelpRepositoryImpl
    ): HelpRepository

    @Binds
    abstract fun provideTermsRepository(
        repository: TermsRepositoryImpl
    ): TermsRepository

    @Binds
    abstract fun providePaymentRepository(
        repository: PaymentRepositoryImpl
    ): PaymentRepository

    @Binds
    @Singleton
    @NavigationGraphReference(NavigationGraphs.MAIN)
    abstract fun provideSharedRepository(
        repository: SharedRepositoryImpl
    ): SharedRepository

    /*@Binds
    @Singleton
    @NavigationGraphReference(NavigationGraphs.SUB_CATEGORY)
    abstract fun provideSubCategoryNavSharedRepository(
        repository: SharedRepositoryImpl
    ): SharedRepository*/
}