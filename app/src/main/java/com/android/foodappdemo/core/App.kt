package com.android.foodappdemo.core

import android.app.Application
import com.android.foodappdemo.models.FoodItem
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class App : Application() {

    companion object {
        //val cartList: ArrayList<Pair<Int, MutableList<FoodItem>>> = ArrayList()
        val cartList: MutableList<FoodItem> = ArrayList()
    }

    override fun onCreate() {
        super.onCreate()

        /*MockableMavericks.initialize(this)
        // Override the default activity for showing mocks from the launcher
        MavericksLauncherMockActivity.activityToShowMock = LauncherActivity::class*/

        startKoin {
            androidContext(this@App)
            //modules()
        }
    }
}