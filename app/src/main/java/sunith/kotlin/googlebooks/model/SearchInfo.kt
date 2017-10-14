package sunith.kotlin.googlebooks.model

import java.io.Serializable

data class SearchInfo(val isEbook: Boolean, val saleability: String, val country: String) : Serializable
