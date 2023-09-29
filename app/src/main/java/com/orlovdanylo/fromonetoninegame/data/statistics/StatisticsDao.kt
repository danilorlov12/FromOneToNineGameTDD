package com.orlovdanylo.fromonetoninegame.data.statistics

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface StatisticsDao {

    @Query("SELECT * FROM statistics")
    suspend fun getStatistics(): StatisticsModelEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateStatistics(statistics: StatisticsModelEntity)
}