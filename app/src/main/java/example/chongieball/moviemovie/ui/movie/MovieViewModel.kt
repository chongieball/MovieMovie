package example.chongieball.moviemovie.ui.movie

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import example.chongieball.moviemovie.data.model.movie.Movie
import example.chongieball.moviemovie.data.repository.Repository
import example.chongieball.moviemovie.ui.base.BaseViewModel
import example.chongieball.moviemovie.util.SingleLiveEvent
import example.chongieball.moviemovie.util.ext.addTo
import example.chongieball.moviemovie.util.rx.SchedulerProvider

class MovieViewModel(
    private val repository: Repository,
    private val schedulerProvider: SchedulerProvider
) : BaseViewModel() {

    private var currentPage = 1
    private var totalPage = 1

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean>
        get() = _loading

    private val _adapterLoading = MutableLiveData<Boolean>()
    val adapterLoading: LiveData<Boolean>
        get() = _adapterLoading

    val errorMessage = SingleLiveEvent<String>()

    private val _errorInit = MutableLiveData<Boolean>()
    val errorInit: LiveData<Boolean>
        get() = _errorInit

    private val _movies = MutableLiveData<List<Movie>>()
    val movies: LiveData<List<Movie>>
        get() = _movies

    fun getMovies(genre: String, isNextPage: Boolean = false) {
        if (isNextPage) currentPage++

        if (currentPage <= totalPage) {
            repository.movies(genre, currentPage)
                .subscribeOn(schedulerProvider.io())
                .doOnSubscribe {
                    checkLoading(true, isNextPage)
                }
                .subscribe({ response ->
                    checkLoading(false, isNextPage)
                    totalPage = response.totalPages
                    _movies.postValue(response.movies)
                }, {
                    checkLoading(false, isNextPage)
                    errorMessage.postValue(handleThrowable(it))

                    if (!isNextPage) _errorInit.postValue(true)
                })
                .addTo(compositeDisposable)
        }

    }

    private fun checkLoading(state: Boolean, isNextPage: Boolean) {
        if (isNextPage) _adapterLoading.postValue(state)
        else _loading.postValue(state)
    }
}