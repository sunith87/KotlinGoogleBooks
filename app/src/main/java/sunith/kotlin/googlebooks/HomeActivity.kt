package sunith.kotlin.googlebooks

import android.app.FragmentManager
import android.app.ProgressDialog
import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.text.TextUtils
import android.view.View
import android.widget.Button

import googlebooks.kotlin.sunith.kotlingooglebooks.R
import sunith.kotlin.googlebooks.download.BookDataDownloader
import sunith.kotlin.googlebooks.model.SearchData
import sunith.kotlin.googlebooks.render.SearchListFragment
import android.view.inputmethod.InputMethodManager
import android.widget.SearchView


class HomeActivity : AppCompatActivity() {

    private lateinit var searchView: SearchView
    private lateinit var searchButton: Button
    private var progressBar: ProgressDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        searchView = findViewById(R.id.bookSearchView) as SearchView
        searchView.setOnCloseListener({
            handleSearchClose();
        })

        searchButton = findViewById(R.id.btnBookSearch) as Button
        searchButton.setOnClickListener({
            hideKeyboard(searchView)
            fetchQuery()
            searchView.clearFocus()
        });
    }

    private fun handleSearchClose(): Boolean {
        val searchFragment: SearchListFragment = fragmentManager.findFragmentByTag(Constants.SEARCH_RESULT_FRAGMENT_TAG) as SearchListFragment;
        fragmentManager.beginTransaction().
                remove(searchFragment).
                commit();


//        var detailsFragment: DetailsFragment =getFragmentManager().findFragmentByTag(SEARCH_DETAILS_FRAGMENT_TAG);
//        if (detailsFragment != null) {
//            getFragmentManager().beginTransaction().
//                    remove(detailsFragment).
//                    commit();
//        }
        return false
    }

    private fun fetchQuery() {
        val query: String = searchView.query.toString();
        if (!TextUtils.isEmpty(query)) {
            fetchBookData(query);
        } else {
            showSnackBar("Search field cannot be empty");
        }
    }

    private fun hideKeyboard(searchView: SearchView) {
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(searchView.getWindowToken(), 0)
    }

    private fun showSnackBar(message: String) {
        val parentLayout: View = findViewById(R.id.rootView);
        Snackbar.make(parentLayout, message, Snackbar.LENGTH_LONG).show();
    }

    private fun fetchBookData(query: String) {
        showProgressBar(query);

        val spacesReplacedQuery: String = query.replace(" ", "+")
        val url: String = String.format(Constants.GOOGLE_API_ENDPOINT, spacesReplacedQuery, Constants.NUMBER_OF_RESULTS_MAX)

        val errorHandler = fun(error: String): Unit {
            downloadError(error)
        }

        val successHandler = fun(searchData: SearchData): Unit {
            downloadComplete(searchData)
        }
        BookDataDownloader(successHandler, errorHandler).execute(url)
    }


    private fun showProgressBar(query: String) {
        progressBar = ProgressDialog(this);
        progressBar!!.setMessage("Searching Books API for " + query);
        progressBar!!.show();
    }


    private fun downloadComplete(searchData: SearchData) {
        cleanupProgressBar()

        val searchListFragment = SearchListFragment()
        val bundle = Bundle()
        bundle.putSerializable(Constants.SEARCH_DATA, searchData)
        searchListFragment.arguments = bundle

        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.fragmentContainer, searchListFragment, Constants.SEARCH_RESULT_FRAGMENT_TAG)
        fragmentTransaction.commit()
    }

    private fun cleanupProgressBar() {
        progressBar!!.hide()
        progressBar = null
    }

    fun downloadError(error: String) {
        cleanupProgressBar();
        showSnackBar("Error while searching: " + error);
    }
}
