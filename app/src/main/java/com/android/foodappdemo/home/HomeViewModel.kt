package com.android.foodappdemo.home

import com.airbnb.mvrx.*
import com.android.foodappdemo.core.MvRxViewModel

class HomeViewModel(
    homeState: HomeState,
    private val homeRepository: HomeRepository
) : MvRxViewModel<HomeState>(homeState) {

    init {

        homeRepository.getFeaturedImages().execute() {
            copy(featuredImages = it)
        }

        homeRepository.getCategories().execute {
            copy(category = it)
        }

    }


    fun fetchFeaturedImages() = withState { state ->

        if (state.featuredImages is Loading) return@withState


        homeRepository.getFeaturedImages().execute() {
            copy(featuredImages = it)
        }
    }


    companion object : MvRxViewModelFactory<HomeViewModel, HomeState> {
        override fun create(
            viewModelContext: ViewModelContext,
            state: HomeState
        ): HomeViewModel? {
            val watchlistRepository = HomeRepository()
            return HomeViewModel(state, watchlistRepository)
        }
    }

}