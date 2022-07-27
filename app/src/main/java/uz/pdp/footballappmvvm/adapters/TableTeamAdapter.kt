package uz.pdp.footballappmvvm.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import uz.pdp.footballappmvvm.database.entity.TeamEntity
import uz.pdp.footballappmvvm.databinding.ItemTeamTableBinding
import uz.pdp.footballappmvvm.utils.loadSvg
import uz.pdp.footballappmvvm.utils.setImage

class TableTeamAdapter : ListAdapter<TeamEntity, TableTeamAdapter.Vh>(MyDiffUtil()) {

    class MyDiffUtil : DiffUtil.ItemCallback<TeamEntity>() {
        override fun areItemsTheSame(oldItem: TeamEntity, newItem: TeamEntity): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: TeamEntity, newItem: TeamEntity): Boolean {
            return oldItem == newItem
        }
    }

    inner class Vh(private val itemTeamTableBinding: ItemTeamTableBinding) :
        RecyclerView.ViewHolder(itemTeamTableBinding.root) {
        fun onBind(teamEntity: TeamEntity) {
            itemTeamTableBinding.apply {
                position.text = teamEntity.position.toString()
                if (teamEntity.crestUrl[teamEntity.crestUrl.length - 2] == 'v') {
                    image.loadSvg(teamEntity.crestUrl)
                } else {
                    image.setImage(teamEntity.crestUrl)
                }
                teamName.text = teamEntity.name
                play.text = teamEntity.playedGames.toString()
                goalFor.text = teamEntity.goalsFor.toString()
                goalAgainst.text = teamEntity.goalsAgainst.toString()
                pts.text = teamEntity.points.toString()
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Vh {
        return Vh(ItemTeamTableBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: Vh, position: Int) {
        holder.onBind(getItem(position))
    }
}