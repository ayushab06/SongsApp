package com.ayushab06.mymusic

import android.app.Activity
import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlin.coroutines.CoroutineContext

abstract class CoJob: AppCompatActivity(),CoroutineScope {
    lateinit var job: Job
    override val coroutineContext: CoroutineContext
        get() = job+Dispatchers.Main

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        job= Job()
    }

    override fun onDestroy() {
        super.onDestroy()
        job.cancel()
    }


}