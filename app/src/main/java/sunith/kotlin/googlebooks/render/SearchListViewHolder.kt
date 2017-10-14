package sunith.kotlin.googlebooks.render

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.squareup.picasso.Picasso
import googlebooks.kotlin.sunith.kotlingooglebooks.R
import sunith.kotlin.googlebooks.model.SearchItem
import android.support.v4.content.LocalBroadcastManager
import android.content.Intent
import sunith.kotlin.googlebooks.Constants


class SearchListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
    private var searchItem: SearchItem? = null

    override fun onClick(view: View?) {

        val searchItemClicked = Intent()
        searchItemClicked.action = Constants.SEARCH_ITEM_CLICKED
        searchItemClicked.putExtra(Constants.SEARCH_ITEM_KEY, searchItem)
        LocalBroadcastManager.getInstance(context).sendBroadcast(searchItemClicked)
    }

    private lateinit var context: Context

    private lateinit var titleView: TextView

    private lateinit var bookThumbanailView: ImageView

    constructor(itemView: View, context: Context) : this(itemView) {
        this.context = context;
        titleView = itemView.findViewById<View>(R.id.bookTitleView) as TextView
        bookThumbanailView = itemView.findViewById<View>(R.id.bookImageView) as ImageView
        itemView.setOnClickListener(this);
    }


    fun bindViews(searchItem: SearchItem) {
        this.searchItem = searchItem;

        titleView.text = searchItem.volumeInfo.title;
        val smallThumbnail = searchItem.volumeInfo.imageLinks.smallThumbnail
        download(smallThumbnail)
    }

    private fun download(url: String) {
        Picasso.with(context).
                load(url).
                fit().
                placeholder(R.drawable.placeholder).
                into(bookThumbanailView);
    }


}