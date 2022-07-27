package uz.pdp.footballappmvvm.resourse

import uz.pdp.footballappmvvm.database.entity.ScorerEntity

sealed class ScorerResource {

    object Loading : ScorerResource()

    data class Error(val message: String) : ScorerResource()

    data class Success(val list: List<ScorerEntity>) : ScorerResource()

}
