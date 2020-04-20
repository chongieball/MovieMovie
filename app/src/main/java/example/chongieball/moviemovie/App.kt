package example.chongieball.moviemovie

import androidx.appcompat.app.AppCompatDelegate
import androidx.multidex.MultiDexApplication
import example.chongieball.moviemovie.di.appModule
import example.chongieball.moviemovie.di.networkModule
import example.chongieball.moviemovie.di.repositoryModule
import example.chongieball.moviemovie.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class App : MultiDexApplication() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@App)
            modules(networkModule)
            modules(repositoryModule)
            modules(appModule)
            modules(viewModelModule)
        }
    }
}