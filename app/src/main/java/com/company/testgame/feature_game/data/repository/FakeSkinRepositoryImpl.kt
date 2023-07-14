package com.company.testgame.feature_game.data.repository

import com.company.testgame.R
import com.company.testgame.feature_game.domain.model.achievement.PlayerAchievements
import com.company.testgame.feature_game.domain.model.repository.SkinRepository
import com.company.testgame.feature_game.domain.model.skin.Skin
import com.company.testgame.feature_game.domain.model.ShopItem
import com.company.testgame.feature_game.domain.model.skin.SkinUnlockCondition
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FakeSkinRepositoryImpl: SkinRepository {
    override suspend fun getRocketSkins(): Flow<List<Skin>> {
        return flow {
            emit(listOf(
                Skin(
                    0,
                    "Leon 0",
                    ShopItem.RocketSkin.shopKey,
                    false,
                    "https://www.vhv.rs/dpng/d/262-2628683_transparent-leon-png-leon-brawl-stars-png-png.png",
                    false,
                    false,
                    SkinUnlockCondition.Condition11.id
                ),
                Skin(
                    1,
                    "Leon 1",
                    ShopItem.RocketSkin.shopKey,
                    true,
                    R.drawable.rocket_idle.toString(),
                    true,
                    true,
                    null
                ),
                Skin(
                    2,
                    "Leon 2",
                    ShopItem.RocketSkin.shopKey,
                    false,
                    "https://www.pngfind.com/pngs/m/265-2654176_art-on-new-bravler-leon-brawl-stars-.png",
                    false,
                    false,
                    SkinUnlockCondition.Condition12.id
                ),
                Skin(
                    3,
                    "Leon 3",
                    ShopItem.RocketSkin.shopKey,
                    false,
                    "https://www.vhv.rs/dpng/d/488-4887125_brawl-stars-wiki-brawl-stars-leon-skins-hd.png",
                    false,
                    false,
                    SkinUnlockCondition.Condition13.id
                ),
            ))
        }
    }

    override suspend fun getObstacleSkins(): Flow<List<Skin>> {
        return flow {
            emit(listOf(
                Skin(
                    4,
                    "Meteor 0",
                    ShopItem.ObstacleSkin.shopKey,
                    true,
                    R.drawable.obstacle.toString(),
                    true,
                    true
                ),
                Skin(
                    5,
                    "Meteor 1",
                    ShopItem.ObstacleSkin.shopKey,
                    false,
                    "https://cdn.imgbin.com/11/7/8/imgbin-meteor-CGnvkiv8CDbYN1eizZvDaEAZa.jpg",
                    false,
                    false,
                    SkinUnlockCondition.Condition21.id
                ),
            ))
        }
    }

    override suspend fun getBonusSkins(): Flow<List<Skin>> {
        return flow {
            emit(listOf(
                Skin(
                    6,
                    "Bonus 0",
                    ShopItem.BonusSkin.shopKey,
                    true,
                    R.drawable.star.toString(),
                    true,
                    true
                ),
                Skin(
                    7,
                    "Bonus 1",
                    ShopItem.BonusSkin.shopKey,
                    false,
                    "https://w7.pngwing.com/pngs/134/138/png-transparent-star-golden-stars-angle-3d-computer-graphics-symmetry-thumbnail.png",
                    false,
                    false,
                    SkinUnlockCondition.Condition31.id
                ),
            ))
        }
    }

    override suspend fun getBackgroundSkins(): Flow<List<Skin>> {
        return flow {
            emit(listOf(
                Skin(
                    8,
                    "Back 0",
                    ShopItem.BackgroundSkin.shopKey,
                    true,
                    R.drawable.back_main.toString(),
                    true,
                    true
                ),
                Skin(
                    9,
                    "Back 1",
                    ShopItem.BackgroundSkin.shopKey,
                    false,
                    "https://wallpaperboat.com/wp-content/uploads/2019/08/Background-space-AVATAN-PLUS-920x1636.jpg",
                    false,
                    false,
                    SkinUnlockCondition.Condition41.id
                ),
                Skin(
                    10,
                    "Back 2",
                    ShopItem.BackgroundSkin.shopKey,
                    false,
                    "https://media.geeksforgeeks.org/wp-content/uploads/20210101144014/gfglogo.png",
                    false,
                    false,
                    SkinUnlockCondition.Condition42.id
                ),
            ))
        }
    }

    override suspend fun insertSkin(skin: Skin) {

    }

    override suspend fun getAchievements(): Flow<List<PlayerAchievements>> {
        return flow {
            emit(listOf(PlayerAchievements.Dummy))
        }
    }

    override suspend fun updateAchievements(achievements: PlayerAchievements) {

    }
}