package com.tenutz.kiosksim.di.module

import android.content.Context
import com.tenutz.kiosksim.data.datasource.database.AppDatabase
import com.tenutz.kiosksim.data.datasource.paging.dao.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class DatabaseModule {

    @Singleton
    @Provides
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase =  AppDatabase.getInstance(context)

    @Singleton
    @Provides
    fun provideStoreReviewDao(appDatabase: AppDatabase): StoreReviewDao = appDatabase.storeReviewDao()

    @Singleton
    @Provides
    fun provideStoreReviewRemoteKeysDao(appDatabase: AppDatabase): StoreReviewRemoteKeysDao = appDatabase.storeReviewRemoteKeysDao()

    @Singleton
    @Provides
    fun provideMenuReviewDao(appDatabase: AppDatabase): MenuReviewDao = appDatabase.menuReviewDao()

    @Singleton
    @Provides
    fun provideMenuReviewRemoteKeysDao(appDatabase: AppDatabase): MenuReviewRemoteKeysDao = appDatabase.menuReviewRemoteKeysDao()

    @Singleton
    @Provides
    fun provideMenuSalesDao(appDatabase: AppDatabase): MenuSalesDao = appDatabase.menuSalesDao()

    @Singleton
    @Provides
    fun provideMenuSalesRemoteKeysDao(appDatabase: AppDatabase): MenuSalesRemoteKeysDao = appDatabase.menuSalesRemoteKeysDao()
}