package hafizh.bangkit.submission.moviecatalogthesecond.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class PopularTvShowResponse (
    @field:SerializedName("page")
    val page: Int,

    @field:SerializedName("results")
    val tvShows: List<TvShowResponse>,

    @field:SerializedName("total_results")
    val totalResults: Int,

    @field:SerializedName("total_pages")
    val totalPages: Int
)