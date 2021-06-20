package hafizh.bangkit.submission.moviecatalogthesecond.data.source

import hafizh.bangkit.submission.moviecatalogthesecond.data.source.remote.response.BaseResponse
import hafizh.bangkit.submission.moviecatalogthesecond.data.source.remote.response.MovieResponse
import hafizh.bangkit.submission.moviecatalogthesecond.data.source.remote.response.TvShowResponse

open class DetailDataSource(protected var baseResponse: BaseResponse) {

    companion object {
        @Volatile
        private var instance: DetailDataSource? = null

        fun getInstance(baseResponse: BaseResponse) : DetailDataSource =
            instance ?: synchronized( this) {
                instance ?: DetailDataSource(baseResponse).apply { instance = this }
            }
    }

    fun getTvShow() = baseResponse as TvShowResponse
    fun getMovie() = baseResponse as MovieResponse
    fun getBase() = baseResponse
    fun setBase(baseResponse: BaseResponse) {
        this.baseResponse = baseResponse
    }
}