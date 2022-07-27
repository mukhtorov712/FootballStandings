package uz.pdp.footballappmvvm.resourse

import uz.pdp.footballappmvvm.models.custom.LeagueData

sealed class LoadResource {

    object Loading : LoadResource()

    data class Error(val message: String) : LoadResource()

    data class Success(val leagueList: List<LeagueData>) : LoadResource()

}
