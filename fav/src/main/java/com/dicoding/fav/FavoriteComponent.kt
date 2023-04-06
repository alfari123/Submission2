package com.dicoding.fav

import android.content.Context
import com.dicoding.submission1.di.FavModuleDepedency
import dagger.BindsInstance
import dagger.Component


@Component(dependencies = [FavModuleDepedency::class])
interface FavoriteComponent {
    fun inject(activity: FavoriteActivity)

    @Component.Builder
    interface Builder {
        fun context(@BindsInstance context: Context): Builder
        fun appDependencies(favoriteModuleDependency: FavModuleDepedency): Builder
        fun build(): FavoriteComponent
    }
}