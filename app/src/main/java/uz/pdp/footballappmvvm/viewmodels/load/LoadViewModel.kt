package uz.pdp.footballappmvvm.viewmodels.load

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import uz.pdp.footballappmvvm.models.custom.LeagueData
import uz.pdp.footballappmvvm.database.entity.TeamEntity
import uz.pdp.footballappmvvm.repository.LoadRepository
import uz.pdp.footballappmvvm.resourse.LoadResource
import uz.pdp.footballappmvvm.utils.NetworkHelper

class LoadViewModel(
    private val loadRepository: LoadRepository,
    private val networkHelper: NetworkHelper
) : ViewModel() {


    fun loadStandingsByIdNet(list: List<LeagueData>): StateFlow<LoadResource> {
        val stateFlow = MutableStateFlow<LoadResource>(LoadResource.Loading)
        viewModelScope.launch {
            if (networkHelper.isNetworkConnected()) {
                for (element in list) {
                    loadRepository.getStandingsByIdNet(element.id).collect {
                        if (it.isSuccessful) {
                            val body = it.body()
                            val tableList = it.body()?.standings?.get(0)?.table
                            val list1 = ArrayList<TeamEntity>()
                            withContext(Dispatchers.Default) {
                                tableList?.forEach { t ->
                                    list1.add(
                                        TeamEntity(
                                            t.team.id,
                                            body?.competition?.id,
                                            t.position,
                                            t.team.name,
                                            body?.competition?.name ?: "",
                                            body?.competition?.area?.name ?: "",
                                            t.team.crestUrl,
                                            t.draw,
                                            t.goalDifference,
                                            t.goalsAgainst,
                                            t.goalsFor,
                                            t.lost,
                                            t.playedGames,
                                            t.points,
                                            t.won
                                        )
                                    )
                                }
                            }
                            loadRepository.addTeamDb(list1)
                        }
                    }
                }
                for (i in list.indices) {
                    loadRepository.getTopStandingsIdDb(list[i].id).collect { t ->
                        list[i].standingList = t
                    }
                }
                stateFlow.emit(LoadResource.Success(list))
            } else {
                loadRepository.getCount().collect {
                    if (it != 0) {
                        withContext(Dispatchers.Default) {
                            for (i in list.indices) {
                                loadRepository.getTopStandingsIdDb(list[i].id).collect { t ->
                                    list[i].standingList = t
                                }
                            }
                        }
                        stateFlow.emit(LoadResource.Success(list))
                    } else {
                        stateFlow.emit(LoadResource.Error("No Internet Connection\n" +
                                "Not Found Information!!!"))
                    }
                }
            }
        }
        return stateFlow
    }
}