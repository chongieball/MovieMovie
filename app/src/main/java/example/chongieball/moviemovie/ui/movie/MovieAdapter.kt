package example.chongieball.moviemovie.ui.movie

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import example.chongieball.moviemovie.BuildConfig
import example.chongieball.moviemovie.R
import example.chongieball.moviemovie.data.model.movie.Movie
import example.chongieball.moviemovie.ui.base.AdapterBaseLoading
import example.chongieball.moviemovie.ui.base.AdapterClickListener
import example.chongieball.moviemovie.ui.base.BaseHolder
import example.chongieball.moviemovie.util.ext.loadImage
import kotlinx.android.synthetic.main.item_film.view.*

class MovieAdapter(list: MutableList<Movie>, listener: AdapterClickListener<Movie>): AdapterBaseLoading<Movie>(list, listener) {

    class MovieHolder(listener: AdapterClickListener<Movie>, view: View): BaseHolder<Movie>(listener, view) {

        fun bind() {
            with(itemView) {
                itemData?.let {
                    tv_title.text = it.title
                    tv_popular.text = it.voteCount.toString()
                    tv_vote_average.text = it.voteAverage.toString()
                    tv_release_date.text = it.releaseDate

                    iv_background.loadImage("${BuildConfig.URL_IMAGE}${it.backdropPath}")
                    iv_poster.loadImage("${BuildConfig.URL_IMAGE}${it.posterPath}")
                }
            }
        }
    }

    override fun onCreateMainViewHolder(parent: ViewGroup?, viewType: Int): BaseHolder<Movie> {
        return MovieHolder(listener, LayoutInflater.from(parent?.context).inflate(R.layout.item_film, parent, false))
    }

    override fun bindViewHolder(holder: BaseHolder<Movie>, data: Movie) {
        (holder as MovieHolder).bind()
    }

    override fun layoutLoading(): Int = R.layout.view_footer_loading
}