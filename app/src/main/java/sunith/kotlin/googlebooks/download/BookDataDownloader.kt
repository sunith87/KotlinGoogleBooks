package sunith.kotlin.googlebooks.download

import android.os.AsyncTask
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import sunith.kotlin.googlebooks.Constants
import sunith.kotlin.googlebooks.model.SearchData
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.MalformedURLException
import java.net.URL

class BookDataDownloader(val downloadSuccess: (SearchData) -> Unit, val downloadError: (String) -> Unit) : AsyncTask<String, Void, String>() {
    val URL_POSITION: Int = 0

    override fun doInBackground(vararg params: String?): String {
        return download(params.get(URL_POSITION))
    }

    private fun download(endpoint: String?): String {
        var result: String? = null
        val url: URL = URL(endpoint)

        try {
            val connection: HttpURLConnection = url.openConnection() as HttpURLConnection

            if (connection.responseCode == HttpURLConnection.HTTP_OK) {
                result = convertStreamToString(connection);
            } else {
                result = Constants.PARSE_ERROR
            }

        } catch (e: MalformedURLException) {
            result = Constants.MALFORMEDURL
        } catch (e: IOException) {
            result = Constants.IOEXCEPTION
        }

        return result!!
    }

    override fun onPostExecute(result: String?) {
        super.onPostExecute(result)

        if (result.equals(Constants.IOEXCEPTION) || result.equals(Constants.MALFORMEDURL)) {
            downloadError(result!!)
        } else {
            var searchData: SearchData? = null
            try {
                searchData = getSearchData(result)
            } catch (e: Exception) {
                downloadError(Constants.PARSE_ERROR)
            }
            if (searchData!!.items!!.isEmpty()) {
                downloadError(Constants.PARSE_ERROR)
            } else {
                downloadSuccess(searchData!!)
            }
        }
    }

    private fun getSearchData(result: String?): SearchData? {
        val searchDatatype = object : TypeToken<SearchData>() {}.type
        val gson: Gson = Gson()
        val searchData: SearchData = gson.fromJson(result, searchDatatype)
        return searchData;
    }

    private fun convertStreamToString(connection: HttpURLConnection): String {

        val inputStream = connection.inputStream
        val inputStreamReader: InputStreamReader = InputStreamReader(inputStream)
        val reader: BufferedReader = BufferedReader(inputStreamReader)

        val stringBuilder: StringBuilder = StringBuilder()

        reader.forEachLine { stringBuilder.append(it) }

        return stringBuilder.toString()
    }
}