package com.android.foodappdemo.core

import com.airbnb.mvrx.BaseMvRxViewModel

import com.airbnb.mvrx.MvRxState

abstract class MvRxViewModel<S : MvRxState>(initialState: S) : BaseMvRxViewModel<S>(initialState,true)