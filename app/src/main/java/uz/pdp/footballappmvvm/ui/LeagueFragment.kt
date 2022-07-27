package uz.pdp.footballappmvvm.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.google.android.material.tabs.TabLayoutMediator
import uz.pdp.footballappmvvm.R
import uz.pdp.footballappmvvm.adapters.PagerAdapter
import uz.pdp.footballappmvvm.models.custom.LeagueData
import uz.pdp.footballappmvvm.databinding.FragmentLeagueBinding
import uz.pdp.footballappmvvm.utils.setLeagueImage


class LeagueFragment : Fragment(R.layout.fragment_league) {

    private val binding by viewBinding(FragmentLeagueBinding::bind)
    lateinit var pagerAdapter: PagerAdapter
    private val list = listOf("Table", "Matches", "Scorer")




    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val leagueData = arguments?.getSerializable("leagueData") as LeagueData

        binding.apply {
            back.setOnClickListener { findNavController().popBackStack() }

            imageLeague.setLeagueImage(leagueData.imageId)
            nameLeague.text = leagueData.name
            pagerAdapter = PagerAdapter(leagueData.id, this@LeagueFragment)
            viewPager.adapter = pagerAdapter

            TabLayoutMediator(
                tabLayout, viewPager
            ) { tab, position ->
                tab.text = list[position]
            }.attach()
        }
    }



}