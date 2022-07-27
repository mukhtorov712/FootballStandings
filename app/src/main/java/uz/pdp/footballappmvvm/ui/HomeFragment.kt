package uz.pdp.footballappmvvm.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import uz.pdp.footballappmvvm.R
import uz.pdp.footballappmvvm.adapters.HomeAdapter
import uz.pdp.footballappmvvm.database.AppDatabase
import uz.pdp.footballappmvvm.models.custom.LeagueData
import uz.pdp.footballappmvvm.databinding.FragmentHomeBinding
import uz.pdp.footballappmvvm.repository.LoadRepository
import uz.pdp.footballappmvvm.resourse.LoadResource
import uz.pdp.footballappmvvm.retrofit.ApiClient
import uz.pdp.footballappmvvm.utils.*
import uz.pdp.footballappmvvm.viewmodels.load.LoadViewModel
import uz.pdp.footballappmvvm.viewmodels.load.LoadFactory


class HomeFragment : Fragment(R.layout.fragment_home) {
    private val binding by viewBinding(FragmentHomeBinding::bind)
    private lateinit var leagueList: ArrayList<LeagueData>
    lateinit var homeAdapter: HomeAdapter
    lateinit var loadViewModel: LoadViewModel
    var onCreated = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        loadLeague()
        loadViewModel = ViewModelProvider(
            this,
            LoadFactory(
                LoadRepository(
                    ApiClient.apiService,
                    AppDatabase.getInstance(requireContext()).teamDao()
                ), NetworkHelper(requireContext())
            )
        )[LoadViewModel::class.java]

        homeAdapter = HomeAdapter(object : HomeAdapter.OnHomeItemClickListener {
            override fun onItemClick(leagueData: LeagueData) {
                findNavController().navigate(
                    R.id.action_homeFragment2_to_leagueFragment,
                    Bundle().apply {
                        putSerializable("leagueData", leagueData)
                    }, navOptions())
            }
        })
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.rv.adapter = homeAdapter

        if (!onCreated) {
            load()
            onCreated = true
        }

    }

    private fun load() {
        binding.apply {
            lifecycleScope.launch {
                loadViewModel.loadStandingsByIdNet(leagueList).collect {
                    when (it) {
                        is LoadResource.Loading -> {
                            animationView.show()
                        }
                        is LoadResource.Error -> {
                            animationView.hide()
                            text.text = it.message
                        }
                        is LoadResource.Success -> {
                            animationView.hide()
                            text.hide()
                            homeAdapter.submitList(it.leagueList)
                        }
                    }
                }
            }
        }
    }


    private fun loadLeague() {
        //        listOf(2021, 2014, 2019, 2002, 2015)
        leagueList = ArrayList()
        leagueList.add(LeagueData(2021, "Premier League", "England", "gb-eng", 23))
        leagueList.add(LeagueData(2014, "La Liga", "Spain", "es", 15))
        leagueList.add(LeagueData(2019, "Serie A", "Italy", "it", 12))
        leagueList.add(LeagueData(2002, "Bundesliga", "Germany", "de", 10))
        leagueList.add(LeagueData(2015, "Ligue 1", "France", "fr", 9))
    }
}