package example.chongieball.moviemovie.ui.base

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

abstract class AdapterBaseLoading<T>(list: MutableList<T>, listener: AdapterClickListener<T>) :
    BaseAdapter<T, BaseHolder<T>>(list, listener) {

    var isLoading = false

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseHolder<T> {
        return when (viewType) {
            LOADING_VIEW -> LoadingViewHolder(
                LayoutInflater.from(parent.context).inflate(
                    layoutLoading(),
                    parent,
                    false
                ), listener
            )
            else -> onCreateMainViewHolder(parent, viewType)
        }
    }

    abstract fun onCreateMainViewHolder(parent: ViewGroup?, viewType: Int): BaseHolder<T>

    abstract fun layoutLoading(): Int

    class LoadingViewHolder<T>(
        itemView: View,
        listener: AdapterClickListener<T>
    ) :
        BaseHolder<T>(listener, itemView) {
        override fun onClick(v: View) {
            super.onClick(v)
        }
    }

    open fun showLoadingFooter() {
        isLoading = true
        notifyItemInserted(itemCount)
    }

    open fun hideLoadingFooter() {
        isLoading = false
        notifyItemInserted(itemCount)
    }

    companion object {
        const val LOADING_VIEW = 1
        const val ITEM_VIEW = 2
    }
}