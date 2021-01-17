package com.android.foodappdemo.adapter

import android.os.Handler
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.android.foodappdemo.R
import com.android.foodappdemo.models.FoodItem
import com.android.foodappdemo.utils.CommonUtils
import com.android.foodappdemo.utils.gone
import com.android.foodappdemo.utils.load
import com.android.foodappdemo.utils.visible
import kotlinx.android.synthetic.main.list_item_food.view.*
import java.lang.StringBuilder

class FoodAdapter(
    var data: List<FoodItem>? = null,
    val onItemClick: ((Any) -> Unit)? = null,
    val onAddClick: ((FoodItem?) -> Unit)? = null
) : RecyclerView.Adapter<FoodAdapter.WhatsHappeningViewHolder>() {

    fun refreshData(data: List<FoodItem>) {
        this.data = data
        notifyDataSetChanged()
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WhatsHappeningViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_item_food, parent, false)

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
                    ivFood.load(context, it.image, R.drawable.placeholder)
                    CommonUtils.setValueToView(tvFoodTitle, it.name)
                    CommonUtils.setValueToView(tvFoodDesc, it.description)
                    val spec = StringBuilder()

                    it.weight?.let { wg ->
                        spec.append("$wg grams")
                    }

                    it.diameter?.let { dim ->
                        spec.append(", $dim cm")
                    }

                    it.quantity?.let { qt ->
                        spec.append(", $qt pieces")
                    }
                    CommonUtils.setValueToView(tvFoodSpec, spec.toString())
                    CommonUtils.setValueToView(btnPrice, it.price.plus(" usd"))
                }

                setOnClickListener {
                    // onItemClick?.invoke()
                }
                btnPrice?.setOnClickListener {

                    /*btnPrice.backgroundTintList = ContextCompat.getColorStateList(context, R.color.green)
                    btnPrice.setTextSize(TypedValue.COMPLEX_UNIT_SP,12f)*/
                    btnAddedMsg.visible()
                    Handler().postDelayed({
                        btnAddedMsg.gone()
                        /*btnPrice.backgroundTintList = ContextCompat.getColorStateList(context, R.color.black)
                        btnPrice.setTextSize(TypedValue.COMPLEX_UNIT_SP,15f)*/
                    }, 1000)

                    onAddClick?.invoke(data?.get(adapterPosition))
                }

            }

        }
    }
}