package com.streaming.dazn.domain.di

import android.app.Application
import com.streaming.dazn.data.repository.GetVideoRepoImpl
import com.streaming.dazn.domain.repository.GetVideoRepo
import com.google.firebase.analytics.FirebaseAnalytics
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object AppModule {

    @Provides
    fun provideGetVideoRepo(): GetVideoRepo {
        return GetVideoRepoImpl()
    }

    @Provides
    @Singleton
    fun provideFirebaseAnalytics(application: Application): FirebaseAnalytics {
        return FirebaseAnalytics.getInstance(application)
    }

}