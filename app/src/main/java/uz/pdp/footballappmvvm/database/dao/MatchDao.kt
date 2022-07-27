package uz.pdp.footballappmvvm.database.dao

import androidx.room.Dao
import androidx.room.Query
import uz.pdp.footballappmvvm.database.entity.MatchEntity

@Dao
interface MatchDao : BaseDao<MatchEntity> {
    @Query("select * from matchentity where leagueId=:leagueId and matchDay=:matchDay order by date")
    fun getMatchById(leagueId: Int, matchDay: Int): List<MatchEntity>

    @Query("select count(id) from matchentity where leagueId=:leagueId")
    fun getCount(leagueId: Int): Int
}