package uz.pdp.footballappmvvm.repository

import kotlinx.coroutines.flow.flow
import uz.pdp.footballappmvvm.database.dao.ScorerDao
import uz.pdp.footballappmvvm.database.entity.ScorerEntity
import uz.pdp.footballappmvvm.retrofit.ApiService

class ScorerRepository(private val apiService: ApiService, private val scorerDao: ScorerDao) {

    suspend fun loadScorerNet(competitionId: Int) =
        flow { emit(apiService.getScorer(competitionId)) }

    suspend fun getScorerDb(competitionId: Int) =
        flow { emit(scorerDao.getScorer(competitionId)) }

    fun addScorerDb(
        list: List<ScorerEntity>
    ) = scorerDao.insert(list)


}