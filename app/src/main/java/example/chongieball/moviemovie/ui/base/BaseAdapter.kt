package example.chongieball.moviemovie.ui.base

import androidx.recyclerview.widget.RecyclerView

abstract class BaseAdapter<S, T : BaseHolder<S>>(
    val list: MutableList<S>,
    val listener: AdapterClickListener<S>
) : RecyclerView.Adapter<T>() {

    override fun onBindViewHolder(holder: T, position: Int) {
        val data = list[position]
        holder.bindData(position, data)
        bindViewHolder(holder, data)
    }

    protected abstract fun bindViewHolder(holder: T, data: S)

    override fun getItemCount(): Int = list.size
}