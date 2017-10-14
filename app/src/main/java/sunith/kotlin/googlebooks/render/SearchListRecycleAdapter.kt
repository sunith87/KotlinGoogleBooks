package sunith.kotlin.googlebooks.render

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import googlebooks.kotlin.sunith.kotlingooglebooks.R
import sunith.kotlin.googlebooks.model.SearchData

class SearchListRecycleAdapter(val context: Context, val searchData: SearchData) : RecyclerView.Adapter<SearchListViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): SearchListViewHolder {
        val inflate = LayoutInflater.from(context).inflate(R.layout.item, parent, false)
        return SearchListViewHolder(inflate, context)
    }

    override fun getItemCount(): Int {
        return searchData.items.size
    }

    override fun onBindViewHolder(holder: SearchListViewHolder?, position: Int) {
        holder?.bindViews(searchData.items.get(position))
    }
}