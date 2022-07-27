package uz.pdp.footballappmvvm.viewmodels.match

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import uz.pdp.footballappmvvm.database.entity.MatchEntity
import uz.pdp.footballappmvvm.models.custom.TourData
import uz.pdp.footballappmvvm.repository.MatchRepository
import uz.pdp.footballappmvvm.resourse.MatchResource
import uz.pdp.footballappmvvm.utils.NetworkHelper
import uz.pdp.footballappmvvm.utils.getDate

class MatchViewModel(
    private val matchRepository: MatchRepository,
    private val networkHelper: NetworkHelper,
    private var a: Int = -1
) : ViewModel() {

    fun getMatchById(leagueId: Int): StateFlow<MatchResource> {
        val stateFlow = MutableStateFlow<MatchResource>(MatchResource.Loading)
        viewModelScope.launch {
            if (networkHelper.isNetworkConnected()) {

                matchRepository.getMatchByIdNet(leagueId).catch {
                    stateFlow.emit(MatchResource.Error(it.message ?: ""))
                }.collect {
                    val matchList = it.body()?.matches ?: emptyList()
                    if (it.isSuccessful && matchList.isNotEmpty()) {
                        a = it.body()?.matches?.get(0)?.season?.currentMatchday?:-1
                        val list = ArrayList<MatchEntity>()
                        withContext(Dispatchers.Default) {
                            matchList.forEach { t ->
                                list.add(
                                    MatchEntity(
                                        t.id,
                                        leagueId,
                                        t.matchday,
                                        t.status,
                                        getDate(t.utcDate),
                                        t.homeTeam.name,
                                        t.awayTeam.name,
                                        t.homeTeam.id,
                                        t.awayTeam.id,
                                        t.score.fullTime.homeTeam,
                                        t.score.fullTime.awayTeam
                                    )
                                )
                            }
                        }
                        matchRepository.addMatchDb(list)
                        val list1 = ArrayList<TourData>()
                        withContext(Dispatchers.Default) {
                            for (i in 1..a) {
                                list1.add(
                                    TourData(
                                        a - i + 1,
                                        matchRepository.getTeamByMatchIdDb(leagueId, a - i + 1)
                                    )
                                )
                            }
                        }
                        stateFlow.emit(MatchResource.Success(list1))
                    } else {
                        stateFlow.emit(MatchResource.Error(it.errorBody()?.string().toString()))
                    }
                }
            } else {
                matchRepository.getCount(leagueId).collect {
                    if (it != 0) {
                        val list = ArrayList<TourData>()
                        withContext(Dispatchers.Default) {
                            for (i in 1..a) {
                                list.add(
                                    TourData(
                                        a - i + 1,
                                        matchRepository.getTeamByMatchIdDb(leagueId, a - i + 1)
                                    )
                                )
                            }
                        }
                        stateFlow.emit(MatchResource.Success(list))
                    } else {
                        stateFlow.emit(
                            MatchResource.Error(
                                "No Internet Connection\n" +
                                        "Not Found Information!!!"
                            )
                        )
                    }
                }
            }
        }
        return stateFlow
    }

}