package com.denebchorny.spaceflightnews

import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ProcessLifecycleOwner
import androidx.multidex.MultiDexApplication
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber
import timber.log.Timber.Forest.plant

@HiltAndroidApp
open class App : MultiDexApplication(), DefaultLifecycleObserver {

    override fun onCreate() {
        super<MultiDexApplication>.onCreate()
        initTimber()
        ProcessLifecycleOwner.get().lifecycle.addObserver(this)
    }

    override fun onStart(owner: LifecycleOwner) {
        super.onStart(owner)
        Timber.tag("AppStatus").d("App in foreground")
    }

    override fun onStop(owner: LifecycleOwner) {
        super.onStop(owner)
        Timber.tag("AppStatus").d("App in background")
    }

    private fun initTimber() {
        if (BuildConfig.DEBUG) {
            plant(Timber.DebugTree())
        }
    }
}