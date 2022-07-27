package uz.pdp.footballappmvvm.viewmodels.scorer

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import uz.pdp.footballappmvvm.repository.MatchRepository
import uz.pdp.footballappmvvm.repository.ScorerRepository
import uz.pdp.footballappmvvm.utils.NetworkHelper
import java.lang.Exception

class ScorerFactory(
    private val scorerRepository: ScorerRepository,
    private val networkHelper: NetworkHelper
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ScorerViewModel::class.java)){
            return ScorerViewModel(networkHelper, scorerRepository) as T
        }
        throw Exception("Error")
    }

}