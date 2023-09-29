package com.orlovdanylo.fromonetoninegame.data.core

import androidx.room.Database
import androidx.room.RoomDatabase
import com.orlovdanylo.fromonetoninegame.data.game.GameDao
import com.orlovdanylo.fromonetoninegame.data.game.GameModelDB
import com.orlovdanylo.fromonetoninegame.data.statistics.StatisticsDao
import com.orlovdanylo.fromonetoninegame.data.statistics.StatisticsModelEntity

@Database(
    version = 2,
    entities = [
        GameModelDB::class,
        StatisticsModelEntity::class],
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun gameDao(): GameDao

    abstract fun statisticsDao(): StatisticsDao

}
