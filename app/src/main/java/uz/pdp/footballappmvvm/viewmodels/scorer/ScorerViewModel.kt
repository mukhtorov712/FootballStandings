package uz.pdp.footballappmvvm.viewmodels.scorer

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import uz.pdp.footballappmvvm.database.entity.ScorerEntity
import uz.pdp.footballappmvvm.repository.ScorerRepository
import uz.pdp.footballappmvvm.resourse.ScorerResource
import uz.pdp.footballappmvvm.utils.NetworkHelper

class ScorerViewModel(
    private val networkHelper: NetworkHelper,
    private val scorerRepository: ScorerRepository
) : ViewModel() {

    fun getScorer(competitionId: Int): StateFlow<ScorerResource> {

        val stateFlow = MutableStateFlow<ScorerResource>(ScorerResource.Loading)

        viewModelScope.launch {
            if (networkHelper.isNetworkConnected()) {

                scorerRepository.loadScorerNet(competitionId).catch {
                    stateFlow.emit(ScorerResource.Error(it.message ?: ""))
                }.collect {
                    if (it.isSuccessful) {
                        val l = it.body()?.scorers
                        val list = ArrayList<ScorerEntity>()
                        withContext(Dispatchers.Default) {
                            l?.forEach { t ->
                                list.add(
                                    ScorerEntity(
                                        t.player.id,
                                        it.body()?.competition?.id ?: 0,
                                        t.team.id,
                                        t.player.name,
                                        t.team.name,
                                        t.numberOfGoals
                                    )
                                )
                            }
                        }
                        scorerRepository.addScorerDb(list)
                        stateFlow.emit(ScorerResource.Success(list))
                    } else {
                        stateFlow.emit(ScorerResource.Error(it.errorBody()?.string() ?: ""))
                    }
                }

            } else {
                scorerRepository.getScorerDb(competitionId).collect {
                    if (it.isNotEmpty()) {
                        stateFlow.emit(ScorerResource.Success(it))
                    } else {
                        stateFlow.emit(
                            ScorerResource.Error(
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