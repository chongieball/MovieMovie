package example.chongieball.moviemovie.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import example.chongieball.moviemovie.data.model.genre.Genre
import example.chongieball.moviemovie.data.repository.Repository
import example.chongieball.moviemovie.ui.base.BaseViewModel
import example.chongieball.moviemovie.util.SingleLiveEvent
import example.chongieball.moviemovie.util.ext.addTo
import example.chongieball.moviemovie.util.rx.SchedulerProvider

class HomeViewModel(
    private val repository: Repository,
    private val schedulerProvider: SchedulerProvider
) : BaseViewModel() {

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean>
        get() = _loading

    val errorMessage = SingleLiveEvent<String>()

    private val _genres = MutableLiveData<List<Genre>>()
    val genres: LiveData<List<Genre>>
        get() = _genres

    fun getGenres() {
        repository.genres()
            .subscribeOn(schedulerProvider.io())
            .doOnSubscribe {
                _loading.postValue(true)
            }
            .subscribe({ response ->
                _loading.postValue(false)
                _genres.postValue(response.genres)
            }, {
                _loading.postValue(false)
                errorMessage.postValue(handleThrowable(it))
            })
            .addTo(compositeDisposable)
    }
}