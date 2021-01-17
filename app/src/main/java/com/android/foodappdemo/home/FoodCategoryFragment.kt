package com.android.foodappdemo.home

import android.os.Bundle
import android.view.View
import com.android.foodappdemo.R
import com.android.foodappdemo.adapter.FoodAdapter
import com.android.foodappdemo.core.App
import com.android.foodappdemo.core.BaseFragment
import com.android.foodappdemo.models.Category
import com.android.foodappdemo.models.FoodItem
import com.android.foodappdemo.utils.setVerticalLayoutManager
import kotlinx.android.synthetic.main.fragment_food_category.*

class FoodCategoryFragment : BaseFragment(R.layout.fragment_food_category) {

    var foodAdapter: FoodAdapter? = null

    var category: Category? = null
    var foodList: List<FoodItem>? = null

    override fun invalidate() {

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getDataFromArguments()
    }

    private fun getDataFromArguments() {
        arguments?.let {
            arguments?.getParcelable<Category>("mData")?.let {
                category = it
                foodList = category?.foodItems
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setFoodAdapter()
    }

    private fun setFoodAdapter() {
        if (rvFoodItem.adapter == null) {
            rvFoodItem?.setVerticalLayoutManager()
            foodAdapter = FoodAdapter(arrayListOf(), onAddClick = {
                it?.let {
                    App.cartList.add(it)
                    (parentFragment as HomeFragment).updateCartCount()
                    /*App.cartList.firstOrNull { cart ->
                            cart.first == it.id
                        }?.apply {
                            this.second.add(it)
                        } ?: kotlin.run {
                            App.cartList.add(Pair(it.id, listOf(it).toMutableList()))
                        }*/
                }
            })
            rvFoodItem.adapter = foodAdapter
        }

        foodList?.let {
            foodAdapter?.refreshData(it)
        }
    }
}