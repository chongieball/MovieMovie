package example.chongieball.moviemovie.ui.detail

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import example.chongieball.moviemovie.BuildConfig
import example.chongieball.moviemovie.R
import example.chongieball.moviemovie.data.model.movie.DetailMovieResponse
import example.chongieball.moviemovie.ui.base.BaseActivity
import example.chongieball.moviemovie.ui.detail.userreview.UserReviewDialog
import example.chongieball.moviemovie.util.Constant
import example.chongieball.moviemovie.util.ext.loadImage
import example.chongieball.moviemovie.util.ext.observe
import kotlinx.android.synthetic.main.activity_detail_movie.*
import kotlinx.android.synthetic.main.layout_header_detail.*
import org.koin.android.viewmodel.ext.android.viewModel

class DetailMovieActivity : BaseActivity() {

    private val viewModel by viewModel<DetailMovieViewModel>()

    private var movieId = 0
    private var tryCount = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_movie)

        supportActionBar?.title = getString(R.string.detail_movie)
        movieId = intent.getIntExtra(Constant.Extra.MOVIE_ID, 0)
        viewModel.getDetail(movieId)
        initView()
    }

    private fun initView() {
        btn_retry.setOnClickListener {
            tryCount++
            viewModel.getDetail(movieId)
        }
    }

    override fun observeChange() {
        observe(viewModel.loading, ::showLoading)
        observe(viewModel.errorMessage, ::showError)
        observe(viewModel.movie, ::showDetail)
    }

    private fun showDetail(detailMovieResponse: DetailMovieResponse) {
        iv_background.loadImage("${BuildConfig.URL_IMAGE}${detailMovieResponse.backdropPath}")
        tv_title.text = detailMovieResponse.title

        btn_watch_trailer.visibility = if (detailMovieResponse.videos.results.isEmpty()) View.INVISIBLE else View.VISIBLE
        btn_watch_trailer.setOnClickListener {
            startActivity(
                Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse("http://www.youtube.com/watch?v=${detailMovieResponse.videos.results[0].key}")
                )
            )
        }
        iv_poster.loadImage("${BuildConfig.URL_IMAGE}${detailMovieResponse.posterPath}")
        tv_tagline.text = detailMovieResponse.tagline
        tv_genres.text = detailMovieResponse.genres.joinToString { it.name }
        tv_release_date.text = detailMovieResponse.releaseDate
        tv_runtime.text = getString(R.string.text_runtime, detailMovieResponse.runtime.toString())
        tv_vote_average.text = detailMovieResponse.voteAverage.toString()
        tv_popular.text = detailMovieResponse.voteCount.toString()
        btn_see_review.setOnClickListener {
            val bundle = Bundle()
            bundle.putInt(Constant.Extra.MOVIE_ID, detailMovieResponse.id)
            val dialog = UserReviewDialog()
            dialog.arguments = bundle
            dialog.show(supportFragmentManager, Constant.Extra.DIALOG_USER_REVIEW)
        }
        cl_detail.visibility = View.VISIBLE
    }

    private fun showError(s: String) {
        showErrorMessage(s)
        btn_retry.visibility = View.VISIBLE
        cl_detail.visibility = View.GONE

        if (tryCount > 3) finish()
    }

    private fun showLoading(b: Boolean) {
        progress_bar.visibility = if (b) View.VISIBLE else View.GONE
        btn_retry.visibility = View.GONE
        cl_detail.visibility = View.GONE
    }
}
