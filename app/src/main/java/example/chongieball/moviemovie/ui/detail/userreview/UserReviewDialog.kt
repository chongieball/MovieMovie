package example.chongieball.moviemovie.ui.detail.userreview

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.LinearLayoutManager
import example.chongieball.moviemovie.R
import example.chongieball.moviemovie.data.model.review.Review
import example.chongieball.moviemovie.ui.base.AdapterClickListener
import example.chongieball.moviemovie.util.Constant
import example.chongieball.moviemovie.util.ext.observe
import example.chongieball.moviemovie.widget.EndlessScrollListener
import kotlinx.android.synthetic.main.dialog_user_review.*
import org.koin.android.viewmodel.ext.android.viewModel

class UserReviewDialog: DialogFragment(), AdapterClickListener<Review> {

    private val viewModel by viewModel<UserReviewViewModel>()

    private lateinit var adapter: UserReviewAdapter
    private var movieId = 0

    override fun onStart() {
        super.onStart()
        getDialog()?.getWindow()
            ?.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.dialog_user_review, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.let {
            movieId = it.getInt(Constant.Extra.MOVIE_ID)
            viewModel.getReviews(movieId)
        }

        initView()
        observeData()
    }

    private fun observeData() {
        observe(viewModel.loading, ::showLoading)
        observe(viewModel.adapterLoading, ::showAdapterLoading)
        observe(viewModel.errorMessage, ::showErrorMessage)
        observe(viewModel.errorInit, ::showErrorInit)
        observe(viewModel.reviews, ::showReviews)
    }

    private fun showReviews(list: List<Review>) {
        btn_retry.visibility = View.GONE
        if (list.isEmpty() && adapter.list.isEmpty()) {
            tv_empty_data.visibility = View.VISIBLE
            rv_review.visibility = View.GONE
        } else {
            adapter.list.addAll(list)
            adapter.notifyDataSetChanged()

            rv_review.visibility = View.VISIBLE
            tv_empty_data.visibility = View.GONE
        }
    }

    private fun showErrorInit(b: Boolean) {
        if (b) {
            rv_review.visibility = View.GONE
            btn_retry.visibility = View.VISIBLE
            tv_empty_data.visibility = View.GONE
        }
    }

    private fun showErrorMessage(s: String) {
        context?.let {
            Toast.makeText(it, s, Toast.LENGTH_SHORT).show()
        }
    }

    private fun showAdapterLoading(b: Boolean) {
        if (b) adapter.showLoadingFooter() else adapter.hideLoadingFooter()
    }

    private fun showLoading(b: Boolean) {
        pb_loading.visibility = if (b) View.VISIBLE else View.GONE
        rv_review.visibility = View.GONE
        btn_retry.visibility = View.GONE
        tv_empty_data.visibility = View.GONE
    }

    private fun initView() {
        adapter = UserReviewAdapter(mutableListOf(), this)
        val manager = LinearLayoutManager(context)
        val endlessScrollListener = object: EndlessScrollListener(manager) {
            override fun onLoadMore(page: Int) {
                viewModel.getReviews(movieId, true)
            }
        }
        rv_review.layoutManager = manager
        rv_review.adapter = adapter
        rv_review.addOnScrollListener(endlessScrollListener)

        btn_retry.setOnClickListener {
            viewModel.getReviews(movieId)
        }
    }

    override fun itemClick(position: Int, item: Review?, view: Int) {
        
    }
}