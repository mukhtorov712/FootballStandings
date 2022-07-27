package uz.pdp.footballappmvvm.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import by.kirich1409.viewbindingdelegate.viewBinding
import uz.pdp.footballappmvvm.R
import uz.pdp.footballappmvvm.adapters.TableTeamAdapter
import uz.pdp.footballappmvvm.database.AppDatabase
import uz.pdp.footballappmvvm.databinding.FragmentTableBinding


class TableFragment : Fragment(R.layout.fragment_table) {
    private val binding by viewBinding(FragmentTableBinding::bind)
    lateinit var tableTeamAdapter: TableTeamAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val leagueId = arguments?.getInt("id") ?: 0
        tableTeamAdapter = TableTeamAdapter()
        tableTeamAdapter.submitList(
            AppDatabase.getInstance(requireContext()).teamDao().getTeamByStandingId(leagueId)
        )
        binding.rv.adapter = tableTeamAdapter
    }

}