package example.chongieball.moviemovie.ui.base

import android.view.View
import androidx.annotation.IdRes
import androidx.recyclerview.widget.RecyclerView

abstract class BaseHolder<T>(private val listener: AdapterClickListener<T>, view: View) :
    RecyclerView.ViewHolder(view), View.OnClickListener {

    init {
        view.setOnClickListener(this)
    }

    var itemPosition = 0
    var itemData: T? = null

    override fun onClick(p0: View) {
        listener.itemClick(itemPosition, itemData, itemView.id)
    }

    fun bindData(position: Int, data: T) {
        itemPosition = position
        itemData = data
    }

}

interface AdapterClickListener<T> {
    fun itemClick(position: Int, item: T?, @IdRes view: Int)
}