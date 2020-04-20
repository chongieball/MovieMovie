package example.chongieball.moviemovie.ui.movie

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import example.chongieball.moviemovie.R
import example.chongieball.moviemovie.data.model.genre.Genre
import example.chongieball.moviemovie.data.model.movie.Movie
import example.chongieball.moviemovie.ui.base.AdapterClickListener
import example.chongieball.moviemovie.ui.base.BaseActivity
import example.chongieball.moviemovie.ui.detail.DetailMovieActivity
import example.chongieball.moviemovie.util.Constant
import example.chongieball.moviemovie.util.ext.observe
import example.chongieball.moviemovie.widget.EndlessScrollListener
import kotlinx.android.synthetic.main.activity_movie.*
import org.koin.android.viewmodel.ext.android.viewModel

class MovieActivity : BaseActivity(), AdapterClickListener<Movie> {

    private val viewModel by viewModel<MovieViewModel>()
    
    private lateinit var adapter: MovieAdapter
    
    private var genre: Genre? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie)
        
        intent.extras?.let {bundle -> 
            genre = bundle.getParcelable(Constant.Extra.GENRE)
            
            genre?.let {
                supportActionBar?.title = it.name
                initView()
                viewModel.getMovies(it.id.toString(), false)
            }
        }
        
    }

    private fun initView() {
        adapter = MovieAdapter(mutableListOf(), this)
        val manager = LinearLayoutManager(this)
        val endlessScrollListener = object: EndlessScrollListener(manager) {
            override fun onLoadMore(page: Int) {
                viewModel.getMovies(genre!!.id.toString(), true)
            }
        }
        rv_movie.layoutManager = manager
        rv_movie.adapter = adapter
        rv_movie.addOnScrollListener(endlessScrollListener)

        btn_retry.setOnClickListener {
            viewModel.getMovies(genre!!.id.toString())
        }
    }

    override fun observeChange() {
        observe(viewModel.loading, ::showLoading)
        observe(viewModel.adapterLoading, ::showAdapterLoading)
        observe(viewModel.errorMessage, ::showErrorMessage)
        observe(viewModel.errorInit, ::showErrorInit)
        observe(viewModel.movies, ::showMovies)
    }

    private fun showMovies(list: List<Movie>) {
        group_fail.visibility = View.GONE
        if (list.isEmpty() && adapter.list.isEmpty()) {
            tv_empty_data.visibility = View.VISIBLE
            group_success.visibility = View.GONE
        } else {
            adapter.list.addAll(list)
            adapter.notifyDataSetChanged()

            group_success.visibility = View.VISIBLE
            tv_empty_data.visibility = View.GONE
        }

    }

    private fun showErrorInit(b: Boolean) {
        if (b) {
            group_success.visibility = View.GONE
            group_fail.visibility = View.VISIBLE
            tv_empty_data.visibility = View.GONE
        }
    }

    private fun showAdapterLoading(b: Boolean) {
        if (b) adapter.showLoadingFooter() else adapter.hideLoadingFooter()
    }

    private fun showLoading(b: Boolean) {
        pb_loading.visibility = if (b) View.VISIBLE else View.GONE
        group_success.visibility = View.GONE
        group_fail.visibility = View.GONE
        tv_empty_data.visibility = View.GONE
    }

    override fun itemClick(position: Int, item: Movie?, view: Int) {
        val intent = Intent(this, DetailMovieActivity::class.java)
        intent.putExtra(Constant.Extra.MOVIE_ID, item?.id)
        startActivity(intent)
    }
}
