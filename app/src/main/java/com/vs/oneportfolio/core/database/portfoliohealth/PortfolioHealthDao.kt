package com.vs.oneportfolio.core.database.portfoliohealth

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface PortfolioHealthDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPortfolioAnalysis(analysis: PortfolioHealthEntity): Long


    @Query("SELECT * FROM portfolio_health ORDER BY timestamp DESC")
    fun getAllPortfolioAnalyses(): Flow<List<PortfolioHealthEntity>>

    @Query("SELECT * FROM portfolio_health WHERE id = :id")
    suspend fun getPortfolioAnalysisById(id: Long): PortfolioHealthEntity?
}