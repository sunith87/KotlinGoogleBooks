package sunith.kotlin.googlebooks.model


data class VolumeInfo(val title: String,
                      val subTitle: String,
                      val authors: List<String>,
                      val publisher: String,
                      val publishedDate: String,
                      val description: String,
                      val printType: String,
                      val printCount: Int,
                      val maturityRating: String,
                      val language: String,
                      val imageLinks: ImageLinks)

