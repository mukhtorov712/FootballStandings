package uz.pdp.footballappmvvm.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import uz.pdp.footballappmvvm.database.entity.MatchEntity
import uz.pdp.footballappmvvm.databinding.ItemMatchBinding

class MatchAdapter(private val list: List<MatchEntity>) : RecyclerView.Adapter<MatchAdapter.Vh>() {

    inner class Vh(private val itemMatchBinding: ItemMatchBinding) :
        RecyclerView.ViewHolder(itemMatchBinding.root) {
        fun onBind(matchEntity: MatchEntity) {
            itemMatchBinding.apply {
                date.text = matchEntity.date
                homeName.text = matchEntity.homeName
                awayName.text = matchEntity.awayName
                homeScore.text = matchEntity.homeScore.toString()
                awayScore.text = matchEntity.awayScore.toString()
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Vh =
        Vh(ItemMatchBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun onBindViewHolder(holder: Vh, position: Int) {
        holder.onBind(list[position])
    }

    override fun getItemCount(): Int = list.size
}