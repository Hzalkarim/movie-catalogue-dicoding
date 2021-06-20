package hafizh.bangkit.submission.moviecatalogthesecond.data.source.local.entity

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity(tableName = "fav_table")
@Parcelize
data class MovieTvShowEntity(
    @PrimaryKey
    @ColumnInfo(name = "uid")
    val uid: String = "",

    @ColumnInfo(name = "id")
    val id: Int,

    @ColumnInfo(name = "title")
    val title: String? = null,

    @ColumnInfo(name = "overview")
    val overview: String? = null,

    @ColumnInfo(name = "poster_path")
    val posterPath: String? = null,

    @ColumnInfo(name = "tagline")
    val tagline: String? = null,

    @ColumnInfo(name = "vote_average")
    val voteAverage: Float = 0f,

    @ColumnInfo(name = "popularity")
    val popularity: String? = null,

    @ColumnInfo(name = "is_movie")
    val isMovie: Boolean = true
) : Parcelable
