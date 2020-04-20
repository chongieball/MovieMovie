package example.chongieball.moviemovie.di

import example.chongieball.moviemovie.data.repository.Repository
import example.chongieball.moviemovie.data.repository.RepositoryImpl
import org.koin.dsl.module

val repositoryModule = module {
    factory<Repository> {
        RepositoryImpl(get())
    }
}