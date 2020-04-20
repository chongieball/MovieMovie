package example.chongieball.moviemovie.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import example.chongieball.moviemovie.data.model.movie.DetailMovieResponse
import example.chongieball.moviemovie.data.repository.Repository
import example.chongieball.moviemovie.ui.base.BaseViewModel
import example.chongieball.moviemovie.util.SingleLiveEvent
import example.chongieball.moviemovie.util.ext.addTo
import example.chongieball.moviemovie.util.rx.SchedulerProvider

class DetailMovieViewModel(
    private val repository: Repository,
    private val schedulerProvider: SchedulerProvider
) : BaseViewModel() {

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean>
        get() = _loading

    val errorMessage = SingleLiveEvent<String>()

    private val _movie = MutableLiveData<DetailMovieResponse>()
    val movie: LiveData<DetailMovieResponse>
        get() = _movie

    fun getDetail(idMovie: Int) {
        repository.detailMovie(idMovie)
            .subscribeOn(schedulerProvider.io())
            .doOnSubscribe {
                _loading.postValue(true)
            }
            .subscribe({ response ->
                _loading.postValue(false)
                _movie.postValue(response)
            }, {
                _loading.postValue(false)
                errorMessage.postValue(handleThrowable(it))
            })
            .addTo(compositeDisposable)
    }
}