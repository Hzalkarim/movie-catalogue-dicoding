package hafizh.bangkit.submission.moviecatalogthesecond.data.source.remote.response

import android.os.Parcelable

interface BaseResponse : Parcelable {
    val id: Int
    val title: String
    val overview: String
    val posterPath: String?
}