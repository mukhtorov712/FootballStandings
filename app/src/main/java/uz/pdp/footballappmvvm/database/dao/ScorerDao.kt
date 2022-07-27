package uz.pdp.footballappmvvm.database.dao

import androidx.room.Dao
import androidx.room.Query
import uz.pdp.footballappmvvm.database.entity.ScorerEntity

@Dao
interface ScorerDao : BaseDao<ScorerEntity> {

    @Query("select * from scorerentity where competitionId =:competitionId")
    fun getScorer(competitionId: Int): List<ScorerEntity>


}