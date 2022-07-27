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
import uz.pdp.footballappmvvm.adapters.TourAdapter
import uz.pdp.footballappmvvm.database.AppDatabase
import uz.pdp.footballappmvvm.databinding.FragmentMatchBinding
import uz.pdp.footballappmvvm.repository.MatchRepository
import uz.pdp.footballappmvvm.resourse.MatchResource
import uz.pdp.footballappmvvm.retrofit.ApiClient
import uz.pdp.footballappmvvm.utils.NetworkHelper
import uz.pdp.footballappmvvm.utils.hide
import uz.pdp.footballappmvvm.utils.show
import uz.pdp.footballappmvvm.viewmodels.match.MatchViewModel
import uz.pdp.footballappmvvm.viewmodels.match.MatchFactory


class MatchFragment : Fragment(R.layout.fragment_match) {

    private val binding by viewBinding(FragmentMatchBinding::bind)
    lateinit var tourAdapter: TourAdapter
    lateinit var matchViewModel: MatchViewModel
    private var isCreate = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        matchViewModel = ViewModelProvider(
            this, MatchFactory(
                MatchRepository(
                    ApiClient.apiService,
                    AppDatabase.getInstance(requireContext()).matchDao()
                ), NetworkHelper(requireContext())
            )
        )[MatchViewModel::class.java]

        tourAdapter = TourAdapter()

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.rv.adapter = tourAdapter

        val leagueId = arguments?.getInt("id") ?: 0

        if (!isCreate) {
            load(leagueId)
            isCreate = true
        }
    }

    private fun load(leagueId: Int) {
        binding.apply {
            lifecycleScope.launch {
                matchViewModel.getMatchById(leagueId).collect {
                    when (it) {
                        is MatchResource.Loading -> {
                            animationView.show()
                        }
                        is MatchResource.Error -> {
                            animationView.hide()
                            text.text = it.message
                        }
                        is MatchResource.Success -> {
                            animationView.hide()
                            text.hide()
                            tourAdapter.submitList(it.list)
                        }
                    }
                }
            }
        }
    }
}