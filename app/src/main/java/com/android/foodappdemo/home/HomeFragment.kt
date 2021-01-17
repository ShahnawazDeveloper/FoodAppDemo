package com.android.foodappdemo.home

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.airbnb.mvrx.*
import com.android.foodappdemo.adapter.FeaturedItemAdapter
import com.android.foodappdemo.adapter.FragmentViewPagerAdapter
import com.android.foodappdemo.R
import com.android.foodappdemo.core.App
import com.android.foodappdemo.core.BaseFragment
import com.android.foodappdemo.models.Category
import com.android.foodappdemo.my_cart.CartFragment
import com.android.foodappdemo.utils.CommonUtils
import com.android.foodappdemo.utils.setupWithViewPager2
import com.android.foodappdemo.utils.visible
import kotlinx.android.synthetic.main.fragment_home.*


class HomeFragment : BaseFragment(R.layout.fragment_home) {

    private var featuredItemAdapter: FeaturedItemAdapter? = null

    // add ViewModel declaration here
    private val homeViewModel: HomeViewModel by activityViewModel()

    override fun invalidate() {
        withState(homeViewModel) { state ->
            when (state.featuredImages) {
                is Loading -> {

                }
                is Success -> {
                    featuredItemAdapter?.refreshData(state.featuredImages.invoke())
                    indicator.setViewPager(vpFeaturedItem)
                }
                is Fail -> {
                    //Toast.makeText(requireContext(), "Failed to load data", Toast.LENGTH_SHORT).show()
                }
            }

            when (state.category) {
                Uninitialized -> {

                }
                is Loading -> {

                }
                is Success -> {
                    setFoodCategory(state.category.invoke())
                }
                is Fail -> {

                }
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setFeaturedItemsAdapter()

        fabLayout.setOnClickListener {
            (requireActivity() as HomeActivity).addFragment(CartFragment())
        }
        addBackStackListener()
    }

    private fun setFeaturedItemsAdapter() {
        featuredItemAdapter = FeaturedItemAdapter()
        vpFeaturedItem.adapter = featuredItemAdapter
    }

    private fun setFoodCategory(category: List<Category>) {
        val fragmentList = ArrayList<Fragment>()
        val mFragmentTitleList = ArrayList<String>()

        for (mData in category) {
            FoodCategoryFragment().apply {
                val bundle = Bundle()
                bundle.putParcelable("mData", mData)
                arguments = bundle
                fragmentList.add(this)
                mFragmentTitleList.add(mData.name)
            }
        }
        vpContainer.adapter =
            FragmentViewPagerAdapter(childFragmentManager, fragmentList, lifecycle)
        tabs?.setupWithViewPager2(vpContainer, mFragmentTitleList)
    }

    fun updateCartCount() {
        tvBadge.visible(!App.cartList.isNullOrEmpty())
        if (App.cartList.size > 99) {
            CommonUtils.setValueToView(tvBadge, "99+")
        } else {
            CommonUtils.setValueToView(tvBadge, App.cartList.size.toString())
        }
    }

    private fun addBackStackListener() {
        requireActivity().supportFragmentManager.addOnBackStackChangedListener {
            requireActivity().supportFragmentManager.let {
                if (it.backStackEntryCount == 0) {
                    // your fragment visible
                    updateCartCount()
                }
            }
        }
    }

}