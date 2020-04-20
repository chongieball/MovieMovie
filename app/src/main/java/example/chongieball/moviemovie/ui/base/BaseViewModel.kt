package example.chongieball.moviemovie.ui.base

import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.ViewModel
import example.chongieball.moviemovie.util.Constant
import io.reactivex.disposables.CompositeDisposable
import java.io.IOException
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

abstract class BaseViewModel : ViewModel(), LifecycleObserver {

    protected val compositeDisposable by lazy { CompositeDisposable() }

    override fun onCleared() {
        with(compositeDisposable) {
            clear()
            dispose()
        }
        super.onCleared()
    }

    fun handleThrowable(throwable: Throwable): String {
        return when (throwable) {
            is ConnectException,
            is SocketTimeoutException,
            is UnknownHostException,
            is IOException -> {
                Constant.Error.MESSAGE_CONNECTION_ERROR
            }
            else -> Constant.Error.MESSAGE_CONNECTION_ERROR
        }
    }
}