package com.company.testgame.feature_game.data.skin

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.company.testgame.feature_game.domain.model.skin.Skin
import kotlinx.coroutines.flow.Flow

@Dao
interface SkinDao {

    @Query("SELECT * FROM skin_table WHERE category = 'rocket'")
    fun getRocketSkins(): Flow<List<Skin>>

    @Query("SELECT * FROM skin_table WHERE category = 'obstacle'")
    fun getObstacleSkins(): Flow<List<Skin>>

    @Query("SELECT * FROM skin_table WHERE category = 'bonus'")
    fun getBonusSkins(): Flow<List<Skin>>

    @Query("SELECT * FROM skin_table WHERE category = 'background'")
    fun getBackgroundSkins(): Flow<List<Skin>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSkin(skin: Skin)
}