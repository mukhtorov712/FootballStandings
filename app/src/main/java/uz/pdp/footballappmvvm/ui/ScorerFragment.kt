package uz.pdp.footballappmvvm.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import by.kirich1409.viewbindingdelegate.viewBinding
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import uz.pdp.footballappmvvm.R
import uz.pdp.footballappmvvm.adapters.ScorerAdapter
import uz.pdp.footballappmvvm.database.AppDatabase
import uz.pdp.footballappmvvm.databinding.FragmentScorerBinding
import uz.pdp.footballappmvvm.repository.ScorerRepository
import uz.pdp.footballappmvvm.resourse.ScorerResource
import uz.pdp.footballappmvvm.retrofit.ApiClient
import uz.pdp.footballappmvvm.utils.NetworkHelper
import uz.pdp.footballappmvvm.utils.hide
import uz.pdp.footballappmvvm.utils.show
import uz.pdp.footballappmvvm.viewmodels.scorer.ScorerFactory
import uz.pdp.footballappmvvm.viewmodels.scorer.ScorerViewModel


class ScorerFragment : Fragment(R.layout.fragment_scorer) {
    private val binding by viewBinding(FragmentScorerBinding::bind)
    lateinit var scorerViewModel: ScorerViewModel
    lateinit var scorerAdapter: ScorerAdapter
    private var competitionId = 0
    var isCreate = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        scorerViewModel = ViewModelProvider(
            this, ScorerFactory(
                ScorerRepository(
                    ApiClient.apiService, AppDatabase.getInstance(requireContext()).scorerDao()
                ), NetworkHelper(requireContext())
            )
        )[ScorerViewModel::class.java]
        competitionId = arguments?.getInt("id") ?: -1
        scorerAdapter = ScorerAdapter()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.rv.adapter = scorerAdapter
        if (!isCreate) {
            load(competitionId)
            isCreate = true
        }
    }

    private fun load(competitionId: Int) {
        binding.apply {
            lifecycleScope.launch {
                scorerViewModel.getScorer(competitionId).collect {
                    when (it) {
                        is ScorerResource.Loading -> {
                            animationView.show()
                        }
                        is ScorerResource.Error -> {
                            animationView.hide()
                            text.text = it.message
                        }
                        is ScorerResource.Success -> {
                            animationView.hide()
                            text.hide()
                            scorerAdapter.submitList(it.list)
                        }
                    }
                }
            }
        }
    }
}