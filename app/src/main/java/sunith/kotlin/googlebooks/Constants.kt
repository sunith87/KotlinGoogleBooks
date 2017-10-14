package sunith.kotlin.googlebooks

object Constants {
    const val SEARCH_DATA = "search_data"
    const val SEARCH_ITEM_CLICKED = "search_item_clicked"
    const val SEARCH_ITEM_KEY = "search_item_key"
    const val SEARCH_RESULT_FRAGMENT_TAG = "SEARCH_RESULT_FRAGMENT_TAG"
    const val SEARCH_DETAILS_FRAGMENT_TAG = "SEARCH_DETAILS_FRAGMENT_TAG"
    const val GOOGLE_API_ENDPOINT = "https://www.googleapis.com/books/v1/volumes?q=search+%s&filter=full&maxResults=%d"
    const val NUMBER_OF_RESULTS_MAX: Int = 40

    const val MALFORMEDURL = "MALFORMED_URL_EXCEPTION"
    const val IOEXCEPTION = "IO_EXCEPTION";
    const val PARSE_ERROR = "PARSE_ERROR";


}