package com.company.testgame.di

import com.company.testgame.feature_game.data.FakeShopItemsRepositoryImpl
import com.company.testgame.feature_game.domain.model.ShopItemsRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import java.util.Calendar
import javax.inject.Singleton
import kotlin.random.Random

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideRandomInstance(): Random {
        return Random(Calendar.getInstance().timeInMillis)
    }

    @Provides
    @Singleton
    fun provideShopItemsRepository(): ShopItemsRepository {
        return FakeShopItemsRepositoryImpl()
    }
}