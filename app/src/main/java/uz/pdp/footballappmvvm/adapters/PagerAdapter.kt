package uz.pdp.footballappmvvm.adapters

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import uz.pdp.footballappmvvm.ui.MatchFragment
import uz.pdp.footballappmvvm.ui.ScorerFragment
import uz.pdp.footballappmvvm.ui.TableFragment

class PagerAdapter(private val leagueId: Int, fragment: Fragment) : FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int = 3
    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> {
                TableFragment().apply {
                    arguments = Bundle().apply {
                        putInt("id", leagueId)
                    }
                }
            }
            1 -> {
                MatchFragment().apply {
                    arguments = Bundle().apply {
                        putInt("id", leagueId)
                    }
                }
            }
            else -> {
                ScorerFragment().apply {
                    arguments = Bundle().apply {
                        putInt("id", leagueId)
                    }
                }
            }
        }
    }
}