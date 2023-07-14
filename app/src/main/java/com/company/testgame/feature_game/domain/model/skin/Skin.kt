package com.company.testgame.feature_game.domain.model.skin

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.company.testgame.R

@Entity(tableName = "skin_table")
data class Skin(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String,
    val category: String,
    val unlocked: Boolean = false,
    val imageUrl: String,
    val locallyStored: Boolean = false,
    val selected: Boolean = false,
    val unlockConditionId: Int? = null,
) {
    companion object {
        val Dummy = Skin(
            0,
            "",
            "",
            false,
            R.drawable.back_main.toString(),
            true,
            false,
            null
        )
    }
}
