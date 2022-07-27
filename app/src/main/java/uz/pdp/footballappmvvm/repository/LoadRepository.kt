package uz.pdp.footballappmvvm.repository

import kotlinx.coroutines.flow.flow
import uz.pdp.footballappmvvm.database.dao.TeamDao
import uz.pdp.footballappmvvm.database.entity.TeamEntity
import uz.pdp.footballappmvvm.retrofit.ApiService

class LoadRepository(private val apiService: ApiService, private val teamDao: TeamDao) {

    suspend fun getStandingsByIdNet(standingsId: Int) =
        flow { emit(apiService.getStandingsById(standingsId)) }

    suspend fun getTopStandingsIdDb(standingsId: Int) =
        flow { emit(teamDao.getTopStandingId(standingsId)) }

    suspend fun getCount() =
        flow { emit(teamDao.getCount()) }

    fun addTeamDb(list: List<TeamEntity>) = teamDao.insert(list)

}