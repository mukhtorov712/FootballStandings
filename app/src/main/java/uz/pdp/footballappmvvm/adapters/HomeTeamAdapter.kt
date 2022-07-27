package uz.pdp.footballappmvvm.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import uz.pdp.footballappmvvm.database.entity.TeamEntity
import uz.pdp.footballappmvvm.databinding.ItemHomeTeamBinding
import uz.pdp.footballappmvvm.utils.loadSvg
import uz.pdp.footballappmvvm.utils.setImage

class HomeTeamAdapter(private val list: List<TeamEntity>) :
    RecyclerView.Adapter<HomeTeamAdapter.Vh>() {

    inner class Vh(private val itemHomeTeamBinding: ItemHomeTeamBinding) :
        RecyclerView.ViewHolder(itemHomeTeamBinding.root) {
        fun onBind(teamEntity: TeamEntity) {
            itemHomeTeamBinding.apply {

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
        return Vh(ItemHomeTeamBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: Vh, position: Int) {
        holder.onBind(list[position])
    }

    override fun getItemCount(): Int = list.size

}