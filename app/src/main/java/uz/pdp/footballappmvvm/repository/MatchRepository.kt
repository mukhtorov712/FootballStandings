package uz.pdp.footballappmvvm.repository

import kotlinx.coroutines.flow.flow
import uz.pdp.footballappmvvm.database.dao.MatchDao
import uz.pdp.footballappmvvm.database.dao.TeamDao
import uz.pdp.footballappmvvm.database.entity.MatchEntity
import uz.pdp.footballappmvvm.database.entity.TeamEntity
import uz.pdp.footballappmvvm.retrofit.ApiService

class MatchRepository(private val apiService: ApiService, private val matchDao: MatchDao) {

    suspend fun getMatchByIdNet(leagueId: Int) =
        flow { emit(apiService.getMatchById(leagueId)) }

    fun getTeamByMatchIdDb(leagueId: Int, matchDay: Int) =
        matchDao.getMatchById(leagueId, matchDay)

    suspend fun getCount(leagueId: Int) = flow { emit(matchDao.getCount(leagueId)) }

    fun addMatchDb(list: List<MatchEntity>) = matchDao.insert(list)
}