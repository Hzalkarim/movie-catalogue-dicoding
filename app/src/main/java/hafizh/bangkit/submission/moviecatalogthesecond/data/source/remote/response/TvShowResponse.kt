package hafizh.bangkit.submission.moviecatalogthesecond.data.source.remote.response

import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class TvShowResponse(

    @field:SerializedName("name")
    override val title: String = "",

    @field:SerializedName("overview")
    override val overview: String = "",

    @field:SerializedName("poster_path")
    override val posterPath: String? = "",

    @field:SerializedName("popularity")
    val popularity: String = "",

    @field:SerializedName("first_air_date")
    val firstAirDate: String = "",

    @field:SerializedName("tagline")
    val tagline: String? = "",

    @field:SerializedName("vote_average")
    val voteAverage: Float = 0f,

    @field:SerializedName("number_of_episodes")
    val numberOfEpisode: Int = 0,

    @field:SerializedName("number_of_seasons")
    val numberOfSeasons: Int = 0,

    @field:SerializedName("id")
    override val id: Int = 0
) : BaseResponse
