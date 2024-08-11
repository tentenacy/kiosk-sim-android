package com.tenutz.kiosksim.di.module

import android.app.Activity
import android.content.Intent
import android.graphics.drawable.InsetDrawable
import androidx.activity.result.ActivityResult
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.tenutz.kiosksim.R
import com.tenutz.kiosksim.application.MainActivity
import com.tenutz.kiosksim.core.result.ActivityResultFactory
import com.tenutz.kiosksim.di.qualifier.UnitReference
import com.tenutz.kiosksim.di.qualifier.Units
import com.tenutz.kiosksim.utils.ext.dp
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.scopes.ActivityScoped

@InstallIn(ActivityComponent::class)
@Module
class ActivityModule {

    @ActivityScoped
    @Provides
    fun provideActivityResultFactory(activity: Activity): ActivityResultFactory<Intent, ActivityResult> {
        return ActivityResultFactory.registerActivityForResult(activity as AppCompatActivity)
    }

    @ActivityScoped
    @Provides
    fun provideMainActivity(activity: Activity): MainActivity {
        return activity as MainActivity
    }

    @ActivityScoped
    @Provides
    @UnitReference(Units.DP_8)
    fun provideDivider8dp(mainActivity: MainActivity): DividerItemDecoration {
        return ContextCompat.getDrawable(mainActivity, R.drawable.divider_8dp)!!.let { DividerItemDecoration(mainActivity, LinearLayoutManager.VERTICAL).apply { setDrawable(it) } }
    }

    @ActivityScoped
    @Provides
    @UnitReference(Units.DP_1_H_MARGIN_20)
    fun provideDividerDot5dp(mainActivity: MainActivity): DividerItemDecoration {
        return ContextCompat.getDrawable(mainActivity, R.drawable.divider_1dp)!!.let { DividerItemDecoration(mainActivity, LinearLayoutManager.VERTICAL).apply {
            setDrawable(InsetDrawable(it, 20f.dp, 0, 20f.dp, 0))
        } }
    }
}