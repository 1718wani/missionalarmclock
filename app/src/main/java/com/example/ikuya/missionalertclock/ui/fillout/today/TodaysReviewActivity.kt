package com.example.ikuya.missionalertclock.ui.fillout.today

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.camera.core.impl.utils.Optional.of
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.ViewModelStores.of
import com.example.ikuya.missionalertclock.R
import com.google.android.gms.common.api.internal.ActivityLifecycleObserver.of
import kotlinx.android.synthetic.main.activity_main.*
import java.util.EnumSet.of
import java.util.stream.Stream.of

class TodaysReviewActivity:AppCompatActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.todays_review_activity)
        setSupportActionBar(toolbar)

        val viewModel :TodaysReviewActivityViewModel = ViewModelProvider(this).get(TodaysReviewActivityViewModel::class.java)
    }

}