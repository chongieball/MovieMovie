package example.chongieball.moviemovie.ui.detail.userreview

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import example.chongieball.moviemovie.R
import example.chongieball.moviemovie.data.model.review.Review
import example.chongieball.moviemovie.ui.base.AdapterBaseLoading
import example.chongieball.moviemovie.ui.base.AdapterClickListener
import example.chongieball.moviemovie.ui.base.BaseHolder
import kotlinx.android.synthetic.main.item_review.view.*

class UserReviewAdapter(list: MutableList<Review>, listener: AdapterClickListener<Review>): AdapterBaseLoading<Review>(list, listener) {

    class UserReviewHolder(listener: AdapterClickListener<Review>, view: View): BaseHolder<Review>(listener, view) {

        fun bind() {
            with(itemView) {
                itemData?.let {
                    tv_username.text = it.author
                    tv_review.text = it.content
                }
            }
        }
    }

    override fun onCreateMainViewHolder(parent: ViewGroup?, viewType: Int): BaseHolder<Review> {
        return UserReviewHolder(listener, LayoutInflater.from(parent?.context).inflate(R.layout.item_review, parent, false))
    }

    override fun bindViewHolder(holder: BaseHolder<Review>, data: Review) {
        (holder as UserReviewHolder).bind()
    }

    override fun layoutLoading(): Int = R.layout.view_footer_loading
}