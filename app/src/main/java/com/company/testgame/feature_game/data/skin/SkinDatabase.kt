package com.company.testgame.feature_game.data.skin

import androidx.room.Database
import androidx.room.RoomDatabase
import com.company.testgame.feature_game.domain.model.skin.Skin

@Database(
    entities = [Skin::class],
    version = 1,
    exportSchema = false
)
abstract class SkinDatabase() : RoomDatabase() {

    abstract val skinDao: SkinDao

    companion object {
        const val SKIN_DB_NAME = "SKIN_DB_NAME"
    }
}