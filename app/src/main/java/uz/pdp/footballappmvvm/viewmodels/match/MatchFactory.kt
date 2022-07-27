package uz.pdp.footballappmvvm.viewmodels.match

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import uz.pdp.footballappmvvm.repository.MatchRepository
import uz.pdp.footballappmvvm.utils.NetworkHelper
import java.lang.Exception

class MatchFactory(
    private val matchRepository: MatchRepository,
    private val networkHelper: NetworkHelper
) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MatchViewModel::class.java)){
            return MatchViewModel(matchRepository, networkHelper) as T
        }
        throw Exception("Error")
    }
}