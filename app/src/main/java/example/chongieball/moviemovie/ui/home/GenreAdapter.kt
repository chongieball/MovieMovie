package example.chongieball.moviemovie.ui.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import example.chongieball.moviemovie.R
import example.chongieball.moviemovie.data.model.genre.Genre
import example.chongieball.moviemovie.ui.base.AdapterClickListener
import example.chongieball.moviemovie.ui.base.BaseAdapter
import example.chongieball.moviemovie.ui.base.BaseHolder
import kotlinx.android.synthetic.main.item_genre.view.*

class GenreAdapter(
    val genres: MutableList<Genre>,
    private val clickListener: AdapterClickListener<Genre>
) : BaseAdapter<Genre, GenreAdapter.ViewHolder>(genres, clickListener) {

    class ViewHolder(clickListener: AdapterClickListener<Genre>, view: View) :
        BaseHolder<Genre>(clickListener, view) {

        fun bind() {
            with(itemView) {
                itemData?.let {
                    tv_genre.text = it.name
                }
            }
        }
    }

    override fun bindViewHolder(holder: ViewHolder, data: Genre) {
        holder.bind()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            clickListener,
            LayoutInflater.from(parent.context).inflate(R.layout.item_genre, parent, false)
        )
    }
}