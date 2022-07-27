package uz.pdp.footballappmvvm.viewmodels.load

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import uz.pdp.footballappmvvm.repository.LoadRepository
import uz.pdp.footballappmvvm.utils.NetworkHelper
import java.lang.Exception

class LoadFactory(
    private val loadRepository: LoadRepository,
    private val networkHelper: NetworkHelper
) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LoadViewModel::class.java)) {
            return LoadViewModel(loadRepository, networkHelper) as T
        }
        throw Exception("Error")
    }
}