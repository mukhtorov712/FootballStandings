package uz.pdp.footballappmvvm.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import uz.pdp.footballappmvvm.models.custom.LeagueData
import uz.pdp.footballappmvvm.databinding.ItemHomeBinding
import uz.pdp.footballappmvvm.utils.setCountyImage

class HomeAdapter(
    private val listener: OnHomeItemClickListener
) : ListAdapter<LeagueData, HomeAdapter.Vh>(MyDiffUtil()) {

    private lateinit var homeTeamAdapter: HomeTeamAdapter

    class MyDiffUtil : DiffUtil.ItemCallback<LeagueData>() {
        override fun areItemsTheSame(oldItem: LeagueData, newItem: LeagueData): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: LeagueData, newItem: LeagueData): Boolean {
            return oldItem == newItem
        }
    }


    inner class Vh(private val itemHomeBinding: ItemHomeBinding) :
        RecyclerView.ViewHolder(itemHomeBinding.root) {
        fun onBind(leagueData: LeagueData) {
            itemHomeBinding.apply {
                image.setCountyImage(leagueData.code)
                nameLeague.text = leagueData.name
                nameCountry.text = leagueData.country



                layout.setOnClickListener {
                    listener.onItemClick(leagueData)
                }

                homeTeamAdapter = HomeTeamAdapter(leagueData.standingList ?: emptyList())
                rv.adapter = homeTeamAdapter
            }
        }

    }


    interface OnHomeItemClickListener {
        fun onItemClick(leagueData: LeagueData)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Vh {
        return Vh(ItemHomeBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: Vh, position: Int) {
        holder.onBind(getItem(position))
    }
}