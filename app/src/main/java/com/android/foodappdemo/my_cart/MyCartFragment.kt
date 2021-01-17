package com.android.foodappdemo.my_cart

import android.os.Bundle
import android.view.View
import com.android.foodappdemo.R
import com.android.foodappdemo.adapter.MyCartAdapter
import com.android.foodappdemo.core.App
import com.android.foodappdemo.core.BaseFragment
import com.android.foodappdemo.models.FoodItem
import com.android.foodappdemo.utils.*
import kotlinx.android.synthetic.main.fragment_my_cart.*


class MyCartFragment : BaseFragment(R.layout.fragment_my_cart) {

    var myCartAdapter: MyCartAdapter? = null
    var cartList: List<FoodItem>? = null

    override fun invalidate() {

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        cartList = App.cartList
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setMyCartAdapter()
    }

    private fun setMyCartAdapter() {
        if (rvMyCart.adapter == null) {
            rvMyCart?.setVerticalLayoutManager()
            rvMyCart?.setHorizontalItemDecoration(
                space = CommonUtils.getDimen(context!!, R.dimen.margin_normal),
                initialPadding = 0,
                isRtl = false
            )
            myCartAdapter = MyCartAdapter(arrayListOf(), onRemoveClick = {
                App.cartList.removeAt(it)
                setCartData()
            })
            rvMyCart.adapter = myCartAdapter
        }
        setCartData()
    }

    private fun setCartData() {
        cartList?.let {
            myCartAdapter?.refreshData(it)
            setGrandTotal()
        }
        rlBottom.visible(!cartList.isNullOrEmpty())
        tvEmptyCart.visible(cartList.isNullOrEmpty())
    }

    private fun setGrandTotal() {
        CommonUtils.setValueToView(
            tvGrandTotal,
            cartList?.map { it.price.toDouble() }?.sum().toString()
        )

    }

}