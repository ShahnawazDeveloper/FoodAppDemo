package com.android.foodappdemo.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.android.foodappdemo.R
import com.android.foodappdemo.models.FoodItem
import com.android.foodappdemo.utils.CommonUtils
import com.android.foodappdemo.utils.load
import kotlinx.android.synthetic.main.list_item_my_cart.view.*

class MyCartAdapter(
    //var data: ArrayList<Pair<Int, MutableList<FoodItem>>>? = null,
    var data: List<FoodItem>? = null,
    val onItemClick: ((Any) -> Unit)? = null,
    val onRemoveClick: ((Int) -> Unit)? = null
) : RecyclerView.Adapter<MyCartAdapter.WhatsHappeningViewHolder>() {

    fun refreshData(data: List<FoodItem>?) {
        this.data = data
        notifyDataSetChanged()
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WhatsHappeningViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_item_my_cart, parent, false)

        return WhatsHappeningViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return data?.size ?: 0
    }

    override fun onBindViewHolder(holder: WhatsHappeningViewHolder, position: Int) {
        holder.bindView()
    }

    inner class WhatsHappeningViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindView() {
            itemView.apply {
                val itemData = data?.get(adapterPosition)
                itemData?.let {
                    it.let { data ->
                        ivFood.load(context, data.image, R.drawable.placeholder)
                        CommonUtils.setValueToView(tvFoodTitle, data.name)
                        CommonUtils.setValueToView(tvPrice, it.price.plus(" usd"))
                    }
                }

                setOnClickListener {
                    // onItemClick?.invoke()
                }
                ivRemove?.setOnClickListener {
                    onRemoveClick?.invoke(adapterPosition)
                }
            }

        }
    }
}