package com.android.foodappdemo.core

import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import com.airbnb.mvrx.BaseMvRxFragment

abstract class BaseFragment (@LayoutRes containerLayoutId: Int = 0) : BaseMvRxFragment(containerLayoutId) {

}
