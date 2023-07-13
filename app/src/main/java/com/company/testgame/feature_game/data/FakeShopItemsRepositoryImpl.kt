package com.company.testgame.feature_game.data

import com.company.testgame.R
import com.company.testgame.feature_game.domain.model.ShopItemsRepository
import com.company.testgame.feature_game.domain.model.Skin
import com.company.testgame.feature_game.presenter.screen_shop.ShopItem
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FakeShopItemsRepositoryImpl: ShopItemsRepository {
    override fun getRocketSkins(): Flow<List<Skin>> {
        return flow {
            emit(listOf(
                Skin(
                    0,
                    "Leon 0",
                    ShopItem.RocketSkin,
                    true,
                    R.drawable.rocket_idle.toString(),
                    true,
                    false,
                    true
                ),
                Skin(
                    1,
                    "Leon 1",
                    ShopItem.RocketSkin,
                    false,
                    "https://www.vhv.rs/dpng/d/262-2628683_transparent-leon-png-leon-brawl-stars-png-png.png"
                ),
                Skin(
                    2,
                    "Leon 2",
                    ShopItem.RocketSkin,
                    false,
                    "https://www.pngfind.com/pngs/m/265-2654176_art-on-new-bravler-leon-brawl-stars-.png"
                ),
                Skin(
                    3,
                    "Leon 3",
                    ShopItem.RocketSkin,
                    false,
                    "https://www.vhv.rs/dpng/d/488-4887125_brawl-stars-wiki-brawl-stars-leon-skins-hd.png"
                ),
            ))
        }
    }

    override fun getObstacleSkins(): Flow<List<Skin>> {
        return flow {
            emit(listOf(
                Skin(
                    4,
                    "Meteor 0",
                    ShopItem.ObstacleSkin,
                    true,
                    R.drawable.obstacle.toString(),
                    true,
                    false,
                    true
                ),
                Skin(
                    5,
                    "Meteor 1",
                    ShopItem.ObstacleSkin,
                    false,
                    "https://cdn.imgbin.com/11/7/8/imgbin-meteor-CGnvkiv8CDbYN1eizZvDaEAZa.jpg"
                ),
            ))
        }
    }

    override fun getBonusSkins(): Flow<List<Skin>> {
        return flow {
            emit(listOf(
                Skin(
                    6,
                    "Bonus 0",
                    ShopItem.BonusSkin,
                    true,
                    R.drawable.star.toString(),
                    true,
                    false,
                    true
                ),
                Skin(
                    7,
                    "Bonus 1",
                    ShopItem.BonusSkin,
                    false,
                    "https://w7.pngwing.com/pngs/134/138/png-transparent-star-golden-stars-angle-3d-computer-graphics-symmetry-thumbnail.png"
                ),
            ))
        }
    }

    override fun getBackgroundSkins(): Flow<List<Skin>> {
        return flow {
            emit(listOf(
                Skin(
                    8,
                    "Back 0",
                    ShopItem.BackgroundSkin,
                    true,
                    R.drawable.back_main.toString(),
                    true,
                    false,
                    true
                ),
                Skin(
                    9,
                    "Back 1",
                    ShopItem.BackgroundSkin,
                    false,
                    "https://wallpaperboat.com/wp-content/uploads/2019/08/Background-space-AVATAN-PLUS-920x1636.jpg"
                ),
                Skin(
                    10,
                    "Back 2",
                    ShopItem.BackgroundSkin,
                    false,
                    "https://media.geeksforgeeks.org/wp-content/uploads/20210101144014/gfglogo.png"
                ),
            ))
        }
    }
}