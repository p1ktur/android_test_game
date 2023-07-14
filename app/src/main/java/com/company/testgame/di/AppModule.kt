package com.company.testgame.di

import android.app.Application
import androidx.room.Room
import com.company.testgame.feature_game.data.SkinRepositoryImpl
import com.company.testgame.feature_game.data.achievement.AchievementDao
import com.company.testgame.feature_game.data.achievement.AchievementDatabase
import com.company.testgame.feature_game.data.skin.SkinDao
import com.company.testgame.feature_game.data.skin.SkinDatabase
import com.company.testgame.feature_game.domain.model.SkinRepository
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
    fun provideShopItemsRepository(skinDao: SkinDao, achievementDao: AchievementDao): SkinRepository {
        return SkinRepositoryImpl(skinDao, achievementDao)
    }

    @Provides
    @Singleton
    fun provideAchievementDb(application: Application): AchievementDatabase {
        return Room.databaseBuilder(application, AchievementDatabase::class.java, AchievementDatabase.ACHIEVEMENT_DB_NAME)
            .enableMultiInstanceInvalidation()
            .build()
    }

    @Provides
    @Singleton
    fun provideAchievementDao(database: AchievementDatabase): AchievementDao {
        return database.achievementDao
    }

    @Provides
    @Singleton
    fun provideSkinDb(application: Application): SkinDatabase {
        return Room.databaseBuilder(application, SkinDatabase::class.java, SkinDatabase.SKIN_DB_NAME)
            .enableMultiInstanceInvalidation()
            .build()
    }

    @Provides
    @Singleton
    fun provideSkinDao(database: SkinDatabase): SkinDao {
        return database.skinDao
    }
}