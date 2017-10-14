package sunith.kotlin.googlebooks.model

import java.io.Serializable

data class SearchItem(val volumeInfo: VolumeInfo, val searchInfo: SearchInfo) : Serializable