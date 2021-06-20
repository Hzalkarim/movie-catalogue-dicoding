package hafizh.bangkit.submission.moviecatalogthesecond.data.source.remote.response

import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class MovieResponse(
    @field:SerializedName("title")
    override val title: String = "",

    @field:SerializedName("overview")
    override val overview: String = "",

    @field:SerializedName("poster_path")
    override val posterPath: String? = "",

    @field:SerializedName("popularity")
    val popularity: String = "",

    @field:SerializedName("release_date")
    val releaseDate: String = "",

    @field:SerializedName("runtime")
    val runtime: Int? = 0,

    @field:SerializedName("tagline")
    val tagline: String? = "",

    @field:SerializedName("vote_average")
    val voteAverage: Float = 0f,

    @field:SerializedName("id")
    override val id: Int = 0
) : BaseResponse
