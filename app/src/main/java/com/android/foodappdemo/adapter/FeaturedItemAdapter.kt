package com.android.foodappdemo.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.android.foodappdemo.R
import com.android.foodappdemo.utils.load
import kotlinx.android.synthetic.main.list_item_featured_item.view.*

class FeaturedItemAdapter(private var featuredList: List<String>? = null) :
    RecyclerView.Adapter<FeaturedItemAdapter.FeaturedItemVH>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FeaturedItemVH {
        return FeaturedItemVH(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.list_item_featured_item, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return featuredList?.size ?: 0
    }

    override fun onBindViewHolder(holder: FeaturedItemVH, position: Int) {
        featuredList?.let {
            holder.bindView()
        }
    }

    inner class FeaturedItemVH(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindView() {
            itemView.apply {
                ivFeaturedItem.load(
                    context,
                    featuredList?.get(adapterPosition),
                    R.drawable.placeholder
                )
            }
        }
    }

    fun refreshData(diningList: List<String>?) {
        this.featuredList = diningList
        notifyDataSetChanged()
    }


}
