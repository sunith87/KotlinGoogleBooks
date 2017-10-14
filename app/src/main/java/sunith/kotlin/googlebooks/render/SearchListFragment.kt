package sunith.kotlin.googlebooks.render

import android.app.Fragment
import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import googlebooks.kotlin.sunith.kotlingooglebooks.R
import sunith.kotlin.googlebooks.Constants
import sunith.kotlin.googlebooks.model.SearchData

class SearchListFragment : Fragment() {

    private lateinit var mRecyclerView: RecyclerView
    private lateinit var mLayoutManager: RecyclerView.LayoutManager

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val inflatedView = inflater?.inflate(R.layout.list_fragment, container, false)
        mRecyclerView = inflatedView!!.findViewById(R.id.bookListRecycler)
        return inflatedView;
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val searchData: SearchData = arguments.getSerializable(Constants.SEARCH_DATA) as SearchData
        mLayoutManager = GridLayoutManager(activity, 2)
        mRecyclerView.layoutManager = mLayoutManager
        mRecyclerView.adapter = SearchListRecycleAdapter(activity, searchData)
    }
}