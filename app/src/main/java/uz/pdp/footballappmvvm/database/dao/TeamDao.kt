package uz.pdp.footballappmvvm.database.dao

import androidx.room.Dao
import androidx.room.Query
import uz.pdp.footballappmvvm.database.entity.TeamEntity


@Dao
interface TeamDao : BaseDao<TeamEntity> {

    @Query("select * from teamentity where standingId =:standingsId order by position")
    fun getTeamByStandingId(standingsId: Int): List<TeamEntity>

    @Query("select * from teamentity where standingId =:standingsId order by position limit 4")
    fun getTopStandingId(standingsId: Int): List<TeamEntity>

    @Query("select count(id) from teamentity")
    fun getCount(): Int

}