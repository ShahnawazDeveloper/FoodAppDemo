package com.android.foodappdemo.my_cart

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.android.foodappdemo.adapter.FeaturedItemAdapter
import com.android.foodappdemo.adapter.FragmentViewPagerAdapter
import com.android.foodappdemo.R
import com.android.foodappdemo.core.BaseFragment
import com.android.foodappdemo.models.Category
import com.android.foodappdemo.utils.setupWithViewPager2
import kotlinx.android.synthetic.main.fragment_cart.*


class CartFragment : BaseFragment(R.layout.fragment_cart) {

    private var featuredItemAdapter: FeaturedItemAdapter? = null

    // add ViewModel declaration here
    //private val homeViewModel: HomeViewModel by activityViewModel()

    override fun invalidate() {
        /* withState(homeViewModel) { state ->
             when (state.featuredImages) {
                 is Loading -> {

                 }
                 is Success -> {
                     featuredItemAdapter?.refreshData(state.featuredImages.invoke())
                     indicator.setViewPager(vpFeaturedItem)
                 }
                 is Fail -> {
                     //Toast.makeText(requireContext(), "Failed to load all movies", Toast.LENGTH_SHORT).show()
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
         }*/
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setFoodCategory()
    }

    private fun setFoodCategory() {
        val fragmentList = ArrayList<Fragment>()
        val mFragmentTitleList = ArrayList<String>()

        /*for (mData in category) {
            MyCartFragment().apply {
                val bundle = Bundle()
                bundle.putParcelable("mData", mData)
                arguments = bundle
                fragmentList.add(this)
                mFragmentTitleList.add(mData.name)
            }
        }*/

        val pizzaFragment = MyCartFragment()
        fragmentList.add(pizzaFragment)
        mFragmentTitleList.add("Cart")

        val sushiFragment = Fragment()
        fragmentList.add(sushiFragment)
        mFragmentTitleList.add("Order")

        val drinksFragment = Fragment()
        fragmentList.add(drinksFragment)
        mFragmentTitleList.add("Information")

        vpContainer.adapter =
            FragmentViewPagerAdapter(childFragmentManager, fragmentList, lifecycle)
        tabs?.setupWithViewPager2(vpContainer, mFragmentTitleList)
    }

}