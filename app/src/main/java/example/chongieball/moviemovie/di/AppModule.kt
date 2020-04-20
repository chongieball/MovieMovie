package example.chongieball.moviemovie.di

import example.chongieball.moviemovie.util.rx.AppSchedulerProvider
import example.chongieball.moviemovie.util.rx.SchedulerProvider
import org.koin.dsl.module

val appModule = module {

    factory<SchedulerProvider> {
        AppSchedulerProvider()
    }
}