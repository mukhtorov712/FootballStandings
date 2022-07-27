package uz.pdp.footballappmvvm.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import uz.pdp.footballappmvvm.database.entity.ScorerEntity
import uz.pdp.footballappmvvm.databinding.ItemScorerBinding
import uz.pdp.footballappmvvm.utils.loadSvg
import uz.pdp.footballappmvvm.utils.loadTeamSvg
import uz.pdp.footballappmvvm.utils.setImage
import uz.pdp.footballappmvvm.utils.setTeamImage

class ScorerAdapter : ListAdapter<ScorerEntity, ScorerAdapter.Vh>(MyDiffUtil()) {


    class MyDiffUtil : DiffUtil.ItemCallback<ScorerEntity>() {
        override fun areItemsTheSame(oldItem: ScorerEntity, newItem: ScorerEntity): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: ScorerEntity, newItem: ScorerEntity): Boolean {
            return oldItem == newItem
        }
    }

    inner class Vh(private val itemScorerBinding: ItemScorerBinding) :
        RecyclerView.ViewHolder(itemScorerBinding.root) {
        fun onBind(scorerEntity: ScorerEntity, positions: Int) {
            itemScorerBinding.apply {
                position.text = positions.toString()
                if (scorerEntity.teamId == 64) {
                    image.setTeamImage(64)
                } else {
                    image.loadTeamSvg(scorerEntity.teamId)
                }
                playerName.text = scorerEntity.name
                goals.text = scorerEntity.goals.toString()
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Vh =
        Vh(ItemScorerBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun onBindViewHolder(holder: Vh, position: Int) {
        holder.onBind(getItem(position), position + 1)
    }
}