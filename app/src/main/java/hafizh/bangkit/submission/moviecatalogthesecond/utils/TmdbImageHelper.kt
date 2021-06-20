package hafizh.bangkit.submission.moviecatalogthesecond.utils

class TmdbImageHelper {
    companion object {
        const val ICON = "w500"
        const val POSTER = "original"

        fun getImage(path: String?, type: String = ICON) =
            "https://image.tmdb.org/t/p/$type/$path"
    }
}