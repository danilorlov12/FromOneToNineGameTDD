package com.orlovdanylo.fromonetoninegame.data.core

import android.content.Context
import androidx.room.Database
import androidx.room.Room
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

    companion object {

        private var db: AppDatabase? = null
        private const val DB_NAME = "main.db"
        private val LOCK = Any()

        fun getInstance(context: Context): AppDatabase {
            synchronized(LOCK) {
                db?.let { return it }
                val instance = Room.databaseBuilder(
                    context,
                    AppDatabase::class.java,
                    DB_NAME
                ).addMigrations(StatisticsMigration()).build()
                db = instance
                return instance
            }
        }
    }
}
