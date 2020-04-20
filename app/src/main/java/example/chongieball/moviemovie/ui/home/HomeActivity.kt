package example.chongieball.moviemovie.ui.home

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import example.chongieball.moviemovie.R
import example.chongieball.moviemovie.data.model.genre.Genre
import example.chongieball.moviemovie.ui.base.AdapterClickListener
import example.chongieball.moviemovie.ui.base.BaseActivity
import example.chongieball.moviemovie.ui.movie.MovieActivity
import example.chongieball.moviemovie.util.Constant
import example.chongieball.moviemovie.util.ext.observe
import kotlinx.android.synthetic.main.activity_home.*
import org.koin.android.viewmodel.ext.android.viewModel

class HomeActivity : BaseActivity(), AdapterClickListener<Genre> {

    private val viewModel by viewModel<HomeViewModel>()

    private lateinit var adapter: GenreAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        initView()
        viewModel.getGenres()
    }

    private fun initView() {
        adapter = GenreAdapter(mutableListOf(), this)
        rv_genre.layoutManager = GridLayoutManager(this, 2)
        rv_genre.adapter = adapter

        btn_retry.setOnClickListener {
            viewModel.getGenres()
        }
    }

    override fun observeChange() {
        observe(viewModel.loading, ::showLoading)
        observe(viewModel.errorMessage, ::showError)
        observe(viewModel.genres, ::showListGenre)
    }

    private fun showListGenre(list: List<Genre>) {
        adapter.genres.addAll(list)
        adapter.notifyDataSetChanged()
        group_fail.visibility = View.GONE
        group_success.visibility = View.VISIBLE
    }

    private fun showError(s: String) {
        showErrorMessage(s)
        group_fail.visibility = View.VISIBLE
        group_success.visibility = View.GONE
    }

    private fun showLoading(b: Boolean) {
        pb_loading.visibility = if (b) View.VISIBLE else View.GONE
        group_success.visibility = View.GONE
        group_fail.visibility = View.GONE
    }

    override fun itemClick(position: Int, item: Genre?, view: Int) {
        val intent = Intent(this, MovieActivity::class.java)
        intent.putExtra(Constant.Extra.GENRE, item)
        startActivity(intent)
    }
}
