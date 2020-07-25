package br.com.carvalho.dm114_project

import android.app.Application
import android.content.Context

class MainApplication: Application() {

    init {
        instance = this
    }

    companion object {
        private var instance: MainApplication? = null

        fun getApplicationContext(): Context {
            return instance!!.applicationContext
        }
    }
}