package uz.pdp.footballappmvvm.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import uz.pdp.footballappmvvm.databinding.ItemTourBinding
import uz.pdp.footballappmvvm.models.custom.TourData

class TourAdapter : ListAdapter<TourData, TourAdapter.Vh>(MyDiffUtil()) {

    class MyDiffUtil : DiffUtil.ItemCallback<TourData>() {
        override fun areItemsTheSame(oldItem: TourData, newItem: TourData): Boolean {
            return oldItem.tourNumber == newItem.tourNumber
        }

        override fun areContentsTheSame(oldItem: TourData, newItem: TourData): Boolean {
            return oldItem == newItem
        }
    }


    inner class Vh(private val itemTourBinding: ItemTourBinding) :
        RecyclerView.ViewHolder(itemTourBinding.root) {
        fun onBind(tourData: TourData) {
            itemTourBinding.apply {
                tour.text = tourData.tourNumber.toString()
                val matchAdapter = MatchAdapter(tourData.matchList)
                rv.adapter = matchAdapter
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Vh =
        Vh(ItemTourBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun onBindViewHolder(holder: Vh, position: Int) {
        holder.onBind(getItem(position))
    }
}