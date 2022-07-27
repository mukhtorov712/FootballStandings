package uz.pdp.footballappmvvm.resourse

import uz.pdp.footballappmvvm.models.custom.TourData

sealed class MatchResource {

    object Loading : MatchResource()

    data class Error(val message: String) : MatchResource()

    data class Success(val list: List<TourData>) : MatchResource()

}
